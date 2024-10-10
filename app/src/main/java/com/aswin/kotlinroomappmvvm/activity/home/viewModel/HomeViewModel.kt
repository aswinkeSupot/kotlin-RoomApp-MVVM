package com.aswin.kotlinroomappmvvm.activity.home.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aswin.kotlinroomappmvvm.repository.ItemRepository
import com.aswin.kotlinroomappmvvm.roomDatabase.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    suspend fun getSearchedRecords(query: String) : Boolean{
        var isSuccess = false

        withContext(Dispatchers.IO) {
            allData = repository.getSearchDataFromDB(query)
            isSuccess = true
        }

        return isSuccess
    }

    fun deleteAllItem() : Boolean{
        var isSuccess = false
        GlobalScope.launch(Dispatchers.IO) {
            isSuccess = repository.deleteAllItem()
        }
        return isSuccess
    }


}