package com.example.wheretogo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wheretogo.databinding.GameLayoutBinding
import com.example.wheretogo.ui.theme.WhereToGoTheme

class GameActivity : BaseActivity() {
    /**
     * GameActivity的启动方法
     */
    companion object{
        //TODO:需要传入游戏界面的数据
        fun actionStart(context: Context, data1: String, data2: String){
            val intent = Intent(context, GameActivity::class.java).apply {
                putExtra("param1", data1)
                putExtra("param2", data2)
            }
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = GameLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("GameActivity", "draw game layout")
        binding.backLaunch.setOnClickListener {
            //把数据存到暂存区
//            val resultIntent = Intent()
//            //TODO:存储游戏数据
//            resultIntent.putExtra("result", "GameActivity finished")
//            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}