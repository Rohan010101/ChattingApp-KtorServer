package com.example.di

import com.example.data.MessageDataSource
import com.example.data.MessageDataSourceImpl
import com.example.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {

    // MongoDB Database Instance
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase("message_db_yt")
    }

    // Message Data Source
    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }

    // RoomController
    single {
        RoomController(get())
    }
}