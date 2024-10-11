package com.aswin.kotlinroomappmvvm.activity.addItem.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.activity.addItem.viewModel.AddItemViewModel
import com.aswin.kotlinroomappmvvm.activity.addItem.viewModel.AddItemViewModelFactory
import com.aswin.kotlinroomappmvvm.activity.home.view.HomeActivity
import com.aswin.kotlinroomappmvvm.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddItemBinding
    //    lateinit var addItemModel: AddItemModel
    lateinit var viewModel: AddItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_item)

        // Set lifecycleOwner for data binding
        binding.lifecycleOwner = this
        val factory = AddItemViewModelFactory(this, binding)
        viewModel = ViewModelProvider(this, factory).get(AddItemViewModel::class.java)
        binding.xmlViewModel = viewModel



        // Set up Toolbar as the ActionBar in the Fragment
        setSupportActionBar(binding.toolbar)
        // Set the ActionBar title to an empty string to remove the app name
        supportActionBar?.title = "Add Items"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Optional: Customize the icon (uses the default back icon if not set)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back) // Optional, set your custom icon

        // Observe navigation event
        viewModel.navigateToHome.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
                viewModel.doneNavigating()
            }
        })

        viewModel.backPressed.observe(this, Observer { backPress ->
            if (backPress == true) {
                onBackPressed()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return viewModel.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }
}