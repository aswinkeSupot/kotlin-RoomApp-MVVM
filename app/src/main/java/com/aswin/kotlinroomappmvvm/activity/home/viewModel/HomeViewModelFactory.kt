package com.aswin.kotlinroomappmvvm.activity.home.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aswin.kotlinroomappmvvm.databinding.ActivityHomeBinding

/**
 * Created by Aswin on 03-10-2024.
 */
class HomeViewModelFactory (private val context: Context, private val binding: ActivityHomeBinding) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(context,binding) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}