package com.jsn.android_mvvm_unit_testing.api

import com.jsn.android_mvvm_unit_testing.models.ProductListItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsAPI {
    @GET("/products")
    suspend fun getProducts(): Response<List<ProductListItem>>
}