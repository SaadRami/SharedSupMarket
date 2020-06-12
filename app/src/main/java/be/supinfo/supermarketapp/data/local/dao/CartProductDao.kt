package be.supinfo.supermarketapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import be.supinfo.supermarketapp.data.local.CartProduct

@Dao
interface CartProductDao {
    @Query("SELECT * FROM CartProducts")
    suspend fun getAll(): List<CartProduct>

    @Insert
    suspend fun insertProductInCart(cartProduct: CartProduct)

    @Insert
    suspend fun insertProductsInCart(productsInCartProduct: List<CartProduct>)

    @Query("DELETE FROM CartProducts")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM CartProducts")
    suspend fun getCount(): Int

}