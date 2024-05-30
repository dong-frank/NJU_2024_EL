package com.example.wheretogo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast

/**
 * 底部导航栏控制类
 */
class BottomNavigationLayout (context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.bottomnavigation, this)
        val bottomNavigationLayout =
            findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation_bar)



    }
}