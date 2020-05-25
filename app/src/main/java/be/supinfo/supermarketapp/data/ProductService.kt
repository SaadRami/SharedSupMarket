package be.supinfo.supermarketapp.data

import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("/")
    suspend fun getProductsData(): Response<List<Product>>

    @GET("/recommendations")
    suspend fun getRecommendedProducts(): Response<List<Product>>
}