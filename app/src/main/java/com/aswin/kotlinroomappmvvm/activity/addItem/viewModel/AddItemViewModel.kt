package com.aswin.kotlinroomappmvvm.activity.addItem.viewModel

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aswin.kotlinroomappmvvm.BR
import com.aswin.kotlinroomappmvvm.databinding.ActivityAddItemBinding
import com.aswin.kotlinroomappmvvm.repository.ItemRepository
import com.aswin.kotlinroomappmvvm.roomDatabase.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Aswin on 03-10-2024.
 */
class AddItemViewModel(context: Context, binding: ActivityAddItemBinding) : ViewModel(),
    Observable {
    private val propertyChangeRegistry = PropertyChangeRegistry()

    private val repository: ItemRepository = ItemRepository(context.applicationContext)

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    private val _backPressed = MutableLiveData<Boolean>()
    val backPressed: LiveData<Boolean>
        get() = _backPressed

    @Bindable
    var itemName: String = ""
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, BR.itemName)
        }
    @Bindable
    var itemPrice: String = ""
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, BR.itemPrice)
        }

    @Bindable
    var itemQuantity: String = ""
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, BR.itemQuantity)
        }

    // Handling toolbar menu item clicks
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle toolbar's back button
                _backPressed.value = true
                return true
            }

        }
        return false
    }

    fun addItem(view: View) {

        if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()) {
            val item = Item(0, itemName, itemPrice.toDouble(), itemQuantity.toInt())
            viewModelScope.launch(Dispatchers.IO) {
                val isSuccess = repository.insertItem(item)
                withContext(Dispatchers.Main) {
                    _navigateToHome.value = isSuccess
                }
            }
        } else {
            // Handle error (e.g., show a message via LiveData)
        }
    }

    fun doneNavigating() {
        _navigateToHome.value = false
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback)
    }

}