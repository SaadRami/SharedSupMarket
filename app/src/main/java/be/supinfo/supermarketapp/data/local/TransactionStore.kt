package be.supinfo.supermarketapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "Transactions")
data class TransactionStore(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactionId") val transactionId: Long,
//    @ColumnInfo(name = "dateTransaction") val dateTransaction: Date,
    @ColumnInfo(name = "totalTransaction") val totalTransaction: Double,
    @ColumnInfo(name = "transactionDate") val transactionDate: LocalDate? = null
)