package com.example.wheretogo

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.wheretogo.R

class TitleLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.title, this)
        val titleBack = findViewById<Button>(R.id.titleBack)
        val activity = context as Activity
        val titleText = findViewById<TextView>(R.id.titleText)
        when (activity.javaClass.simpleName) {
            "MainActivity" -> titleText.text = "Menu"
            "SettingActivity" -> titleText.text = "Setting"
            "IntroduceActivity" -> titleText.text = "Introduce"
            "GameActivity" -> titleText.text = "Game"
        }

        titleBack.setOnClickListener {
            Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show()
            Log.d(activity.toString(), "Back")
            activity.finish()
        }
    }
}