package com.aswin.kotlinroomappmvvm.activity.addItem.model

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aswin.kotlinroomappmvvm.BR
import com.aswin.kotlinroomappmvvm.activity.addItem.viewModel.AddItemViewModel
import com.aswin.kotlinroomappmvvm.activity.addItem.viewModel.AddItemViewModelFactory
import com.aswin.kotlinroomappmvvm.activity.home.view.HomeActivity
import com.aswin.kotlinroomappmvvm.databinding.ActivityAddItemBinding
import com.aswin.kotlinroomappmvvm.roomDatabase.Item

/***
 * NOTE-
 * For implementing @Bindable need to add "kotlin-kapt" plugin in app level build.gradle.kts
 * plugins {
 *     id("kotlin-kapt")
 * }
 **/

class AddItemModel(val binding: ActivityAddItemBinding,val activity: FragmentActivity) : BaseObservable() {
    lateinit var addItemViewModel: AddItemViewModel

    init {
        val factory = AddItemViewModelFactory(activity)
        addItemViewModel = ViewModelProvider(activity,factory).get(AddItemViewModel::class.java)

        // Observe LiveData for navigation inside init block
        addItemViewModel.navigateToHome.observe(binding.lifecycleOwner!!, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                val intent = Intent(activity, HomeActivity::class.java)
                activity.startActivity(intent)
                (activity as Activity).finish()
                addItemViewModel.doneNavigating()
            }
        })
    }

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


    fun addItem(view: View) {
        var name = ""
        var price = 0.0
        var quantity = 0

        var hasEmptyEditField = true;

        if(binding.edItemName.text.toString().isNotEmpty()) {
            name = itemName.toString()
            hasEmptyEditField = false;
        } else{
            hasEmptyEditField = true;
        }

        if(binding.edItemPrice.text.toString().isNotEmpty()){
            price = itemPrice.toDouble()
            hasEmptyEditField = false;
        } else{
            hasEmptyEditField = true;
        }

        if(binding.edItemQuantity.text.toString().isNotEmpty()){
            quantity = itemQuantity.toInt()
            hasEmptyEditField = false;
        } else{
            hasEmptyEditField = true;
        }

        if(hasEmptyEditField){
            Toast.makeText(activity,"Please Fill All Fields", Toast.LENGTH_LONG).show()
        }else{
            // Inserting data into Database
            var item: Item = Item(0,name, price, quantity)

            addItemViewModel.addItem(item)
        }
    }
}