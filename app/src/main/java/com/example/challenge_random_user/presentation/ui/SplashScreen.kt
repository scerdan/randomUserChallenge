package com.example.challenge_random_user.presentation.ui

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.challenge_random_user.ui.theme.*

/** Sweep angle determines where animation finishes */
const val SWEEP_ANGLE = 360f
const val ANIM_DURATION = 2000
const val START_ANGLE = -180f

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SplashScreen() {
    val sweepAngle = SWEEP_ANGLE
    val animationDuration = ANIM_DURATION
    var animationPlayed by remember { mutableStateOf(false) }

    val currentPercent = animateFloatAsState(
        targetValue = if (animationPlayed) sweepAngle else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = FastOutLinearInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .size(250.dp)
            .drawBehind {
                drawArc(
                    brush = Brush.linearGradient(
                        0f to GradientColor1,
                        0.2f to GradientColor2,
                        0.35f to GradientColor3,
                        0.45f to GradientColor4,
                        0.75f to GradientColor5,
                    ),
                    startAngle = START_ANGLE,
                    sweepAngle = currentPercent.value,
                    useCenter = false,
                    style = Stroke(width = 30f, cap = StrokeCap.Round)
                )
            }) { }
        Row {
            Image(
                modifier = Modifier
                    .fillMaxWidth(1/2f)
                    .aspectRatio(1/2f),
                painter = rememberImagePainter(data = "https://logos-world.net/wp-content/uploads/2021/02/BBVA-Logo.png"),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
        }
    }

}