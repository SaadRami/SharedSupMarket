package be.supinfo.supermarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import be.supinfo.supermarketapp.data.remote.Product

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