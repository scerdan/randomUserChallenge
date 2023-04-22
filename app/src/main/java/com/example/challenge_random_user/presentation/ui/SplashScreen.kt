package com.example.challenge_random_user.presentation.ui

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.challenge_random_user.ui.theme.*
import com.example.challenge_random_user.utils.Constants
import com.example.challenge_random_user.utils.Screen
import kotlinx.coroutines.delay

/** Sweep angle determines where animation finishes */
const val SWEEP_ANGLE = 360f
const val ANIM_DURATION = 2000
const val START_ANGLE = -180f

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SplashScreen(navController: NavHostController) {
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
        delay(2000L)
        Constants.goTo(Screen.HOME_SCREEN.route,navController, true)
    }


    Column(
        Modifier
            .fillMaxSize(1f)
            .background(GradientColor1),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1 / 2f),
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
                }) {
            }
            Row {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(1 / 2f)
                        .aspectRatio(1 / 2f),
                    painter = rememberImagePainter(data = "https://logos-world.net/wp-content/uploads/2021/02/BBVA-Logo.png"),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
            }
        }
        Row(
            Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                "CHALLENGE", style = TextStyle(
                    color = GradientColor5,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            )
        }
    }
}