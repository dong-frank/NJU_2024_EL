package com.example.wheretogo

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
import com.baidu.location.LocationClient
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapView
import com.example.wheretogo.ui.theme.WhereToGoTheme

class MapActivity : BaseActivity() {
    val locationClient = LocationClient(this)
    val mapView = MapView(this)
    val baiduMap = mapView.map
    var isFirstLoc = true

    override fun onCreate(savedInstanceState: Bundle?) {

    }
}