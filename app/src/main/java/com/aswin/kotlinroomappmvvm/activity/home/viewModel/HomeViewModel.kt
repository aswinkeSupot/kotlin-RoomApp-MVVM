package com.aswin.kotlinroomappmvvm.activity.home.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aswin.kotlinroomappmvvm.repository.ItemRepository
import com.aswin.kotlinroomappmvvm.roomDatabase.Item

/**
 * Created by Aswin on 03-10-2024.
 */
class HomeViewModel(context: Context): ViewModel() {

    var repository: ItemRepository = ItemRepository(context)

    lateinit var allData: LiveData<List<Item>>

    init {
        allData = repository.getAllDataFromDB()
    }

    fun getAllRecords():  LiveData<List<Item>> {
        return allData
    }
}