package be.supinfo.supermarketapp.data

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import be.supinfo.supermarketapp.util.MyHelper
import be.supinfo.supermarketapp.util.REPOSITORY_TAG
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Repository(var app: Context) {

    val products = MutableLiveData<List<Product>>()

    // Moshi
    // Create a custom type that will be used to create or parse JSon data
    // Detail of data acquisition that the viewmodel doens't need to know about
    private val listType = Types.newParameterizedType(List::class.java, Product::class.java)


    init {
        getProductsData()
        Log.i(REPOSITORY_TAG, "${networkAvailable()}")
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