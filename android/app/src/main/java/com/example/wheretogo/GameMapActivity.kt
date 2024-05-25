package com.example.wheretogo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.baidu.lbsapi.panoramaview.PanoramaView
import com.baidu.lbsapi.panoramaview.PanoramaViewListener
import com.baidu.mapapi.model.CoordUtil.getDistance
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.GeoCodeResult
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.example.wheretogo.PanaTool.getGuessPoint
import com.example.wheretogo.PanaTool.getTargetPoint
import com.example.wheretogo.PanaTool.sugSearch
import com.baidu.lbsapi.tools.Point as Point1
import com.baidu.platform.comapi.basestruct.Point as Point2


class GameMapActivity : BaseActivity() {
    companion object{
        //TODO:需要传入地图界面的数据
        fun actionStart(context: Context, data1: String, data2: String){
            val intent = Intent(context, GameMapActivity::class.java).apply {
                putExtra("param1", data1)
                putExtra("param2", data2)
            }
            context.startActivity(intent)
        }
    }
    var mPanaView: PanoramaView? = null
    var mSuggestionSearch : SuggestionSearch? = null
    var editText_city: EditText? = null
    var editText_address: EditText? = null
    var editText_guess: EditText? = null
    var textView_introduce: TextView? = null
    var textView_systemOutput: TextView? = null
    var listView: ListView? = null
    var button_search : Button? = null
    var toolbar: com.google.android.material.appbar.MaterialToolbar? = null
    var goPlaceNameCity : String = "北京"
    var goPlaceNameAddress : String = "海淀区上地十街10号"
    var wherePlaceNameCity : String ="北京"
    var wherePlaceNameAddress : String ="海淀区上地十街10号"
    var wherePlacePID : String =""
    var guessPlaceNameAddress : String ?= null
    var dtDistance : Double = 0.0
    var targetPoint : Point1? = null
    var guessPoint : Point1? = null
    var isCorrect : Boolean = false
    var mode : Boolean = true
    var introduce : String =""

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @SuppressLint("SetTextI18n", "ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_map_layout)
        initView()
        updateUI()
        val intent = intent
        if (intent != null) {
            testPanoByType(intent.getIntExtra("type", -1))
        }
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

        //游戏过程
        gameStart()


    }
    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private fun initView() {
        mPanaView = findViewById<View>(R.id.panorama) as PanoramaView
        editText_city = findViewById<EditText>(R.id.panodemo_main_input_city)
        editText_address = findViewById<EditText>(R.id.panodemo_main_input_address)
        editText_guess = findViewById<EditText>(R.id.user_guess)
        textView_introduce = findViewById<TextView>(R.id.introduce)
        textView_systemOutput = findViewById<TextView>(R.id.system_output)
        listView = findViewById<ListView>(R.id.sugsearchlist)
        button_search = findViewById<Button>(R.id.search)
        toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.title_toolbar)
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
    }

    private fun updateUI(){
        //猜测正确，显示箭头和其他全景信息
        if(!mode){
            textView_systemOutput?.text="随便走走吧"
            editText_city?.visibility = View.VISIBLE
            editText_address?.visibility = View.VISIBLE
            editText_guess?.hint = "想去哪？"
            button_search?.text = "去那看看"
            editText_guess?.isEnabled=false
            textView_introduce?.visibility = View.VISIBLE
            mPanaView?.setShowTopoLink(true)
            //去那看看按钮
            button_search?.setOnClickListener{
                goPlaceNameCity= editText_city?.text.toString()
                goPlaceNameAddress = editText_address?.text.toString()
                getTargetPoint(goPlaceNameCity,goPlaceNameAddress,this@GameMapActivity)
                //经纬度转换为全景
                Log.i("PanaTool", "change the panorama")
            }
            editText_city?.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    editText_city?.clearFocus()
                    true
                } else {
                    false
                }
            }
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
                    goPlaceNameAddress = s.toString()
                    Log.i("PanaTool", goPlaceNameAddress)
                    sugSearch(goPlaceNameCity,goPlaceNameAddress,this@GameMapActivity)
                }
            })
            editText_address?.setOnFocusChangeListener { v, hasFocus ->
                if(hasFocus){
                    //获取焦点时
                    listView?.visibility = View.VISIBLE
                } else {
                    //失去焦点时
                    listView?.visibility = View.GONE
                }
            }
            editText_address?.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //不点击联想框的时候
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
            editText_city?.visibility = View.GONE
            editText_address?.visibility = View.GONE
            editText_guess?.hint = "你知道这是哪吗?"
            button_search?.text = "是这吗"
            editText_guess?.isEnabled=true
            textView_introduce?.visibility = View.GONE
            mPanaView?.setShowTopoLink(false)
            //猜测
            editText_guess?.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    guessPlaceNameAddress = v.text.toString()
                    //隐藏键盘
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    editText_guess?.clearFocus()
                    true
                } else {
                    false
                }
            }
            //是这吗按钮
            button_search?.setOnClickListener {
                guessPlaceNameAddress = editText_guess?.text.toString()
                val result = wherePlaceNameCity
                //隐藏键盘
                handleGuess()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText_guess?.windowToken, 0)
                editText_guess?.clearFocus()
            }
        }
    }

    private fun gameStart(){
        //TODO:从数据库获取信息
        wherePlaceNameCity="南京"
        wherePlaceNameAddress="紫峰大厦"
        wherePlacePID="0900250012221008122348381AD"
        introduce="这是南京第一高楼"
        getTargetPoint(wherePlaceNameCity,wherePlaceNameAddress,this@GameMapActivity)
    }

    private fun handleGuess(){
        getGuessPoint(wherePlaceNameCity,guessPlaceNameAddress,this@GameMapActivity)
    }

    private fun leave(){
        mode=false
        updateUI()
    }

    private fun continueGame(){
        //TODO:从下一条数据库中下一条数据获取信息
        wherePlaceNameCity="南京"
        wherePlaceNameAddress="南京大学"
        wherePlacePID="09002500121709071035569301I"
        introduce="这是南京最高学府"
        getTargetPoint(wherePlaceNameCity,wherePlaceNameAddress,this@GameMapActivity)
    }
    private fun testPanoByType(type : Int) {
        mPanaView?.setShowTopoLink(true)
//        hideMarkerButton()
//        hideSeekLayout()
//        hideOtherLayout()
//        hideIndoorAblumLayout()

        // 测试回调函数,需要注意的是回调函数要在setPanorama()之前调用，否则回调函数可能执行异常

        // 测试回调函数,需要注意的是回调函数要在setPanorama()之前调用，否则回调函数可能执行异常
        mPanaView?.setPanoramaViewListener(object : PanoramaViewListener {
            override fun onLoadPanoramaBegin() {
                Log.i(toString(), "onLoadPanoramaStart...")
            }

            override fun onLoadPanoramaEnd(json: String) {
                Log.i(toString(), "onLoadPanoramaEnd : $json")
            }

            override fun onLoadPanoramaError(error: String) {
                Log.i(
                    toString(),
                    "onLoadPanoramaError : $error"
                )
            }

            override fun onDescriptionLoadEnd(json: String) {}
            override fun onMessage(msgName: String, msgType: Int) {}
            override fun onCustomMarkerClick(key: String) {}
            override fun onMoveStart() {}
            override fun onMoveEnd() {}
        })
        mPanaView?.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionHigh)
        val pid = "0900250012221008122348381AD"
        val uid = "bff8fa7deabc06b9c9213da4"
//        https://map.baidu.com/@13092628.138195531,3716087.11725552,6.96z,18.35t#panoid=fb2629b0a0f0afb4aeb22a4c&panotype=inter&heading=18&pitch=0&l=6.958152730952381&tn=B_NORMAL_MAP&sc=0&newmap=1&shareurl=1&pid=1000250001151117102651259IN&iid=fb2629b0a0f0afb4aeb22a4c
        mPanaView?.setPanorama(pid)
    }

    fun handleSuggestionResult(suggestionResult: SuggestionResult){
        //获取在线建议检索结果
        Log.i("PanaTool", "start find the suggestion")

        if (suggestionResult.error == SearchResult.ERRORNO.NO_ERROR) {
            //获取在线建议检索结果
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
                editText_address?.setText(suggestion.key)
                editText_address?.clearFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
    fun handleGeoCodeResultTarget(geoCodeResult : GeoCodeResult){
        //获取地理编码检索结果
        Log.i("PanaTool", "find the location")
        val latitude = geoCodeResult.location.latitude
        val longitude = geoCodeResult.location.longitude

        targetPoint = Point1(longitude,latitude)
        if(mode){
            mPanaView?.setPanorama(wherePlacePID)
        }else {
            mPanaView?.setPanorama(targetPoint!!.x, targetPoint!!.y)
        }
    }
    fun handleGeoCodeResultGuess(geoCodeResult : GeoCodeResult){
        //获取地理编码检索结果
        Log.i("PanaTool", "find the location")
        val latitude = geoCodeResult.location.latitude
        val longitude = geoCodeResult.location.longitude
        guessPoint = Point1(longitude,latitude)
        dtDistance= getDistance(Point2(guessPoint!!.x,guessPoint!!.y),Point2(targetPoint!!.x,targetPoint!!.y))
        val formattedDistance = String.format("%.2f", dtDistance*100)
        val direction = CoordinateTool.bearingToDirection(CoordinateTool.calculateBearing(guessPoint,targetPoint))
        if(guessPlaceNameAddress == wherePlaceNameAddress) {
            textView_systemOutput?.text = "回答正确:$wherePlaceNameAddress"
            //TODO:弹出提示框，继续下一题还是先随便走走
            isCorrect=true
            AlertDialog.Builder(this).apply {
                setTitle("This is Dialog")
                setMessage("恭喜你回答正确")
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
            textView_systemOutput?.text = "回答错误,请再试一次,$introduce"
            isCorrect=false
            //TODO:提示
//            textView_introduce?.text=introduce
            //TODO:提示滚动到下一条
            introduce="这里是鼓楼区"

        }
    }

    private fun handleAddClick() {
        //TODO:增加一个地点
        val myDB : DB_MyDatabaseHelper = DB_MyDatabaseHelper(this)

    }
    private fun handleMapClick() {
        //TODO:跳转到地图界面
        mode=!mode
        updateUI()
        //
        Toast.makeText(this, "跳转到地图界面", Toast.LENGTH_SHORT).show()
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
        super.onDestroy()
    }
}