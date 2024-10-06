package com.aswin.kotlinroomappmvvm.activity.itemDetail.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.activity.itemDetail.model.ItemDetailsModel
import com.aswin.kotlinroomappmvvm.databinding.ActivityItemDetailsBinding
import com.aswin.kotlinroomappmvvm.roomDatabase.Item

class ItemDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_details)

        // Retrieve the passed item
        val item: Item? = intent.getSerializableExtra("item") as? Item

        binding.lifecycleOwner = this
        var itemDetailsModel = ItemDetailsModel(binding, item,this)
        binding.itemDetailsModel = itemDetailsModel

    }
}