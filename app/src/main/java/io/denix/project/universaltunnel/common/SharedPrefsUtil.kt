package io.denix.project.universaltunnel.common

import android.content.Context

class SharedPrefsUtil {

    fun getLoginUserId(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences("UT", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("login_user", 0)
    }

    fun setLoginUserId(context: Context, userId: Int) {
        val sharedPreferences = context.getSharedPreferences("UT", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putInt("login_user", userId)
        editor.commit()
    }

    fun removeLoginUserId(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UT", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.remove("login_user")
    }

    fun clearAllPrefs(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UT", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.clear()
    }
}