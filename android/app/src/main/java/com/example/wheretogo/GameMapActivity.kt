package com.example.wheretogo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.baidu.lbsapi.panoramaview.PanoramaView
import com.baidu.lbsapi.panoramaview.PanoramaViewListener
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.GeoCodeResult
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.example.wheretogo.PanaTool.getGuessPoint
import com.example.wheretogo.PanaTool.getTargetPoint
import com.example.wheretogo.PanaTool.sugSearch
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import com.baidu.lbsapi.tools.Point as Point1

/**
 * 游戏地图界面
 */
class GameMapActivity : BaseActivity() {
    companion object{
        //需要传入地图界面的数据：mode
        fun actionStart(context: Context, mode: Boolean, data2: String){
            val intent = Intent(context, GameMapActivity::class.java).apply {
                putExtra("Mode", mode)
                putExtra("param2", data2)
            }
            context.startActivity(intent)
        }
    }

    lateinit var sensorManager: SensorManager
    private val myDB_help =DB_MyDatabaseHelper(this)
    private var mPanaView: PanoramaView? = null
    private var mSuggestionSearch : SuggestionSearch? = null
    private var editText_city: EditText? = null
    private var editText_address: EditText? = null
    private var editText_guess: EditText? = null
    private var textView_introduce: TextView? = null
    private var textView_systemOutput: TextView? = null
    private var textView_guesscity: TextView? = null
    private var listView: ListView? = null
    private var button_search : Button? = null
    private var button_guess : Button? = null
    private var toolbar: androidx.appcompat.widget.Toolbar? = null
    private var bottomNavigationView: com.google.android.material.bottomnavigation.BottomNavigationView? = null
//    var dtDistance : Double = 0.0
    private var targetPoint : Point1? = null
//    var guessPoint : Point1? = null
    private var isCorrect : Boolean = false
    private var mode : Boolean = true
    private var tryCount =0
    private var isFullScreen = false

    private var wherePlace : Place?= null
    private var guessPlace = Place("南京","南京大学")
    private var goPlace = Place("南京","南京大学")

    val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if(isFullScreen) {
                val scaleFactor = 2f
                mPanaView?.panoramaPitch = event.values[1] * scaleFactor
                mPanaView?.panoramaHeading = event.values[0] * scaleFactor
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
    }
    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @SuppressLint("SetTextI18n", "ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_map_layout)
        // 相关设置
        mode = intent.getBooleanExtra("Mode", true) //默认为猜测模式,如果是在main界面左滑则进入地图模式
        myDB_help.ResetTourStatus() //每次进入游戏界面重置游戏进度
        initView()

        val intent = intent
        if (intent != null) {
            startPanoView(intent.getIntExtra("type", -1))
        }
        //陀螺仪传感器

        //顶部toolbar监听器
        toolbar?.setOnMenuItemClickListener() {
            when(it.itemId){
                R.id.action_add -> {
                    handleAddClick()
                }
                R.id.icon_map -> {
                    handleMapClick()
                }
            }
            true
        }
        bottomNavigationView?.selectedItemId = R.id.map
        bottomNavigationView?.setItemIconTintList(null)
        bottomNavigationView?.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.home -> {
                    Toast.makeText(this, "返回主界面", Toast.LENGTH_SHORT).show()
                    finish()
                    overridePendingTransition(com.example.wheretogo.R.anim.slide_in_left, com.example.wheretogo.R.anim.slide_out_right)
                }

                R.id.map -> {
                    Toast.makeText(this, "地图模式", Toast.LENGTH_SHORT).show()
                    mode = !mode
                    updateUI()
                }

                R.id.add -> {
                    Toast.makeText(this, "添加新地点", Toast.LENGTH_SHORT).show()
                    DB_MainActivity.actionStart(this, "data1", "data2")
                    overridePendingTransition(com.example.wheretogo.R.anim.slide_in_left, com.example.wheretogo.R.anim.slide_out_right)
                }
            }
            true
        }

        //陀螺仪监听器
        sensorManager.registerListener(
            sensorEventListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
            )
        //游戏开始
        gameStart()
        updateUI()


    }
    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private fun initView() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mPanaView = findViewById<View>(R.id.panorama) as PanoramaView
        editText_city = findViewById<EditText>(R.id.panodemo_main_input_city)
        editText_address = findViewById<EditText>(R.id.panodemo_main_input_address)
        editText_guess = findViewById<EditText>(R.id.user_guess)
        textView_introduce = findViewById<TextView>(R.id.introduce)
        textView_systemOutput = findViewById<TextView>(R.id.system_output)
        listView = findViewById<ListView>(R.id.sugsearchlist)
        button_search = findViewById<Button>(R.id.search)
        button_guess = findViewById<Button>(R.id.guess)
        toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.title_toolbar)
        textView_guesscity = findViewById<TextView>(R.id.city_guess)
        mPanaView?.accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                // Add the content description of the PanoramaView to the AccessibilityNodeInfo
                info.text = "百度全景地图"
            }
            override fun onRequestSendAccessibilityEvent(host: ViewGroup, child: View, event: AccessibilityEvent): Boolean {
                // Change the content of the event to the content description of the PanoramaView
                event.text.clear()
                event.text.add("百度全景地图")
                return super.onRequestSendAccessibilityEvent(host, child, event)
            }
        }
        //全屏按钮
        val fullscreenButton: FloatingActionButton = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fullscreen_button)
        val originalLayoutParams = mPanaView?.layoutParams as RelativeLayout.LayoutParams
        fullscreenButton.setOnClickListener {
            if(!isFullScreen) {
                val layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )

                layoutParams.topMargin = 0

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.removeRule(RelativeLayout.BELOW)
                }

                mPanaView?.layoutParams = layoutParams
            }else{
                mPanaView?.layoutParams = originalLayoutParams
            }
            isFullScreen = !isFullScreen

            updateUI()

        }

        bottomNavigationView = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation_bar)
    }

    private fun updateUI(){
        //猜测模式不显示箭头,随意走走模式显示箭头
        mPanaView?.setShowTopoLink(!mode)
        if(!mode){
            textView_guesscity?.visibility = View.GONE
            textView_systemOutput?.text="随便走走吧"
            editText_city?.visibility = View.VISIBLE
            editText_address?.visibility = View.VISIBLE
            editText_guess?.visibility = View.GONE
            editText_guess?.hint = "想去哪？"
            button_search?.visibility= View.VISIBLE
            button_guess?.visibility= View.GONE
            editText_guess?.isEnabled=false
            textView_introduce?.visibility = View.VISIBLE
            textView_systemOutput?.visibility = View.GONE
            if(isFullScreen){
                editText_city?.visibility = View.GONE
                editText_address?.visibility = View.GONE
                button_search?.visibility= View.GONE
                textView_introduce?.visibility = View.GONE

            }else{
                editText_city?.visibility = View.VISIBLE
                editText_address?.visibility = View.VISIBLE
                button_search?.visibility= View.VISIBLE
                textView_introduce?.visibility = View.VISIBLE
            }
            button_search?.setOnClickListener{
                goPlace.address = editText_address?.text.toString()
                getTargetPoint(goPlace.city, goPlace.address,this@GameMapActivity)
//                Log.i("PanaTool", "change the panorama")
            }
            editText_city?.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    goPlace.city = editText_city?.text.toString()
                    //隐藏键盘
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    editText_city?.clearFocus()
                    true
                } else {
                    false
                }
            }

            editText_city?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // 输入文字结束时
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // 输入文字前
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 输入文字中
                    goPlace.city = s.toString()
//                    Log.i("PanaTool", goPlace.city.toString())
                }
            })
            //监听输入框文本变化
            editText_address?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // 输入文字结束时
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // 输入文字前
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 输入文字中
                    goPlace.address = s.toString()
                    Log.i("PanaTool", goPlace.address.toString())
                    sugSearch(goPlace.city,goPlace.address,this@GameMapActivity)
                }
            })
            editText_address?.setOnFocusChangeListener { v, hasFocus ->
                if(hasFocus){
                    //获取焦点时,显示联想列表
                    listView?.visibility = View.VISIBLE
                } else {
                    //失去焦点时,隐藏联想列表
                    listView?.visibility = View.GONE
                }
            }
            editText_address?.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //不点击联想框的时候,按回车也能隐藏联想框和键盘
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    editText_address?.clearFocus()
                    true
                } else {
                    false
                }
            }


        }else {
            //猜测模式
            textView_guesscity?.visibility = View.VISIBLE
            textView_systemOutput?.text="猜猜这是哪里"
            editText_city?.visibility = View.GONE
            editText_address?.visibility = View.GONE
            editText_guess?.visibility = View.VISIBLE
            editText_guess?.hint = "你知道这是哪吗?"
            button_search?.visibility= View.GONE
            button_guess?.visibility= View.VISIBLE
            editText_guess?.isEnabled=true
            textView_systemOutput?.visibility = View.VISIBLE
            textView_introduce?.visibility = View.GONE
            if(isFullScreen) {
                textView_guesscity?.visibility = View.GONE
                editText_guess?.visibility = View.GONE
                button_guess?.visibility = View.GONE
                textView_systemOutput?.visibility = View.GONE
            }else{
                textView_guesscity?.visibility = View.VISIBLE
                editText_guess?.visibility = View.VISIBLE
                button_guess?.visibility = View.VISIBLE
                textView_systemOutput?.visibility = View.VISIBLE
            }
            editText_guess?.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    guessPlace.address = v.text.toString()
                    //隐藏键盘
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    editText_guess?.clearFocus()
                    true
                } else {
                    false
                }
            }
            editText_guess?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // 输入文字结束时
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // 输入文字前
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 输入文字中
                    guessPlace.address = s.toString()
                    Log.i("PanaTool", guessPlace.address.toString())
                    sugSearch(guessPlace.city,guessPlace.address,this@GameMapActivity)
                }
            })
            editText_guess?.setOnFocusChangeListener { v, hasFocus ->
                if(hasFocus){
                    //获取焦点时
                    listView?.visibility = View.VISIBLE
                } else {
                    //失去焦点时
                    listView?.visibility = View.GONE
                }
            }
            button_guess?.setOnClickListener {
                guessPlace.address = editText_guess?.text.toString()
                //隐藏键盘
                handleGuess()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText_guess?.windowToken, 0)
                editText_guess?.clearFocus()
            }
        }
    }

    private fun gameStart(){
        val name =myDB_help.nextSiteName
        val city = myDB_help.getSelectedCity(name)
        val address = myDB_help.getSelectedAddress(name)
        val pid = myDB_help.getSelectedPid(name)
        val intro = myDB_help.getSelectedIntro(name)
        wherePlace = Place(name.split(";").toTypedArray(),city,address,pid,intro.split(";").toTypedArray())
        guessPlace.city = wherePlace?.city
        textView_guesscity?.text =wherePlace?.city
        getTargetPoint(wherePlace?.city,wherePlace?.address,this@GameMapActivity)
    }

    private fun handleGuess(){
        getGuessPoint(guessPlace.city,guessPlace.address,this@GameMapActivity)
    }

    private fun leave(){
        mode=false
        updateUI()
    }

    private fun continueGame(){
        gameStart()
        editText_guess?.setText("")
    }

    private fun nextIntro(i : Int ) : String {
        val intro : String
        intro = if (i < wherePlace?.intro?.size!!-1) {
            wherePlace?.intro?.get(i).toString()
        }else{
            wherePlace?.name?.get(0).toString()
        }
        return intro
    }
    private fun startPanoView(type : Int) {
        mPanaView?.setShowTopoLink(!mode)
        // 测试回调函数,需要注意的是回调函数要在setPanorama()之前调用，否则回调函数可能执行异常
        mPanaView?.setPanoramaViewListener(object : PanoramaViewListener {
            override fun onLoadPanoramaBegin() {
                Log.i(toString(), "onLoadPanoramaStart...")
            }

            override fun onLoadPanoramaEnd(json: String) {
                //如果是随意走走模式,则获取当前位置的pid
                if (!mode) {
                    val jsonObject = JSONObject(json)
                    wherePlace?.pid = jsonObject.getString("ID")
                }
                }

            override fun onLoadPanoramaError(error: String) {
                Log.i(
                    toString(),
                    "onLoadPanoramaError : $error"
                )
                //TODO:提示错误
            }

            override fun onDescriptionLoadEnd(json: String) {}
            override fun onMessage(msgName: String, msgType: Int) {}
            override fun onCustomMarkerClick(key: String) {}
            override fun onMoveStart() {}
            override fun onMoveEnd() {}
        })
        mPanaView?.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionHigh)
    }

    fun handleSuggestionResult(suggestionResult: SuggestionResult){
        //获取在线建议检索结果
//        Log.i("PanaTool", "start find the suggestion")

        if (suggestionResult.error == SearchResult.ERRORNO.NO_ERROR) {
            val suggestions = suggestionResult.allSuggestions
            val suggestionList = ArrayList<String>()
            for(suggestion in suggestions){
                suggestionList.add(suggestion.key)
            }

            val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                suggestionList
            )
            listView?.adapter = adapter

            listView?.setOnItemClickListener{ parent, view, position, id ->
                //获取点击的建议项
                val suggestion = suggestions[position]
                if(!mode) {
                    editText_address?.setText(suggestion.key)
                    editText_address?.clearFocus()
                }else {
                    editText_guess?.setText(suggestion.key)
                    editText_guess?.clearFocus()
                }
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
    fun handleGeoCodeResultTarget(geoCodeResult : GeoCodeResult){
        //获取随便走走地点地理编码检索结果
//        Log.i("PanaTool", "find the location")
        val latitude = geoCodeResult.location.latitude
        val longitude = geoCodeResult.location.longitude
        targetPoint = Point1(longitude,latitude)
        mPanaView?.setPanoramaViewListener(object : PanoramaViewListener {
            override fun onLoadPanoramaBegin() {
                Log.i(toString(), "onLoadPanoramaStart...")
            }

            override fun onLoadPanoramaEnd(json: String) {
                if(!mode) {
                    val jsonObject = JSONObject(json)
                    goPlace.pid = jsonObject.getString("ID")
                    Log.i("PanaTool", goPlace.pid.toString())
                }
            }
            override fun onLoadPanoramaError(error: String) {
                Log.i(
                    toString(),
                    "onLoadPanoramaError : $error"
                )
                //TODO:提示错误
            }

            override fun onDescriptionLoadEnd(json: String) {}
            override fun onMessage(msgName: String, msgType: Int) {}
            override fun onCustomMarkerClick(key: String) {}
            override fun onMoveStart() {}
            override fun onMoveEnd() {}
        })
        if(mode){
            mPanaView?.setPanorama(wherePlace?.pid)
        }else {
            mPanaView?.setPanorama(targetPoint!!.x, targetPoint!!.y)
        }
    }
    fun handleGeoCodeResultGuess(geoCodeResult : GeoCodeResult){
        //获取猜测地理编码检索结果
        Log.i("PanaTool", "find the location")
        //删去了计算距离的部分
//        val latitude = geoCodeResult.location.latitude
//        val longitude = geoCodeResult.location.longitude
//        guessPoint = Point1(longitude,latitude)
//        dtDistance= getDistance(Point2(guessPoint!!.x,guessPoint!!.y),Point2(targetPoint!!.x,targetPoint!!.y))
//        val formattedDistance = String.format("%.2f", dtDistance*100)
//        val direction = CoordinateTool.bearingToDirection(CoordinateTool.calculateBearing(guessPoint,targetPoint))
        if(wherePlace?.name?.contains(guessPlace.address) == true) {
            val name = wherePlace?.name!![0].toString()
            val intro = wherePlace?.intro!!.joinToString("\n")
            textView_systemOutput?.text = "回答正确:$name"
            //弹出提示框，继续下一题还是先随便走走
            isCorrect=true
            tryCount=0
            AlertDialog.Builder(this).apply {
                setTitle("恭喜你回答正确")
                //TODO:检查
                setMessage(name + "\n" + intro)
                setCancelable(false)
                setPositiveButton("继续游戏") { dialog, which ->
                    continueGame()
                }
                setNegativeButton("随便走走") { dialog, which ->
                    leave()
                }
                show()
            }
        } else {
            val intro = nextIntro(tryCount)
            tryCount++
            textView_systemOutput?.text = "回答错误,提示:$intro"
            isCorrect=false
        }
    }

    private fun handleAddClick() {
        val editText = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("介绍一下这个地方吧")
            .setView(editText)
            .setPositiveButton("确定") { dialog, _ ->
                val intro = editText.text.toString()
                goPlace.intro = arrayOf(intro)
                myDB_help.addSite(goPlace.address,goPlace.address,goPlace.city,goPlace.pid,goPlace.intro[0])
                myDB_help.UpdateTourStatus("1", myDB_help.getCurrentAt(), myDB_help.getTotalSitesNumber() + 1)
                dialog.dismiss()
            }
            .setNegativeButton("取消") { dialog, _ ->
                dialog.cancel()
            }
            .create()
        dialog.show()
    }
    private fun handleMapClick() {
        //跳转到地图界面
        mode=!mode
        updateUI()
        Toast.makeText(this, "随便走走吧", Toast.LENGTH_SHORT).show()
    }
    @Override
    override fun onPause() {
        super.onPause()
        mPanaView?.onPause()
    }
    @Override
    override fun onResume() {
        super.onResume()
        mPanaView?.onResume()
    }
    @Override
    override fun onDestroy() {
        mPanaView?.destroy()
        mSuggestionSearch?.destroy()
        sensorManager.unregisterListener(sensorEventListener)
        super.onDestroy()
    }
}