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

    // Moshi
    // Create a custom type that will be used to create or parse JSon data
    // Detail of data acquisition that the viewmodel doens't need to know about
    private val listType = Types.newParameterizedType(List::class.java, Product::class.java)


    init {
        // To do a coroutine call we're going to wrap the callWebService call in a bit of code
        // Dispatchers.IO = do the call in the bg thread
        // Dispatchers.MAIN = do the call in the UI thread
        // the function callWebService is called within a Coroutine and it's a BG thread
        CoroutineScope(Dispatchers.IO).launch { callWebService() }
        Log.i(REPOSITORY_TAG, "${networkAvailable()}")
    }

    // Workertread means that the function will be called from a background thread, we'll be using coroutine to do that
    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
            // get retrofit from a builder pattern
            // integrate the moshi library for parsing json, we don't need to create an adapter
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            // use retrofit object to create an instance of our productservice
            val service = retrofit.create(ProductService::class.java)
            // service.getProductsData().body() = returns the data from the webservice
            val serviceData = service.getProductsData().body() ?: emptyList()
            // To save the value to the liveData we can't use value or setValue, that property or function can only be
            // called from an UI thread, instead we call postValue wich is designed to be called from a Background thread
            products.postValue(serviceData)
            // because we're using corotuines we don't have to do any callbacks, all the complexity will be done on the bg thread
        }
    }

    fun getProductsData() {
        // val dataFromResources = MyHelper.getDataFromResources(context, R.raw.products_data)
        val dataFromAssets = MyHelper.getDataFromAssets(app, "products_data.json")
        //Log.i(VIEWMMODEL_TAG, dataFromAss)


        // Create an instance of the Moshi Library, use build pattern to create it
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        // create moshi adapter, set its type to Jsonadapter using generic class(List<ModelClass>)
        // initialize it with moshi.adapter to wich we pass the listType
        val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)
        products.value = adapter.fromJson(dataFromAssets) ?: emptyList()
    }

    // The networkinfo class is deprecated starting with Android Q, if we want to work
    // with this approach = suppress the warnings
    //So this function can now be used to reliably report whether the application has network access.
    // Before you call any web service you can call this function to find out the status of your connectivity.
    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}