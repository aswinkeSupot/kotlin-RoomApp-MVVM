package com.aswin.kotlinroomappmvvm.activity.itemDetail.model

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aswin.kotlinroomappmvvm.BR
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.activity.home.view.HomeActivity
import com.aswin.kotlinroomappmvvm.activity.itemDetail.viewModle.ItemDetailsViewModel
import com.aswin.kotlinroomappmvvm.activity.itemDetail.viewModle.ItemDetailsViewModelFactory
import com.aswin.kotlinroomappmvvm.databinding.ActivityItemDetailsBinding
import com.aswin.kotlinroomappmvvm.roomDatabase.Item

/***
 * NOTE-
 * For implementing @Bindable need to add "kotlin-kapt" plugin in app level build.gradle.kts
 * plugins {
 *     id("kotlin-kapt")
 * }
 **/

class ItemDetailsModel(val binding: ActivityItemDetailsBinding,val item: Item?, val activity: FragmentActivity) : BaseObservable() {
    lateinit var itemDetailsViewModel: ItemDetailsViewModel

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

    init {

        // Set up Toolbar as the ActionBar in the Fragment
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        // Set the ActionBar title to an empty string to remove the app name
        (activity as AppCompatActivity).supportActionBar?.title = "Detail Page"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Optional: Customize the icon (uses the default back icon if not set)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back) // Optional, set your custom icon

        val factory = ItemDetailsViewModelFactory(activity)
        itemDetailsViewModel = ViewModelProvider(activity,factory).get(ItemDetailsViewModel::class.java)

        itemName = item?.name ?: ""
        itemPrice = item?.price.toString()
        itemQuantity = item?.quantity.toString()

        // Observe LiveData for navigation inside init block
        itemDetailsViewModel.navigateToHome.observe(binding.lifecycleOwner!!, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                val intent = Intent(activity, HomeActivity::class.java)
                activity.startActivity(intent)
                (activity as Activity).finish()
                itemDetailsViewModel.doneNavigating()
            }
        })
    }

    // Handling toolbar menu item clicks
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle toolbar's back button
                activity.onBackPressed()
                return true
            }

        }
        return false
    }

    fun deleteItem(view: View){
//        var itemid = item!!.id
        // Inserting data into Database
//        var item: Item = Item(itemid,name, price, quantity)

        itemDetailsViewModel.deleteItem(item!!)
    }

    fun updateItem(view: View) {
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
            var itemid = item!!.id
            // Inserting data into Database
            var item: Item = Item(itemid,name, price, quantity)

            itemDetailsViewModel.updateItem(item)
        }
    }
}