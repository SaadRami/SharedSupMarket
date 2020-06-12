package be.supinfo.supermarketapp.data.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import be.supinfo.supermarketapp.data.remote.Product

data class TransactionsWithProductsAndCartProducts(
//    @Embedded
//    var cartProducts: List<CartProduct>,
//    @Embedded val transaction: TransactionStore,
//    @Relation(
//        parentColumn = "transactionId",
//        entityColumn = "productId",
//        associateBy = Junction(CartProduct::class)
//    )
//    val products: List<Product>

    @Embedded
    var transaction: TransactionStore,
    @Relation(
        entity = CartProduct::class,
        entityColumn = "transactionId",
        parentColumn = "transactionId"
    )
    var cartProducts: List<CartProduct> = emptyList(),
    @Relation(
        entity = Product::class,
        entityColumn = "productId",
        parentColumn = "transactionId",
        associateBy = (Junction(
            value = CartProduct::class,
            parentColumn = "transactionId",
            entityColumn = "productId"
        ))
    )
    var products: List<Product> = emptyList()

) {
    override fun toString(): String {
        val result = StringBuilder()
        result.append("Transaction N : ${transaction.transactionId}")
            .append("\n Product List :")
        for ((index, value) in products.withIndex()) {
            result.append("\nProduct : ${value.title}, quantity : ${cartProducts[index].quantity}")
        }
        return result.toString()
    }
}



