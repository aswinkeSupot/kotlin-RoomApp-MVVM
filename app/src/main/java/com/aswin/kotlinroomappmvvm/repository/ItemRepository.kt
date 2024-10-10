package com.aswin.kotlinroomappmvvm.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.aswin.kotlinroomappmvvm.roomDatabase.Item
import com.aswin.kotlinroomappmvvm.roomDatabase.ItemDatabase

/**
 * Created by Aswin on 03-10-2024.
 */
class ItemRepository(context: Context) {
    private val applicationContext = context.applicationContext

    // Instance of the Database
    private val itemDB = ItemDatabase.getDatabase(applicationContext)
    // Instance of DAO
    private val itemDao = itemDB.getItemDao()


    fun getAllDataFromDB(): LiveData<List<Item>> {
        return itemDao.getAllItemsInDB()
    }

    suspend fun getSearchDataFromDB(query: String): LiveData<List<Item>>{
        return itemDao.searchItem(query)
    }

    suspend fun insertItem(item: Item): Boolean {
        return try {
            itemDao.insertItem(item)
            true // Return true if the insert was successful
        } catch (e: Exception) {
            e.printStackTrace()
            false // Return false if there was an error
        }
    }

    suspend fun updateItem(item: Item): Boolean {
        return try {
            itemDao.updateItem(item)
            true
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteItem(item: Item): Boolean {
        return try {
            itemDao.deleteItem(item)
            true
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteAllItem(): Boolean {
        return try {
            itemDao.deleteAllItem()
            true
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}