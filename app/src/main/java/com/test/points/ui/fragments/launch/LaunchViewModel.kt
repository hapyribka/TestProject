package com.test.points.ui.fragments.launch

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.test.points.R
import com.test.points.ui.fragments.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class LaunchViewModel() : BaseViewModel() {
    fun startTimer(navController: NavController) {
        viewModelScope.launch {
            delay(DURATION)
            if (isActive) {
                navController.navigate(R.id.action_LaunchFragment_to_PointsFragment)
            }
        }
    }

    companion object {
        private const val DURATION = 3000L
    }
}
