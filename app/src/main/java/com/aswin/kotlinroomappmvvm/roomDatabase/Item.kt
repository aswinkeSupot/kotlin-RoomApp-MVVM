package com.example.roomdbapp.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * ROOM DB Details
 * Entity (TableName) : item_table
 * 1. item_id
 * 2. item_name
 * 3. item_price
 * 4. item_quantity
 * **/
@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    val id : Int = 0,
    @ColumnInfo(name = "item_name")
    val name : String,
    @ColumnInfo(name = "item_price")
    val price : Double,
    @ColumnInfo(name = "item_quantity")
    val quantity : Int
)
