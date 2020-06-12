package be.supinfo.supermarketapp.data.remote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

// POJO
@Entity(
    tableName = "Products",
    foreignKeys = [ForeignKey(
        entity = Rayon::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("rayonId"),
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Product(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "productId") val productId: Long,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: String,
    @Json(name = "description") val description: String,
    @Json(name = "filename") val filename: String,
    @Json(name = "height") val height: Int,
    @Json(name = "width") val width: Int,
    @Json(name = "price") val price: Double,
    @Json(name = "rating") val rating: Int,
    @Json(name = "rayonId") @ColumnInfo(name = "rayonId") val rayonId: Long,
    var quantityInCart: Int
) {
    val imageUrl: String
        get() {
            return ""
        }
}

//    constructor(
//        title: String,
//        type: String,
//        description: String,
//        filename: String,
//        height: Int,
//        width: Int,
//        price: Double,
//        rating: Int,
//        rayonId: Int,
//        quantityInCart: Int
//    ) :
//            this(
//                0,
//                title,
//                type,
//                description,
//                filename,
//                height,
//                width,
//                price,
//                rating,
//                rayonId,
//                quantityInCart
//            )
//}
