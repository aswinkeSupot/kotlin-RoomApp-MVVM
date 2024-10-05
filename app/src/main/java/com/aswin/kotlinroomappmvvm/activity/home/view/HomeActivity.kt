package com.aswin.kotlinroomappmvvm.activity.home.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.activity.addItem.view.AddItemActivity
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

    lateinit var homeViewModel: HomeViewModel
    lateinit var itemList: MutableList<Item>
    lateinit var adapter: ItemRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val factory = HomeViewModelFactory(applicationContext)
        // Getting the response
        homeViewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)


        //RecyclerView
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //Post arrayList
        itemList = arrayListOf<Item>()


        GlobalScope.launch(Dispatchers.Main) {
            homeViewModel.getAllRecords().observe(this@HomeActivity, Observer {
                if(it.size >0){
                    var result = ""

                    for ((index, item) in it.withIndex()){
                        result = result + "${index+1}.  item = ${item.name} \n     price = ${item.price} \n     quantity = ${item.quantity} \n__________________\n"

                        var itemval = Item(
                            0,
                            item.name,
                            item.price,
                            item.quantity
                        )
                        itemList.add(itemval)
                    }

//                    binding.tvRecords.text = result

                    // RecyclerView
                    adapter = ItemRecyclerAdapter(applicationContext,itemList)
                    binding.recyclerView.setAdapter(adapter)
                    adapter.notifyDataSetChanged()

                }else{
//                    binding.tvRecords.text = "No Records Found"
                }
            })
        }

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this@HomeActivity, AddItemActivity::class.java)
            startActivity(intent)
        }
    }
}