package com.example.wheretogo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.wheretogo.databinding.SettingLayoutBinding

class SettingActivity : BaseActivity() {
    /**
     * SettingActivity的启动方法
     */
     companion object {
        fun actionStart(context: Context, data1: String, data2: String) {
            val intent = Intent(context, SettingActivity::class.java).apply {
                putExtra("param1", data1)
                putExtra("param2", data2)
            }
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = SettingLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("SettingActivity", "draw setting layout")
    }
}