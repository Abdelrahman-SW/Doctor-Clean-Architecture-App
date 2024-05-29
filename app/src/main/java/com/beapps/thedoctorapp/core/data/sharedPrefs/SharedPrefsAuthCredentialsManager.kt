package com.beapps.thedoctorapp.core.data.sharedPrefs

import android.content.Context
import com.beapps.thedoctorapp.core.domain.Doctor
import com.beapps.thedoctorapp.core.domain.AuthCredentialsManager

class SharedPrefsAuthCredentialsManager(private val context: Context) : AuthCredentialsManager {

    private val key = "my_prefs"
    private val sharedPref by lazy { context.getSharedPreferences(key, Context.MODE_PRIVATE) }
    private val editor by lazy { sharedPref.edit() }

    override fun saveDoctorCredentials(doctor: Doctor) {
        editor.putBoolean("isUserLoggedIn", true)
        editor.putString("name", doctor.name)
        editor.putString("id", doctor.id)
        editor.apply()
    }

    override fun deleteDoctorCredentials() {
        val sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isUserLoggedIn", false)
        editor.putString("name", "")
        editor.putString("id", "")
        editor.apply()
    }

    override fun isDoctorLoggedIn(): Boolean {
        val sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isUserLoggedIn", false)
    }

    override fun getCurrentLoggedInDoctor(): Doctor? {
        val sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        return if (!isDoctorLoggedIn()) null
        else {
            Doctor(
                name = sharedPref.getString("name", "") ?: "",
                id = sharedPref.getString("id", "") ?: ""
            )
        }
    }
}