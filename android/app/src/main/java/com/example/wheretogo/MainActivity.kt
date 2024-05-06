package com.example.wheretogo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launch_layout)
        Log.d("MainActivity", "draw launch layout")

        val binding = LaunchLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //TODO:主菜单按键处理

        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                //TODO:处理游戏结束后的数据
                Log.d("MainActivity", "onGameActivityResult: ${data?.getStringExtra("result")}")
            }
        }
        binding.startGame.setOnClickListener {
            Toast.makeText(this, "GAME START!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, GameActivity::class.java)
            startForResult.launch(intent)
            Log.d("MainActivity", "Game Start and go to GameActivity")
        }

        binding.introduce.setOnClickListener {
            Toast.makeText(this, "INTRODUCE!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, IntroduceActivity::class.java)
            startActivity(intent)
            Log.d("MainActivity", "Introduce and go to IntroduceActivity")
        }

        binding.exit.setOnClickListener {
            Toast.makeText(this, "EXIT!", Toast.LENGTH_SHORT).show()
            finish()
            Log.d("MainActivity", "Exit")
        }
    }

}
