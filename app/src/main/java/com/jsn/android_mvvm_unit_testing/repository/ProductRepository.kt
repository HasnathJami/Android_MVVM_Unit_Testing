package com.jsn.android_mvvm_unit_testing.repository

import com.jsn.android_mvvm_unit_testing.api.ProductsAPI
import com.jsn.android_mvvm_unit_testing.models.ProductListItem
import com.jsn.android_mvvm_unit_testing.utils.NetworkResult

class ProductRepository(private val productsAPI: ProductsAPI) {
    suspend fun getProducts(): NetworkResult<List<ProductListItem>> {
        val response = productsAPI.getProducts()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkResult.Success(responseBody)

            } else {
                NetworkResult.Error("Something went wrong")
            }
        } else {
            NetworkResult.Error("Something went wrong")
        }
    }
}