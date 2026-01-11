package com.onder.f1PaddockMini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.onder.f1PaddockMini.navigation.NavigationGraph
import com.onder.f1PaddockMini.pages.SplashScreen
import com.onder.f1PaddockMini.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                F1PaddockApp()
            }
        }
    }
}

@Composable
fun F1PaddockApp() {
    var showSplash by remember { mutableStateOf(true) }
    val navController = rememberNavController()

    if (showSplash) {
        SplashScreen(
            onSplashComplete = {
                showSplash = false
            }
        )
    } else {
        NavigationGraph(navController = navController)
    }
}