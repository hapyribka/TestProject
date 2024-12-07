package com.test.points.repositories.preferences

import android.content.Context
import com.test.points.repositories.preferences.Preferences.Companion.PREF_POINTS_COUNT
import com.test.points.repositories.preferences.Preferences.Companion.PREF_TEST

class PreferencesImpl(private val context: Context) : Preferences {
    override fun savePointsCount(value: String) {
        val prefsEditor = context.getSharedPreferences(PREF_TEST, Context.MODE_PRIVATE).edit()
        prefsEditor.putString(PREF_POINTS_COUNT, value)
        prefsEditor.apply()
    }

    override fun getSavedPointsCount(): String {
        return context.getSharedPreferences(PREF_TEST, Context.MODE_PRIVATE)
            .getString(PREF_POINTS_COUNT, DEFAULT_POINTS_COUNT) ?: DEFAULT_POINTS_COUNT
    }

    companion object {
        private const val DEFAULT_POINTS_COUNT = "100"
    }
}
