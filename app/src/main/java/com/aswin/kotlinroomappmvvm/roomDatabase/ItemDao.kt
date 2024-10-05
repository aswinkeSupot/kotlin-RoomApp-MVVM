package com.aswin.kotlinroomappmvvm.roomDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * DAO : Data Access Object, defines the methods to interact with DB
 *
 * suspend : is use for creating coroutines.
 *
 * @Insert : annotation is used by Room DB Library to identify that the associated
 *  method is used for inserting data into the database.
 *
 * @Update : This annotation is use to identify that the associated method is
 *  used for updating data
 *
 * @Delete : This annotation is use to identify that the associated method is
 *  used for delete item
 *
 * @Query : Is use for custom Query, we can use placeholders like id to pass
 *  parameters to our query
 */

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    // Delete all data from the table "item_table" with a custom Query
    @Query("DELETE FROM item_table")
    suspend fun deleteAllItem()

    // Get all data from the item_table
    @Query("SELECT * FROM item_table")
    fun getAllItemsInDB(): LiveData<List<Item>>

    // Get all data from the item_table order by item_id in descending order
    @Query("SELECT * FROM item_table ORDER BY item_id DESC")
    fun getAllItemsDescInDB(): LiveData<List<Item>>

    // Search with title and body
    @Query("SELECT * FROM item_table WHERE item_name LIKE :query OR  item_price LIKE :query")
    fun searchNote(query: String?) : LiveData<List<Item>>
}