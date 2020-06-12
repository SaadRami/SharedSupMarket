package be.supinfo.supermarketapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import be.supinfo.supermarketapp.data.remote.Product

// Data access object
@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    suspend fun getAll(): List<Product>
//
//    @Query("SELECT * FROM Products WHERE productId = :prdId")
//    suspend fun getProductOfProductInCart(prdId: Int): Product

    @Query("SELECT * FROM Products WHERE rayonId = :rayonId")
    suspend fun getProductsByRayonId(rayonId: Long): List<Product>

    @Query("SELECT productId FROM Products WHERE title = :title")
    suspend fun getProductByName(title: String): Long

    @Query("SELECT * FROM Products WHERE productId = :prdId")
    suspend fun getProductFromProductInCart(prdId: Long): Product

    @Insert
    suspend fun insertProduct(product: Product)

    @Insert
    suspend fun insertProducts(products: List<Product>)

    @Query("DELETE FROM products")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM Products")
    suspend fun getCount(): Int

}