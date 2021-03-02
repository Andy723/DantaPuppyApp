/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.utils.getMutableStateOf

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
        currentScreen = screen
    }
}