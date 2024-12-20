@file:OptIn(ExperimentalAnimationApi::class)

package io.hvk.agsl.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import io.hvk.agsl.component.MetaContainer
import io.hvk.agsl.component.MetaEntity

const val original = "Bye"
const val translated = "Tschüs"

object TextTranslate : Screen {
    private fun readResolve(): Any = TextTranslate

    @Composable
    override fun Content() {
        ContentUI()
    }

}

@Composable
private fun ContentUI() {
    var text by remember {
        mutableStateOf(original)
    }

    val time = 1000

    val blur = remember { Animatable(0f) }

    LaunchedEffect(text) {
        blur.animateTo(30f, tween(time / 2, easing = LinearEasing))
        blur.animateTo(0f, tween(time / 2, easing = LinearEasing))
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        MetaContainer(
            modifier = Modifier
                .animateContentSize()
                .clipToBounds()
                .fillMaxWidth(),
            cutoff = .2f
        ) {
            MetaEntity(
                modifier = Modifier.fillMaxWidth(),
                blur = blur.value,
                metaContent = {
                    AnimatedContent(
                        targetState = text,
                        modifier = Modifier
                            .fillMaxWidth(),
                        transitionSpec = {
                            fadeIn(tween(time, easing = LinearEasing)) + expandVertically(
                                tween(
                                    time,
                                    easing = LinearEasing
                                ), expandFrom = Alignment.CenterVertically
                            ) with fadeOut(
                                tween(
                                    time,
                                    easing = LinearEasing
                                )
                            ) + shrinkVertically(
                                tween(
                                    time,
                                    easing = LinearEasing
                                ), shrinkTowards = Alignment.CenterVertically
                            )
                        }
                    ) { text ->
                        Text(
                            text,
                            modifier = Modifier.fillMaxWidth(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 50.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                }) {}
        }
        Button(onClick = {
            text = if (text == original) translated else original
        }) {
            Text("Translate text")
        }
    }
}