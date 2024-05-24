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
//        val titleBack = findViewById<Button>(R.id.titleBack)
        val activity = context as Activity
        val title_toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.title_toolbar)
        title_toolbar.inflateMenu(R.menu.toolbar_menu)
//        val titleText = findViewById<TextView>(R.id.titleText)
        when (activity.javaClass.simpleName) {
            "MainActivity" -> title_toolbar.setTitle("鼠鼠去那儿")
            "GameMapActivity" -> title_toolbar.setTitle("猜猜鼠鼠去了哪儿")
        }
//
//        titleBack.setOnClickListener {
//            Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show()
//            Log.d(activity.toString(), "Back")
//            activity.finish()
//        }
    }
}