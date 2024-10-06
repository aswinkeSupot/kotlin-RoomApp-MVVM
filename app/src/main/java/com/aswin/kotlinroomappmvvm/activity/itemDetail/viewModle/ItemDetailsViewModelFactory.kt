package com.aswin.kotlinroomappmvvm.activity.itemDetail.viewModle

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aswin.kotlinroomappmvvm.roomDatabase.Item

class ItemDetailsViewModelFactory (private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemDetailsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}