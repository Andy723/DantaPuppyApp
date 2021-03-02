package com.example.androiddevchallenge

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.data.Puppy
import com.example.jetnews.utils.getMutableStateOf

enum class ScreenName { HOME, DETAIL }

sealed class Screen(val id: ScreenName) {
    object HomeScreen : Screen(ScreenName.HOME)
    data class DetailsScreen(val puppy: Puppy) : Screen(ScreenName.DETAIL)
}

private const val SIS_SCREEN = "sis_screen"
private const val SIS_NAME = "screen_name"
private const val SIS_PUPPY = "puppy"


private fun Screen.toBundle(): Bundle {
    return bundleOf(SIS_NAME to id.name).also {
        // add extra keys for various types here
        if (this is Screen.DetailsScreen) {
            it.putSerializable(
                SIS_PUPPY,
                puppy
            )
        }
    }
}


private fun Bundle.getStringOrThrow(key: String) =
    requireNotNull(getString(key)) { "Missing key '$key' in $this" }

private fun Bundle.toScreen(): Screen {
    val screenName = ScreenName.valueOf(getStringOrThrow(SIS_NAME))
    return when (screenName) {
        ScreenName.HOME -> Screen.HomeScreen
        ScreenName.DETAIL -> {
            val puppy = getSerializable(SIS_PUPPY) as Puppy
            Screen.DetailsScreen(puppy)
        }
    }
}

class NavigationViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    var currentScreen: Screen by savedStateHandle.getMutableStateOf<Screen>(
        key = SIS_SCREEN,
        default = Screen.HomeScreen,
        save = { it.toBundle() },
        restore = { it.toScreen() }
    )
    @MainThread
    fun onBack(): Boolean {
        val wasHandled = currentScreen != Screen.HomeScreen
        currentScreen = Screen.HomeScreen
        return wasHandled
    }


    @MainThread
    fun navigateTo(screen: Screen) {
        currentScreen= screen
    }
}