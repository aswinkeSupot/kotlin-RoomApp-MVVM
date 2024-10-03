package com.aswin.kotlinroomappmvvm.activity.addItem.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.activity.addItem.viewModel.AddItemViewModel
import com.aswin.kotlinroomappmvvm.activity.addItem.viewModel.AddItemViewModelFactory
import com.aswin.kotlinroomappmvvm.activity.home.view.HomeActivity
import com.aswin.kotlinroomappmvvm.activity.home.viewModel.HomeViewModelFactory
import com.aswin.kotlinroomappmvvm.databinding.ActivityAddItemBinding
import com.aswin.kotlinroomappmvvm.databinding.ActivityHomeBinding
import com.example.roomdbapp.roomDatabase.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddItemActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddItemBinding
    lateinit var addItemViewModel: AddItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_item)

        val factory = AddItemViewModelFactory(applicationContext)
        addItemViewModel = ViewModelProvider(this,factory).get(AddItemViewModel::class.java)

        binding.btnSave.setOnClickListener {
            var itemName = ""
            var itemPrice = 0.0
            var itemQuantity = 0
            var hasEmptyEditField = true;
            if(binding.edItemName.text.toString().isNotEmpty()) {
                itemName = binding.edItemName.text.toString();
                hasEmptyEditField = false;
            } else{
                hasEmptyEditField = true;
            }

            if(binding.edItemPrice.text.toString().isNotEmpty()){
                itemPrice = binding.edItemPrice.text.toString().toDouble();
                hasEmptyEditField = false;
            } else{
                hasEmptyEditField = true;
            }

            if(binding.edItemQuantity.text.toString().isNotEmpty()){
                itemQuantity = binding.edItemQuantity.text.toString().toInt();
                hasEmptyEditField = false;
            } else{
                hasEmptyEditField = true;
            }

            if(hasEmptyEditField){
                Toast.makeText(applicationContext,"Please Fill All Fields",Toast.LENGTH_LONG).show()
            }else{
                // Inserting data into Database
                var item: Item = Item(0,itemName, itemPrice, itemQuantity)

                addItemViewModel.addItem(item)

            }

        }

        // Observe LiveData for navigation
        addItemViewModel.navigateToHome.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
                addItemViewModel.doneNavigating()
            }
        })
    }
}