//package io.hvk.agsl
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScope
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.RenderEffect
//import androidx.compose.ui.graphics.graphicsLayer
//
//
//const val ShaderSource = """
//    uniform shader composable;
//
//    uniform float cutoff;
//    uniform float3 rgb;
//
//    half4 main(float2 fragCoord) {
//        half4 color = composable.eval(fragCoord);
//        color.a = step(cutoff, color.a);
//        if (color == half4(0.0, 0.0, 0.0, 1.0)) {
//            color.rgb = half3(rgb[0], rgb[1], rgb[2]);
//        }
//        return color;
//    }
//
//""".trimIndent()
//
//@Composable
//fun MetaContainer(
//    modifier: Modifier = Modifier,
//    cutoff: Float = .5f,
//    color: Color = Color.Black,
//    content: @Composable BoxScope.() -> Unit,
//) {
//    val metaShader = remember {
//        RuntimeShader(ShaderSource)
//    }
//    Box(
//        modifier
//            .graphicsLayer {
//                metaShader.setFloatUniform("cutoff", cutoff)
//                metaShader.setFloatUniform("rgb", color.red, color.green, color.blue)
//                renderEffect = RenderEffect
//                    .createRuntimeShaderEffect(
//                        metaShader, "composable"
//                    )
//                    .asComposeRenderEffect()
//            },
//        content = content,
//    )
//}