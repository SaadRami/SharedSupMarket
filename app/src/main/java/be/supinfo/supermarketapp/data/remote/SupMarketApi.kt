package be.supinfo.supermarketapp.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface SupMarketApi {
    @GET("/")
    suspend fun getProductsData(): Response<List<Product>>

    @GET("/recommendations")
    suspend fun getRecommendedProducts(): Response<List<Product>>
}