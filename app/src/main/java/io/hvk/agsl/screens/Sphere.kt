package io.hvk.agsl.screens

import android.graphics.RuntimeShader
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import io.hvk.agsl.component.ShaderImage

private const val SHADER_SRC = """
    uniform shader composable;
    uniform float2 iResolution;
    uniform float iTime;
    
    float sphere(float2 p, float r) {
        return length(p) - r;
    }
    
    float3 palette(float t) {
        float3 a = float3(0.5, 0.5, 0.5);
        float3 b = float3(0.5, 0.5, 0.5);
        float3 c = float3(1.0, 1.0, 1.0);
        float3 d = float3(0.263, 0.416, 0.557);
        return a + b * cos(6.28318 * (c * t + d));
    }
    
    half4 main(float2 fragCoord) {
        float2 uv = (fragCoord - iResolution.xy * 0.5) / min(iResolution.x, iResolution.y);
        
        float d = sphere(uv, 0.4);
        
        float3 col = float3(0.0);
        
        if (d < 0.0) {
            float3 normal = normalize(float3(uv, sqrt(0.16 - dot(uv, uv))));
            float light = dot(normal, normalize(float3(sin(iTime), cos(iTime), 1.0)));
            
            // Renk paleti ile renklendirme
            float colorIndex = (light * 0.5 + 0.5 + iTime * 0.2);
            col = palette(colorIndex) * (0.5 + 0.5 * light);
            
            // Parlama efekti
            float specular = pow(max(0.0, light), 32.0);
            col += float3(specular);
        }
        
        // Kenar yumuşatma
        float edge = smoothstep(-0.01, 0.01, d);
        col = mix(col, float3(0.0), edge);
        
        return half4(col, 1.0);
    }
"""
private val shader = RuntimeShader(SHADER_SRC)

object Sphere : Screen {
    private fun readResolve(): Any = Sphere

    @Composable
    override fun Content() {
        ShaderImage(shader)
    }
}