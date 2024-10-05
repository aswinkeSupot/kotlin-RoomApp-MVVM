package com.aswin.kotlinroomappmvvm.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.databinding.ActivityHomeBinding
import com.aswin.kotlinroomappmvvm.databinding.ActivityItemDetailsBinding
import com.aswin.kotlinroomappmvvm.roomDatabase.Item

class ItemDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_details)

        // Retrieve the passed item
        val item: Item? = intent.getSerializableExtra("item") as? Item

        if (item != null) {
            binding.textView2.text = item.name
        }

    }
}