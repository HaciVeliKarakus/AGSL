package io.hvk.agsl.component

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import kotlinx.coroutines.delay

/**
 * Creates a Modifier that applies a wavy animation effect in response to press interactions.
 *
 * @param interactionSource The source of user interaction.
 * @param releaseDelay The delay (in milliseconds) before releasing the wavy animation after releasing the press.
 * @param spec The animation spec for the enter/exit animation.
 * @param confirmWaving A lambda function returning a Boolean value. If true, the wavy animation will be triggered.
 *
 * @return A Modifier with the wavy animation effect applied.
 */
fun Modifier.onPressWavy(
    interactionSource: InteractionSource,
    releaseDelay: Long = 500,
    spec: AnimationSpec<Float> = tween(durationMillis = 400),
    confirmWaving: () -> Boolean = { true }
) = composed {
    val shader = remember { RuntimeShader(WavyShader) }

    var time by remember { mutableStateOf(0f) }
    var playAnimation by remember { mutableStateOf(false) }

    val isPressed by interactionSource.collectIsPressedAsState()

    val animationProgress by animateFloatAsState(
        targetValue = if (playAnimation) 1f else 0f,
        animationSpec = spec,
        label = "Wavy Animation Progress"
    )

    LaunchedEffect(playAnimation) {
        while (playAnimation) {
            delay(16) // Delay to simulate frame rate, adjust as needed
            time += 0.016f // Increase time by 0.016 seconds (60 FPS simulation)
        }
    }

    LaunchedEffect(isPressed) {
        if (isPressed && confirmWaving()) {
            playAnimation = true
        } else if (!isPressed && playAnimation) {
            delay(releaseDelay)
            playAnimation = false
        }
    }

    this
        .onSizeChanged { size ->
            shader.setFloatUniform(
                "size",
                size.width.toFloat(),
                size.height.toFloat()
            )
        }
        .graphicsLayer {
            clip = true

            shader.setFloatUniform("time", time)
            shader.setFloatUniform("progress", animationProgress)

            renderEffect = RenderEffect
                .createRuntimeShaderEffect(shader, "composable")
                .asComposeRenderEffect()
        }
}

const val WavyShader = """
// Shader Input
uniform float progress; // Animation progress
uniform float time; // Time
uniform float2 size; // View size
uniform shader composable; // Input texture

// Constants
const float speed = 10;
const float frequency = 8;
const float sharpness = 0.99;
const float depth = 0.2;

// Target animation variables
const float targetWaveAmplitude = 6;
const float targetYStretching = 4.5;

// Animation variables
float waveAmplitude = 0;
float yStretching = 0;

// Distortion Constants
const float margin = 0.4;
const float waveFrequency = 0.02;

// Function to distort the coordinate based on wave deformations
float2 distortCoord(in float2 originalCoord) {
    // Normalize the coordinates to [-1;1], with 0 at the center
    float2 uv = originalCoord / size * 2 - 1;
    
    // Calculate smoothstep values for the x and y coordinates
    float edgeX = 1 - smoothstep(0.0, margin, abs(uv.x)) * smoothstep(1.0 - margin, 1.0, abs(uv.x));
    float edgeY = 1 - smoothstep(0.0, margin, abs(uv.y)) * smoothstep(1.0 - margin, 1.0, abs(uv.y));
    
    // Combine the smoothstep values to create a smooth margin
    float edge = min(edgeX, edgeY);
    
    // Calculate the wave distortion offset based on the length of the distorted coordinate
    float waveOffset = sin(length(originalCoord) * waveFrequency + time * speed);
    
    // Apply the wave distortion to the fragment coordinate
    return originalCoord + (waveOffset * waveAmplitude * edge);
}

float4 main(in float2 fragCoord) {
    // Update animation variables based on the progress
    waveAmplitude = targetWaveAmplitude * progress;
    yStretching = targetYStretching * progress;

    // Evaluate the Composable shader at the distorted coordinate
    float2 distortedCoord = distortCoord(fragCoord);
    float4 baseColor = composable.eval(distortedCoord);

    // Normalize the coords
    float2 uv = fragCoord / size;

    // Center and stretch the UV coordinates
    uv -= 0.5;
    uv *= float2(2, yStretching);

    // Calculate y-coordinate
    float y = sqrt(1 - uv.x * uv.x * uv.x * uv.x);

    // Add dynamic offset based on time
    float offset = sin(frequency * uv.x + time * speed) * depth;

    // Calculate upper and lower y-coordinates with offset
    float upperY = y + offset;
    float lowerY = -y + offset;

    // Calculate edge and mid values for smoothstep operation
    float edge = abs(upperY - lowerY);
    float mid = (upperY + lowerY) / 2;

    // Apply smoothstep to create the final color
    return baseColor * smoothstep(edge, edge * sharpness, abs(uv.y - mid));
}
"""