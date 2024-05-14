package com.example.wheretogo

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.lbsapi.BMapManager
import com.baidu.lbsapi.MKGeneralListener
import com.example.wheretogo.databinding.LaunchLayoutBinding

/**
 * MainActivity is the entry point of the app.
 * It is the first screen that the user sees when they launch the app.
    */
class MainActivity : BaseActivity() {
    var mBMapManager: BMapManager? = null
//    val context = applicationContext
    private var btnPrivacy: Button? = null
    /**
     * 获取返回数据
     */
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    val returneddata = data?.getStringExtra("data_return")
                    Log.d("MainActivity", "onActivityResult: $returneddata")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = LaunchLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MainActivity", "draw launch layout")

        btnPrivacy = findViewById<View>(R.id.request_permission) as Button
        btnPrivacy!!.tag = false
        //TODO:主菜单按键处理


        binding.startGame.setOnClickListener {
            GameActivity.actionStart(this, "data1", "data2")//启动游戏界面,data1和data2是传入的数据
            Log.d("MainActivity", "Game Start and go to GameActivity")
        }

        binding.introduce.setOnClickListener {
            IntroduceActivity.actionStart(this, "data1", "data2")//启动介绍界面,data1和data2是传入的数据
            Log.d("MainActivity", "Introduce and go to IntroduceActivity")
        }
        binding.map.setOnClickListener {
            intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
            Log.d("MainActivity", "Setting and go to PresentMapActivity")
        }
        binding.exit.setOnClickListener {
            Toast.makeText(this, "EXIT!", Toast.LENGTH_SHORT).show()
            ActivityCollector.finishAll()
            Log.d("MainActivity", "Exit")
        }
        binding.requestPermission.setOnClickListener {
            //TODO:权限申请和初始化
            initMap()
            requestPermission()
//            MapActivity.actionStart(this, "data1", "data2")//启动地图界面,data1和data2是传入的数据
        }

    }
    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        // 建议在APP整体退出之前调用MapApi的destroy()函数，不要在每个activity的OnDestroy中调用，
        // 避免MapApi重复创建初始化，提高效率
        if (mBMapManager != null) {
            // mBMapManager.destroy();
            mBMapManager = null
        }
        super.onDestroy()
        System.exit(0)
    }

    private fun requestPermission(){

        //运行时权限检查
        if ((ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_PHONE_STATE
            )
                    != PackageManager.PERMISSION_GRANTED) || ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.READ_PHONE_STATE
                ) &&
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf<String>(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
                )

                // MY_PERMISSIONS_REQUEST_READ_PHONE_STATE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
        if(mBMapManager!=null){
            btnPrivacy!!.setEnabled(false)
            val tag = btnPrivacy!!.tag as Boolean
            mBMapManager!!.setAgreePrivacy(this.applicationContext, !tag)
            Thread { mBMapManager!!.init(MyGeneralListener()) }.start()
            btnPrivacy!!.text = "隐私" + " " + !tag
            btnPrivacy!!.tag = !tag

        }
    }

    private fun initMap(){
        //初始化地图
        if (mBMapManager == null) {
            mBMapManager = BMapManager(this.applicationContext)
        }
    }

    internal class MyGeneralListener : MKGeneralListener {
        override fun onGetPermissionState(iError: Int) {
            // 非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
                Log.d("MainActivity","wrong")
            } else {
                //授权成功
                Log.d("MainActivity","successful")
            }
        }
    }

}


