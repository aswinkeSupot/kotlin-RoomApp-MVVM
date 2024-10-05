package com.aswin.kotlinroomappmvvm.activity.home.model

import androidx.databinding.BaseObservable
import androidx.fragment.app.FragmentActivity
import com.aswin.kotlinroomappmvvm.databinding.ActivityHomeBinding

class HomeModel(val binding: ActivityHomeBinding, val activity: FragmentActivity) : BaseObservable() {

    var emptyRecordText: String = ""

    init {
        emptyRecordText = "No Records Found"
    }
}