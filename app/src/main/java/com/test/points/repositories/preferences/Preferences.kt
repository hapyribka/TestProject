package com.test.points.repositories.preferences

interface Preferences {
    fun savePointsCount(value: String)

    fun getSavedPointsCount(): String

    companion object {
        internal const val PREF_TEST = "TEST_PREFS"
        internal const val PREF_POINTS_COUNT = "POINTS_COUNT_STING"
    }
}
