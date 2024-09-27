package com.jsn.android_mvvm_unit_testing

import android.app.Application
import com.jsn.android_mvvm_unit_testing.api.ProductsAPI
import com.jsn.android_mvvm_unit_testing.repository.ProductRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class StoreApplication : Application() {
    lateinit var productsAPI: ProductsAPI
    lateinit var productsRepository: ProductRepository

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fakestoreapi.com/").build()

        productsAPI = retrofit.create(ProductsAPI::class.java)
        productsRepository = ProductRepository(productsAPI)
    }
}