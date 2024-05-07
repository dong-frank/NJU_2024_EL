package com.example.wheretogo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wheretogo.databinding.LaunchLayoutBinding
import com.example.wheretogo.ui.theme.WhereToGoTheme
/**
 * MainActivity is the entry point of the app.
 * It is the first screen that the user sees when they launch the app.
    */
class MainActivity : BaseActivity() {
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




        //TODO:主菜单按键处理



        binding.startGame.setOnClickListener {
            GameActivity.actionStart(this, "data1", "data2")//启动游戏界面,data1和data2是传入的数据
            Log.d("MainActivity", "Game Start and go to GameActivity")
        }

        binding.introduce.setOnClickListener {
            IntroduceActivity.actionStart(this, "data1", "data2")//启动介绍界面,data1和data2是传入的数据
            Log.d("MainActivity", "Introduce and go to IntroduceActivity")
        }
        binding.setting.setOnClickListener {
            SettingActivity.actionStart(this, "data1", "data2")//启动设置界面,data1和data2是传入的数据
            Log.d("MainActivity", "Setting and go to SettingActivity")
        }
        binding.exit.setOnClickListener {
            Toast.makeText(this, "EXIT!", Toast.LENGTH_SHORT).show()
            ActivityCollector.finishAll()
            Log.d("MainActivity", "Exit")
        }
    }

}
