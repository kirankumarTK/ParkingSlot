package com.example.parkingslot.modules.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.parkingslot.LoginMainActivity
import com.example.parkingslot.R
import com.example.parkingslot.ui.component.appBackground
import com.example.parkingslot.ui.theme.ParkingSlotTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class Splash : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParkingSlotTheme {
                SplashScreen(
                    onFinished = {
                        startActivity(Intent(this, LoginMainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }


    @Composable
    fun SplashScreen(onFinished: () -> Unit) {

        val scale = remember { Animatable(0f) }

        LaunchedEffect(true) {
            scale.animateTo(
                1f, animationSpec = tween(500, easing = FastOutSlowInEasing)
            )
            delay(500) // Total 1.5s
            onFinished()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appBackground()),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.applogo),
                contentDescription = "App Logo",
                modifier = Modifier.scale(scale.value)
            )
        }
    }

    @Preview
    @Composable
    fun PreviewSplashScreen() {
        SplashScreen { }
    }
}