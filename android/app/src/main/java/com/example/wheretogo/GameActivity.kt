package com.example.wheretogo

import android.content.Intent
import android.os.Bundle
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

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_layout)

        val binding = GameLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backLaunch.setOnClickListener {
            //把数据存到暂存区
            val resultIntent = Intent()
            //TODO:存储游戏数据
            resultIntent.putExtra("result", "GameActivity finished")
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}