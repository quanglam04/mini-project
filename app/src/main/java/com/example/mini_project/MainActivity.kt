package com.example.mini_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mini_project.navigation.AppNavigation
import com.example.mini_project.ui.theme.Mini_projectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mini_projectTheme {
                AppNavigation()
            }
        }
    }
}

