package be.supinfo.supermarketapp.data

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.data.local.CartProduct
import be.supinfo.supermarketapp.data.local.TransactionStore
import be.supinfo.supermarketapp.data.local.TransactionsWithProductsAndCartProducts
import be.supinfo.supermarketapp.data.local.dao.CartProductDao
import be.supinfo.supermarketapp.data.local.dao.ProductDao
import be.supinfo.supermarketapp.data.local.dao.RayonDao
import be.supinfo.supermarketapp.data.local.dao.TransactionDao
import be.supinfo.supermarketapp.data.remote.Product
import be.supinfo.supermarketapp.data.remote.Rayon
import be.supinfo.supermarketapp.data.remote.SupMarketApi
import be.supinfo.supermarketapp.util.MyHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.time.LocalDate
import javax.inject.Inject

class Repository {
    val rayonsLiveData = MutableLiveData<List<Rayon>>()
    val productsByRayonLiveData = MutableLiveData<List<Product>>()
    val recommendedLiveData = MutableLiveData<List<Product>>()
    val transactionsWithProductsAndCartProductsLiveData =
        MutableLiveData<List<TransactionsWithProductsAndCartProducts>>()
    private val listType = Types.newParameterizedType(List::class.java, Rayon::class.java)
    val service: SupMarketApi

    //   private val dataBase = AppDatabase.getDatabase(app).productDao()

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var productDao: ProductDao

    @Inject
    lateinit var rayonDao: RayonDao

    @Inject
    lateinit var transactionDao: TransactionDao

    @Inject
    lateinit var cartProductDao: CartProductDao

    @Inject
    lateinit var app: Application

    init {
        App.component.inject(this)
        service = retrofit.create(SupMarketApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            ////////////////////////////////////////////
            val productData = productDao.getAll()
            val rayonData = rayonDao.getAllRayons()

            if (rayonData.isEmpty() || productData.isEmpty()) {
                callWebService()
            } else {
                rayonsLiveData.postValue(rayonData)
                /////////// get Recommended data from Webservice ////////////
                val recommendedProducts = service.getRecommendedProducts().body() ?: emptyList()
                recommendedLiveData.postValue(recommendedProducts)
//                Log.i(TAG_REPOSITORY, "From Room database")
//                //Coroutine lets you switch threads in the fly
//                withContext(Dispatchers.Main) {
//                    // This code will be executed from within The Main Thread (The UI Thread, The Foreground thread)
//                    Toast.makeText(app, "Get Data From Database", Toast.LENGTH_LONG)
//                        .show()
//                }
            }

        }

//        val data = readDataFromCache()
//        when {
//            data.isEmpty() -> {
//                refreshData()
//            }
//            else -> {
//                products.value = data
//                Log.i(REPOSITORY_TAG, "Getting data from cache")
//            }
//        }
//        Log.i(REPOSITORY_TAG, "${networkAvailable()}")
    }

    fun refreshData() {
        //    CoroutineScope(Dispatchers.IO).launch { callWebService() }
    }


    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
//            withContext(Dispatchers.Main) {
//                Toast.makeText(app, "Get Data From Remote", Toast.LENGTH_LONG)
//                    .show()
//            }
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(MoshiConverterFactory.create())
//                .build()


            val rayons = service.getAllRayonsData().body() ?: emptyList()
            val recommendedProducts = service.getRecommendedProducts().body() ?: emptyList()
            val allProducts = service.getAllProductsData().body() ?: emptyList()

            Log.i("productID", "${allProducts.size}")
//            for (prd in allProducts) {
//                Log.i("productId : ", "${prd.productId}")
//            }
//
//            for (rayon in rayons) {
//                Log.i(TAG_REPOSITORY, rayon.title)
//            }
//
//            for (recommended in recommendedProducts) {
//                Log.i(TAG_REPOSITORY, recommended.title)
//            }

            rayonsLiveData.postValue(rayons)
            recommendedLiveData.postValue(recommendedProducts)
            //saveDataToCache(serviceData)


            rayonDao.deleteAll()
            val countRayon = rayonDao.getCount()
            val countProduct = productDao.getCount()
            val countTransaction = transactionDao.getCount()
            val countProductInCart = cartProductDao.getCount()

            Log.i("COUNT", "$countRayon, $countProduct, $countProductInCart, $countTransaction")

//            productDao.deleteAll()


            rayonDao.insertRayons(rayons)
            productDao.insertProducts(allProducts)

        }
    }

    private fun saveDataToCache(dataProducts: List<Product>) {
        if (ContextCompat.checkSelfPermission(
                app,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val moshi = Moshi.Builder().build()
            val parameterizedType =
                Types.newParameterizedType(List::class.java, Product::class.java)
            val adapter: JsonAdapter<List<Product>> = moshi.adapter(parameterizedType)
            val json = adapter.toJson(dataProducts)
            MyHelper.saveTextToFile(app, json)
        }
    }

    private fun readDataFromCache(): List<Product> {
        val json = MyHelper.readTextFile(app) ?: return emptyList()

        val moshi = Moshi.Builder().build()
        val parameterizedType =
            Types.newParameterizedType(List::class.java, Product::class.java)
        val adapter: JsonAdapter<List<Product>> = moshi.adapter(parameterizedType)

        return adapter.fromJson(json) ?: emptyList()
    }

//    private fun getProductsData() {
//        val dataFromAssets = MyHelper.getDataFromAssets(app, "products_data.json")
//        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//        val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)
//        rayonsLiveData.value = adapter.fromJson(dataFromAssets) ?: emptyList()
//    }


    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }


    fun fetchProductsByRayon(rayon: Rayon): Unit {
        CoroutineScope(Dispatchers.IO).launch {
            productsByRayonLiveData.postValue(productDao.getProductsByRayonId(rayon.id))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTransaction(totalPrice: Double?, products: ArrayList<Product>?) {
        var transaction = TransactionStore(0, totalPrice!!, LocalDate.now())
        var transactionId = 0L

        CoroutineScope(Dispatchers.IO).launch {
            transactionId = transactionDao.insertTransaction(transaction)
            for (product in products!!) {
//                val productId: Long = productDao.getProductByName(product.title)
                var productInCart =
                    CartProduct(
                        product.productId,
                        product.quantityInCart,
                        transactionId
                    )
                cartProductDao.insertProductInCart(productInCart)
            }
        }
    }

    fun getAllTransactionsWithProductsInCart() {
        CoroutineScope(Dispatchers.IO).launch {
//            var trsWithProductsInCart = transactionDao.getTransactionsWithProductsInCart()
//            Log.i("trsWithProductsInCart", "${trsWithProductsInCart.size}")
            transactionsWithProductsAndCartProductsLiveData.postValue(transactionDao.getTransactionsWithProducts())
//            Log.i(
//                TAG_REPOSITORY,
//                "Transactions with products : ${transactionsWithProductsAndCartProducts.size}"
//            )
//            for (transactionWithProducts in transactionsWithProductsAndCartProducts) {
//                Log.i(TAG_REPOSITORY, "transaction : ${transactionWithProducts.toString()}")
//            }
//        }
        }

    }
}