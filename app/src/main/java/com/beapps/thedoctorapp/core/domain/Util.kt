package com.beapps.thedoctorapp.core.domain

import android.content.Context
import com.beapps.thedoctorapp.auth.domain.Doctor

object Util {
    fun saveDoctorCredentials(context: Context, doctor: Doctor) {
        val sharedPref = context.getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isUserLoggedIn", true)
        editor.putString("name", doctor.name)
        editor.putString("id", doctor.id)
        editor.apply()
    }

    fun deleteDoctorCredentials(context: Context) {
        val sharedPref = context.getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isUserLoggedIn", false)
        editor.putString("name", "")
        editor.putString("id", "")
        editor.apply()
    }

    private fun isDoctorLoggedIn(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isUserLoggedIn", false)
    }

    fun getCurrentLoggedInDoctor(context: Context): Doctor? {
        val sharedPref = context.getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE)
        return if (!isDoctorLoggedIn(context)) null
        else {
            Doctor(
                name = sharedPref.getString("name", "") ?: "",
                id = sharedPref.getString("id", "") ?: ""
            )
        }
    }
}