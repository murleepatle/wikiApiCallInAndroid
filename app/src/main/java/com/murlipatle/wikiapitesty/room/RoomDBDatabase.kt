package com.murlipatle.wikiapitesty.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.murlipatle.wikiapitesty.retrofit.responce.Allcategory

@Database(entities = [Allcategory::class, CategoryRemoteKey::class,ArticleDetail::class,ArticleRemoteKey::class,ImageDetail::class,ImagesRemoteKey::class], version = 1,exportSchema=false)
abstract class RoomDBDatabase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context): RoomDBDatabase {
            return Room.databaseBuilder(context, RoomDBDatabase::class.java, "room_db").build()
        }
    }

    abstract fun getRoomDBDao(): RoomDBDao

}