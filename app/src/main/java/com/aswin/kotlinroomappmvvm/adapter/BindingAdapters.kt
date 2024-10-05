package com.aswin.kotlinroomappmvvm.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.aswin.kotlinroomappmvvm.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("itemName")
    fun TextView.setItemName(value: String?) {
        // Format the double as a string (e.g., two decimal places)
        this.text = "Product :  " + value
    }

    @JvmStatic
    @BindingAdapter("doubleToString")
    fun TextView.setDoubleToString(value: Double?) {
        // Format the double as a string (e.g., two decimal places)
        this.text = "Rs." +value?.let { String.format("%.2f", it) } ?: "N/A"
    }

    @JvmStatic
    @BindingAdapter("intToString")
    fun TextView.setIntToString(value: Int?) {
        // Convert the int to a string
        this.text = "Quantity : "+value?.toString() ?: "N/A" // Default to "N/A" if value is null
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.placeholder)  // Your placeholder image
                    .error(R.drawable.placeholder)              // Your error image
            )
            .into(view)
    }
}