package be.supinfo.supermarketapp.data

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import be.supinfo.supermarketapp.util.BASE_URL
import be.supinfo.supermarketapp.util.MyHelper
import be.supinfo.supermarketapp.util.REPOSITORY_TAG
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Repository(var app: Context) {

    val products = MutableLiveData<List<Product>>()
    private val listType = Types.newParameterizedType(List::class.java, Product::class.java)


    init {
        refreshData()
        Log.i(REPOSITORY_TAG, "${networkAvailable()}")
    }

    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch { callWebService() }
    }


    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val service = retrofit.create(ProductService::class.java)

            val serviceData = service.getProductsData().body() ?: emptyList()

            for (product in serviceData) {
                Log.i(REPOSITORY_TAG, product.title)
            }

            products.postValue(serviceData)
        }
    }

    fun getProductsData() {
        val dataFromAssets = MyHelper.getDataFromAssets(app, "products_data.json")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)
        products.value = adapter.fromJson(dataFromAssets) ?: emptyList()
    }


    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}