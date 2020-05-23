package be.supinfo.supermarketapp.data

import com.squareup.moshi.Json

data class Product(
 @Json(name = "title") val title: String,
 @Json(name = "type") val type: String,
 @Json(name = "description") val description: String,
 @Json(name = "filename") val filename: String,
 @Json(name = "height") val height: Int,
 @Json(name = "width") val width: Int,
 @Json(name = "price") val price: Double,
 @Json(name = "rating") val rating: Int

)