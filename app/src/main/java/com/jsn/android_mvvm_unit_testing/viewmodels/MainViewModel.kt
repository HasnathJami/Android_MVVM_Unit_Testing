package com.jsn.android_mvvm_unit_testing.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsn.android_mvvm_unit_testing.models.ProductListItem
import com.jsn.android_mvvm_unit_testing.repository.ProductRepository
import com.jsn.android_mvvm_unit_testing.utils.NetworkResult
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = MutableLiveData<NetworkResult<List<ProductListItem>>>()
    val products : LiveData<NetworkResult<List<ProductListItem>>>
        get() = _products

    fun getProducts() {
        viewModelScope.launch {
            val result = repository.getProducts()
            _products.postValue(result  )
        }
    }
}