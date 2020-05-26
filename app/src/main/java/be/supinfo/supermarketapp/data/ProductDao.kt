package be.supinfo.supermarketapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// Data access object
@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    suspend fun getAll(): List<Product>

    @Insert
    suspend fun insertProduct(product: Product)

    @Insert
    suspend fun insertProducts(products: List<Product>)

    @Query("DELETE FROM products")
    suspend fun deleteAll()

}