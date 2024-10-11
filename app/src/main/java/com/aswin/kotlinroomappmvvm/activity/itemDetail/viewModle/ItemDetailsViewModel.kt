package com.aswin.kotlinroomappmvvm.activity.itemDetail.viewModle

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.aswin.kotlinroomappmvvm.activity.home.view.HomeActivity
import com.aswin.kotlinroomappmvvm.databinding.ActivityItemDetailsBinding
import com.aswin.kotlinroomappmvvm.repository.ItemRepository
import com.aswin.kotlinroomappmvvm.roomDatabase.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemDetailsViewModel(context: Context, binding: ActivityItemDetailsBinding, item: Item?): ViewModel(), Observable  {

    private val propertyChangeRegistry = PropertyChangeRegistry()
    private val binding = binding;
    private val context = context;
    private val item = item;

    var id : Int = 0
    @Bindable
    var itemName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.itemName)
        }

    @Bindable
    var itemPrice: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.itemPrice)
        }

    @Bindable
    var itemQuantity: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.itemQuantity)
        }

    var repository: ItemRepository = ItemRepository(context)

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    private val _backPressed = MutableLiveData<Boolean>()
    val backPressed: LiveData<Boolean>
        get() = _backPressed

    init {
        id = item?.id!!
        itemName = item?.name ?: ""
        itemPrice = item?.price.toString()
        itemQuantity = item?.quantity.toString()
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

    fun updateItem(view: View) {
        if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()) {
            val item = Item(id, itemName, itemPrice.toDouble(), itemQuantity.toInt())
            viewModelScope.launch(Dispatchers.IO) {
                val isSuccess = repository.updateItem(item)
                withContext(Dispatchers.Main) {
                    _navigateToHome.value = isSuccess
                }
            }
        }
    }

    fun goToHomePage(){
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
        // Check if the context is an instance of Activity before calling finish
        if (context is Activity) {
            (context as Activity).finish()
        }
    }

    fun deleteItem(view: View){
        GlobalScope.launch(Dispatchers.Main) {
            var isSuccess = false
            withContext(Dispatchers.Main) {
                isSuccess = repository.deleteItem(item!!)
            }
            if(isSuccess){
                _navigateToHome.value = isSuccess
            }
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

    // Helper method to notify changes
    private fun notifyPropertyChanged(fieldId: Int) {
        propertyChangeRegistry.notifyChange(this, fieldId)
    }

}