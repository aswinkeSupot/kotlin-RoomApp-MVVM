package com.aswin.kotlinroomappmvvm.activity.home.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.activity.addItem.model.AddItemModel
import com.aswin.kotlinroomappmvvm.activity.addItem.view.AddItemActivity
import com.aswin.kotlinroomappmvvm.activity.home.model.HomeModel
import com.aswin.kotlinroomappmvvm.activity.home.viewModel.HomeViewModel
import com.aswin.kotlinroomappmvvm.activity.home.viewModel.HomeViewModelFactory
import com.aswin.kotlinroomappmvvm.adapter.ItemRecyclerAdapter
import com.aswin.kotlinroomappmvvm.databinding.ActivityHomeBinding
import com.aswin.kotlinroomappmvvm.roomDatabase.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        // Set lifecycleOwner for data binding
        binding.lifecycleOwner = this
        var homeModle = HomeModel(binding,this)
        binding.homeModel = homeModle
    }
}