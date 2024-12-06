package io.hvk.agsl.screens.home

import android.graphics.RuntimeShader
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen

private const val COLOR_SHADER_SRC =
    """uniform float2 iResolution;
   half4 main(float2 fragCoord) {
   float2 scaled = fragCoord/iResolution.xy;
   return half4(scaled, 0, 1);
}"""

private val colorShader = RuntimeShader(COLOR_SHADER_SRC)
private val shaderBrush = ShaderBrush(colorShader)


object GradientShape : Screen {
    private fun readResolve(): Any = GradientShape

    @Composable
    override fun Content() {
        ContentUI()
    }

}

@Composable
private fun ContentUI() {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        colorShader.setFloatUniform(
            "iResolution",
            size.width, size.height
        )
        drawCircle(brush = shaderBrush)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ContentUI()
}