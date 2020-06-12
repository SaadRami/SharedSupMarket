package be.supinfo.supermarketapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import be.supinfo.supermarketapp.data.local.dao.CartProductDao
import be.supinfo.supermarketapp.data.local.dao.ProductDao
import be.supinfo.supermarketapp.data.local.dao.RayonDao
import be.supinfo.supermarketapp.data.local.dao.TransactionDao
import be.supinfo.supermarketapp.data.remote.Product
import be.supinfo.supermarketapp.data.remote.Rayon
import be.supinfo.supermarketapp.util.Converters

// Add an anotation
// entities list of classes (entities)
// version of the database, upgrade the version when restructuring the databse
// exportSchema = false -> don't generate files to document the database
@Database(
    entities = [Product::class, Rayon::class, CartProduct::class, TransactionStore::class],
    version = 26,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    // for each data access object add an abstract function
    abstract fun productDao(): ProductDao

    abstract fun rayonDao(): RayonDao

    abstract fun transactionDao(): TransactionDao

    abstract fun productInCartDao(): CartProductDao

    // we need a function to get an instance of this class
    companion object {
        // variable pouvant être accedé à partir de plusieurs threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // we need a function to get the instance of the database
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                // synchronized = means that the code inside can only be called by one thread at time
                synchronized(this) {
                    // pass the application context to make sure we're not working with an activity
                    // we pass a string that will be used as a name of the physical file in persistent storage
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "products.db"
                    ).build()
                }
            }
            // add an assertion = if this is null just crash the APP
            return INSTANCE!!
        }
    }
}