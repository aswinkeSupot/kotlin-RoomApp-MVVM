package com.aswin.kotlinroomappmvvm.activity.home.model

import android.content.Intent
import android.view.View
import androidx.databinding.BaseObservable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aswin.kotlinroomappmvvm.activity.itemDetail.view.ItemDetailsActivity
import com.aswin.kotlinroomappmvvm.activity.addItem.view.AddItemActivity
import com.aswin.kotlinroomappmvvm.activity.home.viewModel.HomeViewModel
import com.aswin.kotlinroomappmvvm.activity.home.viewModel.HomeViewModelFactory
import com.aswin.kotlinroomappmvvm.adapter.ItemRecyclerAdapter
import com.aswin.kotlinroomappmvvm.databinding.ActivityHomeBinding
import com.aswin.kotlinroomappmvvm.roomDatabase.Item

class HomeModel(val binding: ActivityHomeBinding, val activity: FragmentActivity) : BaseObservable() {

    var emptyRecordText: String = "No Records Found"
    lateinit var homeViewModel: HomeViewModel

    var items: List<Item>? = null

    init {
        val factory = HomeViewModelFactory(activity)
        // Getting the response
        homeViewModel = ViewModelProvider(activity,factory).get(HomeViewModel::class.java)

        //RecyclerView
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        // Initialize the adapter with an empty list
        val adapter = ItemRecyclerAdapter(activity, listOf(), object : ItemRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(item: Item) {
                navigateToDetailPage(item)
            }
        })
        binding.recyclerView.adapter = adapter

        loadData(adapter)
    }
    // Navigate to DetailActivity, passing the clicked Item
    private fun navigateToDetailPage(item: Item) {
        val intent = Intent(activity, ItemDetailsActivity::class.java)
        intent.putExtra("item", item) // Pass the item as Serializable
        activity.startActivity(intent)
    }


    fun goToAddPage(view: View){
        val intent = Intent(activity, AddItemActivity::class.java)
        activity.startActivity(intent)
        //(activity as Activity).finish()
    }

    private fun loadData (adapter: ItemRecyclerAdapter) {
            homeViewModel.getAllRecords().observe(activity, Observer { itemList ->
                if(itemList.isNotEmpty()){
                    var result = ""
                    items = itemList
                    adapter.updateItems(itemList)
                    binding.tvNoPost.visibility = View.GONE

                    for ((index, item) in itemList.withIndex()){
                        result += "${index + 1}.  item = ${item.name} \n     price = ${item.price} \n     quantity = ${item.quantity} \n__________________\n"

                        var itemval = Item(
                            item.id,
                            item.name,
                            item.price,
                            item.quantity
                        )

                    }

                }else{
                    binding.tvNoPost.visibility = View.VISIBLE
                }
            })
    }
}