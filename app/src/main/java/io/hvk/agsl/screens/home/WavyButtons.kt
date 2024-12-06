package io.hvk.agsl.screens.home

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import io.hvk.agsl.component.onPressWavy

object WavyButtons:Screen {
    private fun readResolve(): Any = WavyButtons

    @Composable
    override fun Content() {

    }
}