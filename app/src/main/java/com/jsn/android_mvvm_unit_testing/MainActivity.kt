package com.jsn.android_mvvm_unit_testing

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jsn.android_mvvm_unit_testing.adapter.ProductAdapter
import com.jsn.android_mvvm_unit_testing.utils.NetworkResult
import com.jsn.android_mvvm_unit_testing.viewmodels.MainViewModel
import com.jsn.android_mvvm_unit_testing.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.productList)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val repository = (application as StoreApplication).productsRepository
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        mainViewModel.products.observe(this@MainActivity) {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("CheckTest1", it.data.toString())
                    adapter = ProductAdapter(it.data!!)
                    recyclerView.adapter = adapter
                }

                is NetworkResult.Error -> {
                    Toast.makeText(this@MainActivity, "Error Occurred", Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}