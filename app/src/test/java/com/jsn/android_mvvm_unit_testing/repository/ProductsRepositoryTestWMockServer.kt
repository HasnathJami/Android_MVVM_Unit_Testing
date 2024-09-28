package com.jsn.android_mvvm_unit_testing.repository

import com.jsn.android_mvvm_unit_testing.api.ProductsAPI
import com.jsn.android_mvvm_unit_testing.utils.NetworkResult
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsRepositoryTestWMockServer {

    lateinit var mockWebServer: MockWebServer
    lateinit var apiService: ProductsAPI


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ProductsAPI::class.java)
    }

    @Test
    fun testGetProducts_EmptyList() = runTest {
        val mockResponse = MockResponse()
        mockResponse
            .setResponseCode(404)
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)
        val sut = ProductRepository(apiService)
        val result = sut.getProducts()
        val request = mockWebServer.takeRequest()

        Assert.assertEquals(true, result is NetworkResult.Error)
        Assert.assertEquals(null, result.data?.size)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}