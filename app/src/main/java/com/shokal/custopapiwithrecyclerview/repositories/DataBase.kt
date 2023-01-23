package com.shokal.custopapiwithrecyclerview.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shokal.custopapiwithrecyclerview.converters.ImageConverter
import com.shokal.custopapiwithrecyclerview.models.BookMarkNews
import com.shokal.custopapiwithrecyclerview.models.LocalArticle

@Database(entities = [BookMarkNews::class, LocalArticle::class], version = 7)
@TypeConverters(ImageConverter::class)
abstract class DataBase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null
        fun getDatabase(context: Context): DataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "news_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}