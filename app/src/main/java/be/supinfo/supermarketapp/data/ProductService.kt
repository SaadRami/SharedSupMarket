package be.supinfo.supermarketapp.data

import retrofit2.Response
import retrofit2.http.GET

interface ProductService {

    // suspend : means that this function is designed to be called from within a couroutine
    // coroutine can either be run in a background thread or a UI thread.
    // for a function to be part from coroutine call, it must have the suspend keyword
    @GET("/")
    suspend fun getProductsData(): Response<List<Product>>

    @GET("/recommendations")
    suspend fun getRecommendedProducts(): Response<List<Product>>
}