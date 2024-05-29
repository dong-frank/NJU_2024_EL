package com.example.wheretogo

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class TitleLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.title, this)
        val activity = context as Activity
        val title_toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.title_toolbar)
        when (activity.javaClass.simpleName) {
            "MainActivity" -> title_toolbar.setTitle("鼠鼠去那儿")
            "GameMapActivity" -> {
                title_toolbar.setTitle("你能猜到鼠鼠去哪了吗")
                title_toolbar.inflateMenu(R.menu.toolbar_menu)
            }
        }
    }


}