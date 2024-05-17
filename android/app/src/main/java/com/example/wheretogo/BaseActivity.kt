package com.example.wheretogo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SDKInitializer.setAgreePrivacy(applicationContext,true)
        SDKInitializer.initialize(applicationContext)


        Log.d("BaseActivity", javaClass.simpleName)
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
        Log.d("BaseActivity", "Destroy $javaClass.simpleName")
    }
}