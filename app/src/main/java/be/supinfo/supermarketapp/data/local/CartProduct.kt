package be.supinfo.supermarketapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(
    tableName = "CartProducts",
    primaryKeys = ["transactionId", "productId"]
)

data class CartProduct(
    @ColumnInfo(name = "productId") val productId: Long,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
    @ColumnInfo(name = "transactionId") val transactionId: Long
)

