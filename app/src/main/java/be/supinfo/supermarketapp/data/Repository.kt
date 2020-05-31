package be.supinfo.supermarketapp.data

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.data.local.ProductDao
import be.supinfo.supermarketapp.data.remote.Product
import be.supinfo.supermarketapp.data.remote.SupMarketApi
import be.supinfo.supermarketapp.util.MyHelper
import be.supinfo.supermarketapp.util.TAG_REPOSITORY
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class Repository {
    val productsLiveData = MutableLiveData<List<Product>>()
    private val listType = Types.newParameterizedType(List::class.java, Product::class.java)

    //   private val dataBase = AppDatabase.getDatabase(app).productDao()
    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var productDao: ProductDao

    @Inject
    lateinit var app: Application

    init {
        App.component.inject(this)

        CoroutineScope(Dispatchers.IO).launch {
            val data = productDao.getAll()
            if (data.isEmpty()) {
                callWebService()
            } else {
                productsLiveData.postValue(data)
                Log.i(TAG_REPOSITORY, "From Room database")
                //Coroutine lets you switch threads in the fly
                withContext(Dispatchers.Main) {
                    // This code will be executed from within The Main Thread (The UI Thread, The Foreground thread)
                    Toast.makeText(app, "Get Data From Database", Toast.LENGTH_LONG)
                        .show()
                }
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
        Log.i(TAG_REPOSITORY, "oscour")
        CoroutineScope(Dispatchers.IO).launch { callWebService() }
    }


    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Get Data From Remote", Toast.LENGTH_LONG)
                    .show()
            }
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(MoshiConverterFactory.create())
//                .build()
            val service = retrofit.create(SupMarketApi::class.java)
            val serviceData = service.getProductsData().body() ?: emptyList()

            for (product in serviceData) {
                Log.i(TAG_REPOSITORY, product.title)
            }

            productsLiveData.postValue(serviceData)
            //saveDataToCache(serviceData)
            productDao.deleteAll()
            productDao.insertProducts(serviceData)
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

    private fun getProductsData() {
        val dataFromAssets = MyHelper.getDataFromAssets(app, "products_data.json")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)
        productsLiveData.value = adapter.fromJson(dataFromAssets) ?: emptyList()
    }

    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

}