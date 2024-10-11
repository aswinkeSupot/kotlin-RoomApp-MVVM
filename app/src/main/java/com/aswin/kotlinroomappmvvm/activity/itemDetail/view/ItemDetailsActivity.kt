package com.aswin.kotlinroomappmvvm.activity.itemDetail.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.activity.itemDetail.viewModle.ItemDetailsViewModel
import com.aswin.kotlinroomappmvvm.activity.itemDetail.viewModle.ItemDetailsViewModelFactory
import com.aswin.kotlinroomappmvvm.databinding.ActivityItemDetailsBinding
import com.aswin.kotlinroomappmvvm.roomDatabase.Item

class ItemDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityItemDetailsBinding
    lateinit var viewModel: ItemDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_details)

        // Retrieve the passed item
        val item: Item? = intent.getSerializableExtra("item") as? Item

        // Set lifecycleOwner for data binding
        binding.lifecycleOwner = this
        val factory = ItemDetailsViewModelFactory(this, binding,item)
        viewModel = ViewModelProvider(this,factory).get(ItemDetailsViewModel::class.java)
        binding.xmlViewModel = viewModel

        // Set up Toolbar as the ActionBar in the Fragment
        setSupportActionBar(binding.toolbar)
        // Set the ActionBar title to an empty string to remove the app name
        supportActionBar?.title = "Detail Page"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Optional: Customize the icon (uses the default back icon if not set)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back) // Optional, set your custom icon


        viewModel.backPressed.observe(this, Observer { backPress ->
            if (backPress) {
                onBackPressed()
            }
        })

        viewModel.navigateToHome.observe(this, Observer{ backPress ->
            if (backPress){
                viewModel.goToHomePage()
                viewModel.doneNavigating()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Delegate the menu handling to HomeModel
        return viewModel.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }
}