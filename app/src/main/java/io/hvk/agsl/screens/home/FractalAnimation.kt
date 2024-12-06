package io.hvk.agsl.screens.home

import android.graphics.RuntimeShader
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import io.hvk.agsl.component.ShaderImage

private const val SHADER_SRC = """
    uniform float2 size;
    uniform float time;
    uniform shader composable;
    
    float f(float3 p) {
        p.z -= time * 5.;
        float a = p.z * .1;
        p.xy *= mat2(cos(a), sin(a), -sin(a), cos(a));
        return .1 - length(cos(p.xy) + sin(p.yz));
    }
    
    half4 main(float2 fragcoord) { 
        float3 d = .5 - fragcoord.xy1 / size.y;
        float3 p=float3(0);
        for (int i = 0; i < 32; i++) {
          p += f(p) * d;
        }
        return ((sin(p) + float3(2, 5, 12)) / length(p)).xyz1;
    }
"""


private val shader = RuntimeShader(SHADER_SRC)

object FractalAnimation : Screen {
    private fun readResolve(): Any = FractalAnimation

    @Composable
    override fun Content() {
        ShaderImage(shader)
    }
}



