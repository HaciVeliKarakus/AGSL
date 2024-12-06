package io.hvk.agsl.screens.home

import android.graphics.RuntimeShader
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import io.hvk.agsl.component.ShaderImage


private const val SHADER_SRC = """
    uniform float2 size;
    uniform float time;
    uniform shader composable;
    
    half4 main(float2 fragCoord) {
        float scale = 1 / size.x;
        float2 scaledCoord = fragCoord * scale;
        float2 center = size * 0.5 * scale;
        float dist = distance(scaledCoord, center);
        float2 dir = scaledCoord - center;
        float sin = sin(dist * 70 - time * 6.28);
        float2 offset = dir * sin;
        float2 textCoord = scaledCoord + offset / 30;
        return composable.eval(textCoord / scale);
    }
"""


private val shader = RuntimeShader(SHADER_SRC)

object RippleImage: Screen {
    private fun readResolve(): Any = RippleImage

    @Composable
    override fun Content() {
        ShaderImage(shader)
    }
}