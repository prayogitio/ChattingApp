package com.example.chatclient

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Immutable
data class ChatRoom(
    val name: String,
    val topic: String
)

@Immutable
data class ChatRoomResponse(
    val chatRoomList: List<ChatRoom>
)

@Immutable
data class ChatMessage(
    val message: String,
    val topic: String
)

class ChatViewModel : ViewModel() {
    private var _chatRooms: List<ChatRoom> = emptyList<ChatRoom>().toMutableStateList()
    private var topicListener: Job? = null
    private var _chatMessages: MutableState<List<ChatMessage>> = mutableStateOf(emptyList())
    private val wsManager: WebSocketManager = WebSocketManager()

    init {
        viewModelScope.launch {
            wsManager.openConnection()
        }
    }

    val chatRooms: List<ChatRoom>
        get() = _chatRooms

    val chatMessages: MutableState<List<ChatMessage>>
        get() = _chatMessages

    fun getChatRooms() {
        val httpRequester = HttpRequester()
        _chatRooms = httpRequester.getChatRooms()
    }

    fun enterRoom(topic: String) {
        _chatMessages.value = _chatMessages.value.filter { it.topic == topic }
        topicListener = viewModelScope.launch {
            wsManager.subscribeTopic(topic, _chatMessages)
        }
    }

    fun exitRoom() {
        topicListener?.cancel()
    }

    suspend fun sendMessage(topic: String, message: String) {
        wsManager.sendMessage(topic, message);
    }
}
