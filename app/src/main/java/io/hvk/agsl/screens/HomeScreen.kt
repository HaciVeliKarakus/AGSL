package io.hvk.agsl.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.hvk.agsl.screens.home.ExpandableFAB
import io.hvk.agsl.screens.home.FractalAnimation
import io.hvk.agsl.screens.home.GradientShape
import io.hvk.agsl.screens.home.Lines
import io.hvk.agsl.screens.home.ProgressLoader
import io.hvk.agsl.screens.home.RippleImage
import io.hvk.agsl.screens.home.TextTranslate


object HomeScreen : Screen {
    private fun readResolve(): Any = HomeScreen

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        ContentUI { item ->
            navigator.push(item)
        }
    }
}

@Composable
private fun ContentUI(show: (Screen) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Item("gradient color shape") { show(GradientShape) }
        Item("Fractal Animation") { show(FractalAnimation) }
        Item("Ripple image Animation") { show(RippleImage) }
        Item("Progress Loader") { show(ProgressLoader) }
        Item("Expandable Fab") { show(ExpandableFAB) }
        Item("Text Translate") { show(TextTranslate) }
        Item("Lines") { show(Lines) }
    }
}

@Composable
fun Item(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, "")
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    ContentUI {}
}