package com.example.wheretogo

import android.app.Activity

/**
 * 单例类ActivityCollector，用于管理所有的活动
 */
object ActivityCollector {
    private val activities = ArrayList<Activity>()

    fun addActivity(activity: BaseActivity) {
        activities.add(activity)
    }

    fun removeActivity(activity: BaseActivity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
    }
}