package be.supinfo.supermarketapp.data

data class Product(
    val title: String,
    val type: String,
    val description: String,
    val filename: String,
    val height: Int,
    val width: Int,
    val price: Double,
    val rating: Int
)