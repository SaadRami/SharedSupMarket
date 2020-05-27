package be.supinfo.supermarketapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

// POJO
@Entity(tableName = "Products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Int,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: String,
    @Json(name = "description") val description: String,
    @Json(name = "filename") val filename: String,
    @Json(name = "height") val height: Int,
    @Json(name = "width") val width: Int,
    @Json(name = "price") val price: Double,
    @Json(name = "rating") val rating: Int
) {
    val imageUrl
        //        get() = "$BASE_URL/$filename"
        get() = "https://cdn-prod.scalefast.com/public/assets/img/resized/squareenix-store-v3/29a4af7a80c8aba3cb93218ef6cde90d_640_640_Q10.jpg"
}