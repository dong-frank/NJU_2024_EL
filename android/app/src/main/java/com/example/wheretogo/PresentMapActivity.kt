package com.example.wheretogo

import android.content.Context
import android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
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
import com.baidu.lbsapi.BMapManager
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapView
import com.example.wheretogo.databinding.MapLayoutBinding
import com.example.wheretogo.databinding.PresentmapLayoutBinding
import com.example.wheretogo.ui.theme.WhereToGoTheme

class PresentMapActivity : BaseActivity() {
    lateinit var baiduMap: BaiduMap
    private lateinit var binding: PresentmapLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SDKInitializer.setAgreePrivacy(applicationContext,true)
        SDKInitializer.initialize(applicationContext)
        SDKInitializer.setCoordType(CoordType.BD09LL)
        binding = PresentmapLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        baiduMap = binding.bmapView.getMap()
        baiduMap.isMyLocationEnabled = true
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        binding.bmapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.bmapView.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        binding.bmapView.onDestroy()
    }
}
