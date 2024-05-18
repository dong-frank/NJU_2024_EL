package com.example.wheretogo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.baidu.lbsapi.BMapManager
import com.baidu.lbsapi.panoramaview.PanoramaView
import com.baidu.lbsapi.panoramaview.PanoramaViewListener
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.GeoCodeResult
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.example.wheretogo.PanaTool.getGuessPoint
import com.example.wheretogo.PanaTool.getTargetPoint
import com.example.wheretogo.PanaTool.sugSearch
import com.baidu.mapapi.model.CoordUtil.getDistance
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
    var mBmapManager: BMapManager? = null
    var mPanaView: PanoramaView? = null
    var mSuggestionSearch : SuggestionSearch? = null
    var editText_city: EditText? = null
    var editText_address: EditText? = null
    var editText_guess: EditText? = null
    var textView: TextView? = null
    var textView_systemOutput: TextView? = null
    var listView: ListView? = null
    var button_search : Button? = null
    var inputPlaceNameCity : String = "北京"
    var inputPlaceNameAddress : String = "海淀区上地十街10号"
    var guessPlaceNameCity : String ?= null
    var dtDistance : Double = 0.0
    var targetPoint : Point1? = null
    var guessPoint : Point1? = null
    @SuppressLint("SetTextI18n", "ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_map_layout)
        initView()
        val intent = intent
        if (intent != null) {
            testPanoByType(intent.getIntExtra("type", -1))
        }

        editText_city?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                inputPlaceNameCity = v.text.toString()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                editText_city?.clearFocus()
                true
            } else {
                false
            }
        }
        //监听输入框文本变化
        //TODO:联想框不可见
        editText_address?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // 输入文字结束时
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 输入文字前
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 输入文字中
                inputPlaceNameAddress = s.toString()
                Log.i("PanaTool", inputPlaceNameAddress)
                sugSearch(inputPlaceNameCity,inputPlaceNameAddress,this@GameMapActivity)
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
        button_search?.setOnClickListener{
            inputPlaceNameCity= editText_city?.text.toString()
            inputPlaceNameAddress = editText_address?.text.toString()
            getTargetPoint(inputPlaceNameCity,inputPlaceNameAddress,this@GameMapActivity)
            //TODO:经纬度转换为全景
            Log.i("PanaTool", "change the panorama")
            textView?.text = inputPlaceNameAddress
            editText_address?.clearFocus()
        }
        editText_guess?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                guessPlaceNameCity = v.text.toString()
                val result = inputPlaceNameCity
                getGuessPoint(guessPlaceNameCity,guessPlaceNameCity,this@GameMapActivity)
                //隐藏键盘
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                editText_guess!!.text.clear()
                true
            } else {
                false
            }
        }
    }
    private fun initView() {
        mPanaView = findViewById<View>(R.id.panorama) as PanoramaView
        editText_city = findViewById<EditText>(R.id.panodemo_main_input_city)
        editText_address = findViewById<EditText>(R.id.panodemo_main_input_address)
        editText_guess = findViewById<EditText>(R.id.user_guess)
        textView = findViewById<TextView>(R.id.panodemo_main_output)
        textView_systemOutput = findViewById<TextView>(R.id.system_output)
        listView = findViewById<ListView>(R.id.sugsearchlist)
        button_search = findViewById<Button>(R.id.search)
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
        val pid = "0100220000130817164838355J5"
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
        mPanaView?.setPanorama(targetPoint!!.x,targetPoint!!.y)
    }
    fun handleGeoCodeResultGuess(geoCodeResult : GeoCodeResult){
        //获取地理编码检索结果
        Log.i("PanaTool", "find the location")
        val latitude = geoCodeResult.location.latitude
        val longitude = geoCodeResult.location.longitude
        guessPoint = Point1(longitude,latitude)
        dtDistance= getDistance(Point2(guessPoint!!.x,guessPoint!!.y),Point2(targetPoint!!.x,targetPoint!!.y))
        val formattedDistance = String.format("%.2f", dtDistance*100)
        if(guessPlaceNameCity == inputPlaceNameCity) {
            textView_systemOutput?.text = "回答正确:$inputPlaceNameCity"
        } else {
            textView_systemOutput?.text = "回答错误,请再试一次.和正确位置相差:$formattedDistance 千米"
        }
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