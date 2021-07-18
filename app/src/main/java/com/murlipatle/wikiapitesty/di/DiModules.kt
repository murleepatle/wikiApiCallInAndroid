package com.murlipatle.wikiapitesty.di

import android.content.Context
import com.murlipatle.wikiapitesty.retrofit.RestApiInterface
import com.murlipatle.wikiapitesty.room.RoomDBDao
import com.murlipatle.wikiapitesty.room.RoomDBDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DiModules {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level=HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder().baseUrl("https://en.wikipedia.org/w/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideRestApiInterface(retrofit: Retrofit): RestApiInterface {
        return retrofit.create(RestApiInterface::class.java)
    }


    @Singleton
    @Provides
    fun provideRoomDBDatabase(@ApplicationContext context: Context): RoomDBDatabase {
        return RoomDBDatabase.getInstance(context)
    }


    @Singleton
    @Provides
    fun provideRoomDBDao(roomDBDatabase: RoomDBDatabase): RoomDBDao {
        return roomDBDatabase.getRoomDBDao()
    }

}