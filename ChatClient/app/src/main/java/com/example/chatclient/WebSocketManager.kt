package com.example.chatclient

import android.util.Log
import androidx.compose.runtime.MutableState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.sendText
import org.hildan.krossbow.stomp.subscribeText
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient

class WebSocketManager() {
    private val BackendWSAPI = "ws://192.168.0.107:8080/wsHandler"
    private var client: StompClient? = null
    var session: StompSession? = null

    init {
        client = StompClient(OkHttpWebSocketClient())
    }

    suspend fun openConnection() {
        session = client?.connect(BackendWSAPI)
    }

    suspend fun subscribeTopic(topic: String, chatMessages: MutableState<List<ChatMessage>>) {
        val subscription: Flow<String>? = session?.subscribeText("/topic/" + topic)
        val gson = Gson()
        subscription?.collect {msg ->
            Log.d("Debug", "Received $msg")
            chatMessages.value = chatMessages.value + gson.fromJson(msg, ChatMessage::class.java)
        }
    }

    suspend fun sendMessage(topic: String, message: String) {
        val gson = Gson()
        val msg = gson.toJson(ChatMessage(message, topic))
        session?.sendText("/app/chat", msg)
    }
}