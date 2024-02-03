package com.example.chatclient

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatRoomListScreen(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = viewModel()) {
    var showChatRoom by rememberSaveable {
        mutableStateOf(false)
    }
    var currentChatRoomName by rememberSaveable {
        mutableStateOf("")
    }
    var currentChatRoomTopic by rememberSaveable {
        mutableStateOf("")
    }
    if (showChatRoom) {
        ChatRoomScreen(chatViewModel.chatMessages.value
            , ChatRoom(currentChatRoomName, currentChatRoomTopic)
            , onRoomExit = {
                currentChatRoomName = ""
                currentChatRoomTopic = ""
                showChatRoom = false
                chatViewModel.exitRoom()
            }
            , modifier = modifier.fillMaxWidth())
    } else {
        Surface(modifier = modifier.fillMaxSize()) {
            val chatRooms: List<ChatRoom> = chatViewModel.chatRooms
            if (chatRooms.isEmpty()) {
                Text(text = "No rooms available")
            } else {
                ChatRoomList(chatRooms, onRoomEnter = { chatRoom ->
                    currentChatRoomName = chatRoom.name
                    currentChatRoomTopic = chatRoom.topic
                    showChatRoom = true
                    chatViewModel.enterRoom(chatRoom.topic)
                })
            }
        }
    }
}

@Composable
private fun ChatRoomList(chatRooms: List<ChatRoom>,
                         onRoomEnter: (chatRoom: ChatRoom) -> Unit,
                         modifier: Modifier = Modifier) {
    LazyColumn {
        items(chatRooms) {
            ChatRoomCard(chatRoom = it, onRoomEnter = onRoomEnter)
        }
    }
}

@Composable
private fun ChatRoomCard(chatRoom: ChatRoom,
                     modifier: Modifier = Modifier,
                     onRoomEnter: (chatRoom: ChatRoom) -> Unit,
                     chatViewModel: ChatViewModel = viewModel()) {
    Box(modifier = modifier
        .padding(10.dp)
        .fillMaxWidth()
        .border(
            BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.inversePrimary),
            shape = CircleShape
        )
        .clickable { onRoomEnter(chatRoom) }, contentAlignment = Alignment.Center)
    {
        Box {
            Text(text = chatRoom.name, modifier = modifier.padding(10.dp))
        }
    }
}