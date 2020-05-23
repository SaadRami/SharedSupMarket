package be.supinfo.supermarketapp.data

import android.content.Context
import be.supinfo.supermarketapp.util.MyHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Repository {
    // Moshi
    // Create a custom type that will be used to create or parse JSon data
    // Detail of data acquisition that the viewmodel doens't need to know about
    private val listType = Types.newParameterizedType(List::class.java, Product::class.java)

    fun getData(context: Context): List<Product> {
        // val dataFromResources = MyHelper.getDataFromResources(context, R.raw.products_data)
        val dataFromAssets = MyHelper.getDataFromAssets(context, "products_data.json")
        //Log.i(VIEWMMODEL_TAG, dataFromAss)


        // Create an instance of the Moshi Library, use build pattern to create it
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        // create moshi adapter, set its type to Jsonadapter using generic class(List<ModelClass>)
        // initialize it with moshi.adapter to wich we pass the listType
        val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)
        val products = adapter.fromJson(dataFromAssets) ?: emptyList()

        return products
    }
}