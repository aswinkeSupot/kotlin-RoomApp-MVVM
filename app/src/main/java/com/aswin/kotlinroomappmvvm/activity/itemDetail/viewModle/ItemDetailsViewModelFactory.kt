package com.aswin.kotlinroomappmvvm.activity.itemDetail.viewModle

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aswin.kotlinroomappmvvm.databinding.ActivityItemDetailsBinding
import com.aswin.kotlinroomappmvvm.roomDatabase.Item

class ItemDetailsViewModelFactory (private val context: Context, private val binding: ActivityItemDetailsBinding, val item: Item?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemDetailsViewModel(context, binding,item) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}