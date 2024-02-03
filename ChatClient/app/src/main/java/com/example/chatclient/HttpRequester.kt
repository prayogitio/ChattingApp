package com.example.chatclient

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

class HttpRequester {
    private val BackendRESTAPI = "http://192.168.0.107:8080"

    fun getChatRooms(): List<ChatRoom> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BackendRESTAPI + "/chatrooms")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            for ((name, value) in response.headers) {
                println("$name: $value")
            }

            val responseBodyString = response.body!!.string()
            println(responseBodyString)
            val gson = Gson()
            val responseBody: ChatRoomResponse = gson.fromJson(responseBodyString, ChatRoomResponse::class.java)
            return responseBody.chatRoomList
        }
    }
}