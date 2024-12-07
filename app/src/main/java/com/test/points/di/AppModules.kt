package com.test.points.di

import android.app.Application
import com.test.points.repositories.network.NetworkApi
import com.test.points.repositories.network.NetworkApiImpl
import com.test.points.repositories.preferences.Preferences
import com.test.points.repositories.preferences.PreferencesImpl
import com.test.points.ui.fragments.chart.ChartViewModel
import com.test.points.ui.fragments.launch.LaunchViewModel
import com.test.points.ui.fragments.points.PointsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

object AppModules {
    fun initModules(context: Application) {
        val viewModelModules =
            module {
                viewModel { LaunchViewModel() }
                viewModel { PointsViewModel(get(), get()) }
                viewModel { ChartViewModel() }
            }
        val repositoryModules =
            module {
                single<Preferences> { PreferencesImpl(context) }
                single<NetworkApi> { NetworkApiImpl() }
            }
        startKoin {
            context
            modules(
                listOf(
                    viewModelModules,
                    repositoryModules
                )
            )
        }
    }
}
