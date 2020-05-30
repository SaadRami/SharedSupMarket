package be.supinfo.supermarketapp.data.remote

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
        get() = "https://logodix.com/logo/1038765.png"
}