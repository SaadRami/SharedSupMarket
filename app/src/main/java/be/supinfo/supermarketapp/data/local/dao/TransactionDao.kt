package be.supinfo.supermarketapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import be.supinfo.supermarketapp.data.local.TransactionStore
import be.supinfo.supermarketapp.data.local.TransactionsWithProductsAndCartProducts

// Data access object
@Dao
interface TransactionDao {


    @Query("SELECT * FROM Transactions")
    suspend fun getAll(): List<TransactionStore>

    @Query("SELECT COUNT(*) FROM Transactions")
    suspend fun getCount(): Int

    @Insert
    suspend fun insertTransaction(transactionStore: TransactionStore): Long

    @Insert
    suspend fun insertTransactions(transactionStores: List<TransactionStore>)

    @Query("DELETE FROM Transactions")
    suspend fun deleteTransactions()

    @Transaction
    @Query("SELECT * FROM Transactions")
    fun getTransactionsWithProducts(): List<TransactionsWithProductsAndCartProducts>

}