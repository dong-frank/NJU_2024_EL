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
import com.example.wheretogo.databinding.IntroduceLayoutBinding
import com.example.wheretogo.ui.theme.WhereToGoTheme

class IntroduceActivity : BaseActivity() {
    /**
     * IntroduceActivity的启动方法
     */
    companion object {
        fun actionStart(context: Context, data1: String, data2: String) {
            val intent = Intent(context, IntroduceActivity::class.java).apply {
                putExtra("param1", data1)
                putExtra("param2", data2)
            }
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = IntroduceLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("IntroduceActivity", "draw introduce layout")
    }
}