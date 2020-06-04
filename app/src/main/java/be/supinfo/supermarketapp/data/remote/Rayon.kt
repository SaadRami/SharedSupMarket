package be.supinfo.supermarketapp.data.remote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "Rayons")
data class Rayon(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @Json(name = "title") val title: String
) {
    val imageUrl: String
        //        get() = "$BASE_URL/$filename"
        get() = "https://logodix.com/logo/1038765.png"
}