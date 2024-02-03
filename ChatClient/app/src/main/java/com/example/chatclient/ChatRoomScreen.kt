package com.example.chatclient

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun ChatRoomScreen(chatMessages: List<ChatMessage>,
                   chatRoom: ChatRoom, onRoomExit: () -> Unit,
                   modifier: Modifier = Modifier,
                   chatViewModel: ChatViewModel = viewModel()
) {
    var typedMessage by rememberSaveable {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    Surface(modifier = modifier.fillMaxSize()) {
        Column {
            Box (modifier = modifier
                .height(50.dp), contentAlignment = Alignment.Center
            ) {
                Row (modifier, Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { onRoomExit() }) {
                        Text(text = "Exit")
                    }
                    Text(chatRoom.name)
                }
            }
            Spacer(modifier = modifier.padding(5.dp))
            Box (modifier = modifier
                .weight(1f, true)
            ) {
                ChatBubble(chatMessages)
            }
            Spacer(modifier = modifier.padding(5.dp))
            Box (modifier = modifier
                .height(50.dp), contentAlignment = Alignment.Center
            ) {
                Row (modifier, Arrangement.Absolute.SpaceEvenly)
                {
                    TextField(value = typedMessage, onValueChange = {
                        typedMessage = it
                    })
                    Button(onClick = {
                        val msg = typedMessage
                        scope.launch {
                            chatViewModel.sendMessage(chatRoom.topic, msg)
                        }
                        typedMessage = ""
                    }) {
                        Text(text = "Send")
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(chatMessages: List<ChatMessage>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(15.dp)) {
        items(chatMessages) {
            Box (modifier = modifier
                .padding(10.dp)
                .fillParentMaxWidth()
                .height(30.dp)
                .background(color = MaterialTheme.colorScheme.inversePrimary)
                .border(
                    BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.inversePrimary),
                    shape = CircleShape
                )) {
                    Text(text = it.message, modifier.padding(5.dp))
                }
        }
    }
}