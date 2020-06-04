package be.supinfo.supermarketapp.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import be.supinfo.supermarketapp.data.remote.Rayon

@Entity(
    tableName = "ProductsInCart",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Rayon::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("rayonId"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class ProductInCart(
    val productId: Int,
    val quantity: Int
)