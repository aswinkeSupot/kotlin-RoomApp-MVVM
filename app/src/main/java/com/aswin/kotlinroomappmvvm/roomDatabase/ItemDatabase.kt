package com.example.roomdbapp.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @Database - is use to declare a class as a room database and configure its properties.
 */

@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {
    //Getting a DAO instance
    abstract fun getItemDao() :ItemDao

    /**
     * Singleton Design Pattern:
     * Only one instance of the database exists, avoiding unnecessary overhead
     * associated with repeated database creation
     *
     * companion object : define a static singleton instance of this DB Class
     *
     * @Volatile : prevents any possible race conditions in multithreading.
     * **/

    companion object{
        @Volatile
        private var INSTANCE: ItemDatabase? = null

        // if the Instance is not null, return it
        // Otherwise create a new database instance
        fun getDatabase(context: Context): ItemDatabase{
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ItemDatabase::class.java,
                    "item_database"
                ).build()
                    .also { INSTANCE = it }
            }
        }

    }
}