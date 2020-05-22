package be.supinfo.supermarketapp.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.data.Product
import be.supinfo.supermarketapp.util.MyHelper
import be.supinfo.supermarketapp.util.VIEWMMODEL_TAG
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class MyViewModel(app: Application) : AndroidViewModel(app) {

    // Moshi
    // Create a custom type that will be used to create or parse JSon data
    private val listType = Types.newParameterizedType(List::class.java, Product::class.java)

//    Always save the application reference. This is being passed in as the viewModel object is being created,
//    but it won't persist for the lifetime of the viewModel, and so I'll handle that by creating a new variable here
//    called context, and I'll reference it from the app object, and now I'll be able to use the context to get resources
//    or do other things that are part of the Android structure.

    val prenom = MutableLiveData<String>()
    private val context = app

    // This is where we're going to store our data
    // Initialisation des elements présents dans l'UI
    init {
        Log.i(VIEWMMODEL_TAG, "viewmodel")
        // prenom refers to the live data object, to save its value use .value
        prenom.value = ""
        // By setting a value we're publishing them

        val dataFromResources = MyHelper.getDataFromResources(context, R.raw.products_data)
        val dataFromAssets = MyHelper.getDataFromAssets(context, "products_data.json")
//        Log.i(VIEWMMODEL_TAG, dataFromAss)
        parseJson(dataFromAssets)

    }

    fun displayData() {
        prenom.value = "test"
    }


    fun parseJson(text: String) {
        // Create an instance of the Moshi Library, use build pattern to create it
        val moshi = Moshi.Builder().build()
        // create moshi adapter, set its type to Jsonadapter using generic class(List<ModelClass>)
        // initialize it with moshi.adapter to wich we pass the listType
        val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)
        val products = adapter.fromJson(text)

        for (product in products ?: emptyList()) {
            Log.i(VIEWMMODEL_TAG, "${product.title}  (\$${product.price})")
        }
    }
}