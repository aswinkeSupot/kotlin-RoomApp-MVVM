package com.aswin.kotlinroomappmvvm.activity.addItem.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Aswin on 03-10-2024.
 */
class AddItemViewModelFactory (private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddItemViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}