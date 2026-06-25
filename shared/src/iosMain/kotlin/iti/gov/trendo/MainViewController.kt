package iti.gov.trendo

import androidx.compose.ui.window.ComposeUIViewController
import iti.gov.trendo.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}