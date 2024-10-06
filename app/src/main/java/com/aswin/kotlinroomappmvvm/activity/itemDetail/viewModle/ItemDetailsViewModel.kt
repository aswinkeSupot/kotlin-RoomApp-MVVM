package com.aswin.kotlinroomappmvvm.activity.itemDetail.viewModle

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aswin.kotlinroomappmvvm.repository.ItemRepository
import com.aswin.kotlinroomappmvvm.roomDatabase.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemDetailsViewModel(context: Context): ViewModel()  {
    var repository: ItemRepository = ItemRepository(context)

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    fun updateItem(item: Item){
        GlobalScope.launch(Dispatchers.IO) {
            val isSuccess = repository.updateItem(item)
            GlobalScope.launch(Dispatchers.Main) {
                _navigateToHome.value = isSuccess
            }
        }
    }

    fun deleteItem(item: Item){
        GlobalScope.launch(Dispatchers.IO) {
            val isSuccess = repository.deleteItem(item)
            GlobalScope.launch(Dispatchers.Main) {
                _navigateToHome.value = isSuccess
            }
        }
    }

    fun doneNavigating() {
        _navigateToHome.value = false
    }

}