package com.example.wheretogo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.baidu.lbsapi.BMapManager
import com.baidu.lbsapi.panoramaview.*

class MapActivity : BaseActivity() {
    companion object{
        //TODO:需要传入地图界面的数据
        fun actionStart(context: Context, data1: String, data2: String){
            val intent = Intent(context, MapActivity::class.java).apply {
                putExtra("param1", data1)
                putExtra("param2", data2)
            }
            context.startActivity(intent)
        }
    }
    var mBmapManager: BMapManager? = null
    var mPanaView: PanoramaView? = null
    var editText: EditText? = null
    var textView: TextView? = null
    var inputPlaceName : String = "北京 北京市海淀区上地十街10号"
    var pid : Number? = null
    var inputPoint : Point? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_layout)
        initView()
        val intent = intent
        if (intent != null) {
            testPanoByType(intent.getIntExtra("type", -1))
        }

        editText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                inputPlaceName = v.text.toString()
                editText!!.text.clear()
                inputPoint = inputPlaceName.let { CoordinateTool.nameToPid(it) }
//                textView?.text= inputPoint!!.point.toString()
                Log.i(MapActivity.toString(), textView?.text.toString())
                true
            } else {
                false
            }
        }
        //输入地名转换为相关坐标


//        CoordinateTool.nameToPid(inputPlaceName)
        //根据坐标显示全景图
//        PanaTool.changePanaView(pid)
    }
    private fun initView() {
        mPanaView = findViewById<View>(R.id.panorama) as PanoramaView
        editText = findViewById<EditText>(R.id.panodemo_main_input)
        textView = findViewById<TextView>(R.id.panodemo_main_output)
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
                Log.i(com.example.wheretogo.MapActivity.toString(), "onLoadPanoramaStart...")
            }

            override fun onLoadPanoramaEnd(json: String) {
                Log.i(com.example.wheretogo.MapActivity.toString(), "onLoadPanoramaEnd : $json")
            }

            override fun onLoadPanoramaError(error: String) {
                Log.i(
                    com.example.wheretogo.MapActivity.toString(),
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
        super.onDestroy()
    }
}