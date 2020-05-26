package be.supinfo.supermarketapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

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
        get() = "https://ws.mcdonalds.fr/media/a7/cc/2b/a7cc2b7a1ef57acc4d6aff1252f21c16b27242ca"
}