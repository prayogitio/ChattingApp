package com.example.chatclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatclient.ui.theme.ChatClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatClientTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    Surface(color = MaterialTheme.colorScheme.background) {
        var showLandingScreen by rememberSaveable {
            mutableStateOf(true)
        }
        if (showLandingScreen) {
            LandingScreen(onTimeout = {
                showLandingScreen = false;
            })
        } else {
            ChatRoomListScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatClientTheme {
        ChatRoomListScreen()
    }
}