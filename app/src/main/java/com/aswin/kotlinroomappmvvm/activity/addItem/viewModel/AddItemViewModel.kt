package com.aswin.kotlinroomappmvvm.activity.addItem.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aswin.kotlinroomappmvvm.repository.ItemRepository
import com.aswin.kotlinroomappmvvm.roomDatabase.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Aswin on 03-10-2024.
 */
class AddItemViewModel(context: Context): ViewModel()  {
    private val applicationContext = context.applicationContext
    var repository: ItemRepository = ItemRepository(context)

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    fun addItem(item: Item){
        GlobalScope.launch(Dispatchers.IO) {
            val isSuccess = repository.insertItem(item)
            GlobalScope.launch(Dispatchers.Main) {
                _navigateToHome.value = isSuccess
            }
        }
    }

    fun doneNavigating() {
        _navigateToHome.value = false
    }
}