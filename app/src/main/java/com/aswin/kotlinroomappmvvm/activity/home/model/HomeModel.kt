package com.aswin.kotlinroomappmvvm.activity.home.model

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BaseObservable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.activity.itemDetail.view.ItemDetailsActivity
import com.aswin.kotlinroomappmvvm.activity.addItem.view.AddItemActivity
import com.aswin.kotlinroomappmvvm.activity.home.viewModel.HomeViewModel
import com.aswin.kotlinroomappmvvm.activity.home.viewModel.HomeViewModelFactory
import com.aswin.kotlinroomappmvvm.adapter.ItemRecyclerAdapter
import com.aswin.kotlinroomappmvvm.databinding.ActivityHomeBinding
import com.aswin.kotlinroomappmvvm.roomDatabase.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeModel(val binding: ActivityHomeBinding, val activity: AppCompatActivity) : BaseObservable() {

    var emptyRecordText: String = "No Records Found"
    lateinit var homeViewModel: HomeViewModel

    var items: List<Item>? = null
    lateinit var adapter: ItemRecyclerAdapter

    init {

        // Set up Toolbar as the ActionBar in the Fragment
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        // Set the ActionBar title to an empty string to remove the app name
        (activity as AppCompatActivity).supportActionBar?.title = "RoomDB App"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val factory = HomeViewModelFactory(activity)
        // Getting the response
        homeViewModel = ViewModelProvider(activity,factory).get(HomeViewModel::class.java)

        //RecyclerView
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        // Initialize the adapter with an empty list
        adapter = ItemRecyclerAdapter(activity, listOf(), object : ItemRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(item: Item) {
                navigateToDetailPage(item)
            }
        })
        binding.recyclerView.adapter = adapter

        loadData(adapter)
    }

    // Handling toolbar menu item clicks
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menu_deleteall -> {
                deleteItem()
                return true
            }
            R.id.menu_add -> {
                goToAddPage()
                return true
            }
            android.R.id.home -> {
                // Handle toolbar's back button
                activity.onBackPressed()
                Toast.makeText(activity,"BackClick", Toast.LENGTH_LONG).show()
                return true
            }

        }
        return false
    }

    // Navigate to DetailActivity, passing the clicked Item
    private fun navigateToDetailPage(item: Item) {
        val intent = Intent(activity, ItemDetailsActivity::class.java)
        intent.putExtra("item", item) // Pass the item as Serializable
        activity.startActivity(intent)
    }

    fun onSearchItem(text: String){
        GlobalScope.launch(Dispatchers.Main) {
            val isSuccess = homeViewModel.getSearchedRecords(text)
            if(isSuccess) {
                Log.d("TAGGY", "Successfully Searched")
                loadData(adapter)
            }else{
                Log.d("TAGGY", "Something went Wrong while Searching item")
            }

        }
    }

    fun deleteItem() {
        GlobalScope.launch(Dispatchers.Main) {
            val isSuccess = homeViewModel.deleteAllItem()
            if(isSuccess) {
                Log.d("TAGGY", "Successfully update")
                loadData(adapter)
            }else{
                Log.d("TAGGY", "Something went Wrong while Delete all items")
            }
        }
    }

    fun goToAddPage(){
        val intent = Intent(activity, AddItemActivity::class.java)
        activity.startActivity(intent)
        //(activity as Activity).finish()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun loadData (adapter: ItemRecyclerAdapter) {
        items = null
        homeViewModel.getAllRecords().observe(activity, Observer { itemList ->
            if(itemList.isNotEmpty()){
                var result = ""
                items = itemList
                adapter.updateItems(itemList)
                binding.tvNoPost.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE

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
                binding.recyclerView.visibility = View.GONE
            }
        })
    }
}