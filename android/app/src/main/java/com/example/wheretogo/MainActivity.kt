package com.example.wheretogo

import android.Manifest
import android.R
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.lbsapi.BMapManager
import com.baidu.lbsapi.MKGeneralListener
import com.example.wheretogo.databinding.LaunchLayoutBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException


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
    var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = LaunchLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MainActivity", "draw launch layout")

        PravicyCheck()

        btnPrivacy = findViewById<View>(com.example.wheretogo.R.id.request_permission) as Button
        btnPrivacy!!.tag = false
        //TODO:主菜单按键处理


        binding.startGame.setOnClickListener {
            GameMapActivity.actionStart(this, "data1", "data2")//启动游戏界面,data1和data2是传入的数据
            Log.d("MainActivity", "Game Start and go to GameActivity")
        }
        binding.sqlite.setOnClickListener {
            val intent =Intent(this, DB_MainActivity::class.java)
            startActivity(intent)
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
        }

    }
    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    fun onClickAgree(v: View?) {
        //TODO:权限申请和初始化
        initMap()
        requestPermission()
        dialog!!.dismiss()
        //下面将已阅读标志写入文件，再次启动的时候判断是否显示。
        getSharedPreferences("file", MODE_PRIVATE).edit()
            .putBoolean("AGREE", true)
            .apply()
    }

    fun onClickDisagree(v: View?) {
        System.exit(0) //退出软件
    }

    fun showPrivacy(privacyFileName: String?) {
        val str = initAssets(privacyFileName)
        val inflate : View = LayoutInflater.from(this@MainActivity).inflate(com.example.wheretogo.R.layout.dialog_privacy_show, null)
        val tv_title = inflate.findViewById<View>(com.example.wheretogo.R.id.tv_title) as TextView
        tv_title.text = "隐私政策授权提示"
        val tv_content = inflate.findViewById<View>(com.example.wheretogo.R.id.tv_content) as TextView
        tv_content.text = str
        dialog = AlertDialog.Builder(this@MainActivity)
            .setView(inflate)
            .show()
        // 通过WindowManager获取
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val params = dialog!!.window!!.attributes
        params.width = dm.widthPixels * 4 / 5
        params.height = dm.heightPixels * 1 / 2
        dialog!!.setCancelable(false) //屏蔽返回键
        dialog!!.window!!.setAttributes(params)
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
    }

    /**
     * 从assets下的txt文件中读取数据
     */
    fun initAssets(fileName: String?): String? {
        var str: String? = null
        try {
            val inputStream = assets.open(fileName!!)
            str = getString(inputStream)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return str
    }

    fun getString(inputStream: InputStream?): String {
        var inputStreamReader: InputStreamReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream, "UTF-8")
        } catch (e1: UnsupportedEncodingException) {
            e1.printStackTrace()
        }
        val reader = BufferedReader(inputStreamReader)
        val sb = StringBuffer("")
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
                sb.append("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sb.toString()
    }

    fun PravicyCheck() {
        val status = getSharedPreferences("file", MODE_PRIVATE)
            .getBoolean("AGREE", false)
        if (status != true) {
        } else {
            showPrivacy("privacy.txt") //放在assets目录下的隐私政策文本文件
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


