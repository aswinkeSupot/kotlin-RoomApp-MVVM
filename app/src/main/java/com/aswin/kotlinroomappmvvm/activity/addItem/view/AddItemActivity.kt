package com.aswin.kotlinroomappmvvm.activity.addItem.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.activity.addItem.model.AddItemModel
import com.aswin.kotlinroomappmvvm.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_item)

        // Set lifecycleOwner for data binding
        binding.lifecycleOwner = this
        var addItemModle = AddItemModel(binding,this)
        binding.addItemModel = addItemModle
    }
}