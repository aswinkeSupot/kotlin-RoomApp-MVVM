package com.aswin.kotlinroomappmvvm.activity.home.viewModel

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.activity.addItem.view.AddItemActivity
import com.aswin.kotlinroomappmvvm.activity.itemDetail.view.ItemDetailsActivity
import com.aswin.kotlinroomappmvvm.adapter.ItemRecyclerAdapter
import com.aswin.kotlinroomappmvvm.databinding.ActivityHomeBinding
import com.aswin.kotlinroomappmvvm.repository.ItemRepository
import com.aswin.kotlinroomappmvvm.roomDatabase.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Aswin on 03-10-2024.
 */
class HomeViewModel(context: Context, binding: ActivityHomeBinding): ViewModel(), Observable {

    private val propertyChangeRegistry = PropertyChangeRegistry()
    private val binding = binding;
    private val context = context;

    var emptyRecordText: String = "No Records Found"

    var items: List<Item>? = null
    lateinit var adapter: ItemRecyclerAdapter

    private var repository: ItemRepository = ItemRepository(context)
    lateinit var allData: LiveData<List<Item>>


    init {
        allData = repository.getAllDataFromDB()

        //RecyclerView
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with an empty list
        adapter = ItemRecyclerAdapter(context, listOf(), object : ItemRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(item: Item) {
                navigateToDetailPage(item)
            }
        })
        binding.recyclerView.adapter = adapter

        loadData(adapter)
    }

    // Navigate to DetailActivity, passing the clicked Item
    private fun navigateToDetailPage(item: Item) {
        val intent = Intent(context, ItemDetailsActivity::class.java)
        intent.putExtra("item", item) // Pass the item as Serializable
        context.startActivity(intent)
    }

    // Handling toolbar menu item clicks
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menu_deleteall -> {
                GlobalScope.launch(Dispatchers.Main) {
                    deleteAllItem()
                }

                return true
            }
            R.id.menu_add -> {
                goToAddPage()
                return true
            }

        }
        return false
    }

    suspend fun getSearchedRecords(query: String){
        var isSuccess = false

        withContext(Dispatchers.IO) {
            allData = repository.getSearchDataFromDB(query)
            isSuccess = true
        }
        if(isSuccess){
            loadData(adapter)
        }
    }

    suspend fun deleteAllItem(){
        var isSuccess = false
        withContext(Dispatchers.IO) {
            isSuccess = repository.deleteAllItem()
        }
        if(isSuccess){
            loadData(adapter)
        }
    }

    fun goToAddPage(){
        val intent = Intent(context, AddItemActivity::class.java)
        context.startActivity(intent)
        // Check if the context is an instance of Activity before calling finish
        //if (context is Activity) {
        //    (context as Activity).finish()
        //}
    }

    fun getAllRecords():  LiveData<List<Item>> {
        return allData
    }


    private fun loadData (adapter: ItemRecyclerAdapter) {
        items = null
        getAllRecords().observe(binding.lifecycleOwner!!, Observer { itemList ->
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

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback)
    }

}