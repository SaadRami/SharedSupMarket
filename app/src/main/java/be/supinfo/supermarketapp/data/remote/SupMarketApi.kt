package be.supinfo.supermarketapp.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface SupMarketApi {
    @GET("/products")
    suspend fun getAllProductsData(): Response<List<Product>>

    @GET("/rayons")
    suspend fun getAllRayonsData(): Response<List<Rayon>>


    @GET("/recommended_products")
    suspend fun getRecommendedProducts(): Response<List<Product>>

}