package com.jsn.android_mvvm_unit_testing.repository

import com.jsn.android_mvvm_unit_testing.api.ProductsAPI
import com.jsn.android_mvvm_unit_testing.models.ProductListItem
import com.jsn.android_mvvm_unit_testing.utils.NetworkResult
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class ProductRepositoryTest {

    @Mock
    lateinit var productsAPI: ProductsAPI

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetProducts_EmptyList() = runTest {

        val sut = ProductRepository(productsAPI)
        val result = sut.getProducts()

        assertEquals(true, result is NetworkResult.Success)
        assertEquals(0, result.data?.size)

    }

    @Test
    fun testGetProducts_expectedProductList() = runTest {
        val productList = listOf<ProductListItem>(
            ProductListItem("", "", 1, "", 100.3, "Computer"),
            ProductListItem("", "", 2, "", 120.5, "Laptop")
        )
        Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.success(productList))

        val sut = ProductRepository(productsAPI)
        val result = sut.getProducts()

        assertEquals(true, result is NetworkResult.Success)
        assertEquals(2, result.data?.size)
        assertEquals("Computer", result.data!![0].title)
    }

    @Test
    fun testGetProducts_expectedError() = runTest {

        Mockito.`when`(productsAPI.getProducts())
            .thenReturn(Response.error(401, "Unauthorized".toResponseBody()))

        val sut = ProductRepository(productsAPI)
        val result = sut.getProducts()

        assertEquals(true, result is NetworkResult.Error)
        assertEquals("Something went wrong", result.message)
    }

    @After
    fun tearDown() {
//      Dispatchers.resetMain()
    }
}