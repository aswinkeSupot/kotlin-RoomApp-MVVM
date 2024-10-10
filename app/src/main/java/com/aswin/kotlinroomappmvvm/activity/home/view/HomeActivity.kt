package com.aswin.kotlinroomappmvvm.activity.home.view

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.aswin.kotlinroomappmvvm.R
import com.aswin.kotlinroomappmvvm.activity.home.model.HomeModel
import com.aswin.kotlinroomappmvvm.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var homeModel: HomeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        // Set lifecycleOwner for data binding
        binding.lifecycleOwner = this
        homeModel = HomeModel(binding,this)
        binding.homeModel = homeModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Delegate the menu handling to HomeModel
        return homeModel.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        // Change Toolbar button Text Color
        menu?.let {
            /**Customize Add Menu**/
            val menuItem = it.findItem(R.id.menu_add)
            val spanString = SpannableString(menuItem.title)
            spanString.setSpan(ForegroundColorSpan(Color.RED), 0, spanString.length, 0)
            menuItem.title = spanString


            /**Customize Search Menu**/
            // Get the SearchView from the menu
            val searchItem = menu.findItem(R.id.menu_search)
            val searchView = searchItem.actionView as SearchView

            // Customize the search icon
//            searchView.setIconified(true) // Prevents showing the EditText initially
            searchView.setQueryHint("Search item...") // Set custom hint

            // Change the search text color and hint text color
            val searchTextView = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            searchTextView.setTextColor(Color.RED) // Set text color
            searchTextView.setHintTextColor(Color.MAGENTA) // Set hint color

            // Set up listener to handle search icon click
            searchView.setOnSearchClickListener {
                // Optionally, you can do something when the search view is opened
            }

            // Optionally, you can handle the search query text listener to perform actions
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d("TAGGY", "onQueryTextSubmit: $query")
                    // Handle search query submission
                    homeModel.onSearchItem(query!!)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("TAGGY", "onQueryTextChange: $newText")
                    // Handle text changes
                    homeModel.onSearchItem(newText!!)
                    return true
                }
            })
        }
        return super.onPrepareOptionsMenu(menu)
    }
}