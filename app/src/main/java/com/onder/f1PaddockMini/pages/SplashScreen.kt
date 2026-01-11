package com.onder.f1PaddockMini.pages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.onder.f1PaddockMini.R

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    var animationStarted by remember { mutableStateOf(false) }
    var logoVisible by remember { mutableStateOf(false) }

    // Logo'nun y pozisyonu için animasyon
    val logoOffsetY by animateFloatAsState(
        targetValue = if (animationStarted) 0f else 500f,
        animationSpec = tween(durationMillis = 500),
        label = "logo_offset"
    )

    // Logo'nun görünürlüğü için animasyon
    val logoAlpha by animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "logo_alpha"
    )

    LaunchedEffect(Unit) {
        // Animasyonu başlat
        animationStarted = true
        logoVisible = true
        
        // Toplam 1 saniye bekle
        kotlinx.coroutines.delay(1000)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.99f),
                        Color.Black.copy(alpha = 0.88f),
                        Color.Black.copy(alpha = 0.66f),
                        Color.Black.copy(alpha = 0.33f),
                        Color.Black
                    ),
                    radius = 2000f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.f1_logo),
            contentDescription = "F1 Logo",
            modifier = Modifier
                .size(200.dp)
                .offset(y = logoOffsetY.dp)
                .alpha(logoAlpha)
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onSplashComplete = {})
}

