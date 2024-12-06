package io.hvk.agsl.component

import android.graphics.BitmapFactory
import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import io.hvk.agsl.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ShaderImage(shader: RuntimeShader) {
    val scope = rememberCoroutineScope()
    val timeMs = remember { mutableFloatStateOf(0f) }
    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                timeMs.floatValue = (System.currentTimeMillis() % 100_000L) / 1_000f
                delay(10)
            }
        }
    }
    val context = LocalContext.current

    val photo = BitmapFactory.decodeResource(context.resources, R.drawable.img)

    Image(
        bitmap = photo.asImageBitmap(),
        modifier = Modifier
            .onSizeChanged { size ->
                shader.setFloatUniform(
                    "size",
                    size.width.toFloat(),
                    size.height.toFloat()
                )
            }
            .graphicsLayer {
                clip = true
                shader.setFloatUniform("time", timeMs.floatValue)
                renderEffect =
                    RenderEffect
                        .createRuntimeShaderEffect(shader, "composable")
                        .asComposeRenderEffect()
            },
        contentScale = ContentScale.FillHeight,
        contentDescription = null,
    )
}