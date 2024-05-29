package com.example.wheretogo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast

class BottomNavigationLayout (context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.bottomnavigation, this)
        val bottomNavigationLayout =
            findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationLayout.selectedItemId = R.id.home
        when (context.javaClass.simpleName) {
            "MainActivity" -> bottomNavigationLayout.menu.getItem(1).isChecked = true
            "GameMapActivity" -> bottomNavigationLayout.menu.getItem(2).isChecked = true
            "DB_MainActivity" -> bottomNavigationLayout.menu.getItem(0).isChecked = true
        }
        bottomNavigationLayout.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()
                    if(context.javaClass.simpleName == "MainActivity") {
                        Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()
                    }else{
                        ActivityCollector.removeActivity(context as BaseActivity)
                    }
                    true
                }

                R.id.map -> {
                    Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
                    GameMapActivity.actionStart(context,false,"data2")
                    true
                }

                R.id.add -> {
                    Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show()
                    DB_MainActivity.actionStart(context,"data1","data2")
                    true
                }

                else -> false
            }
        }
    }
}