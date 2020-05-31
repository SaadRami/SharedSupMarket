package be.supinfo.supermarketapp.ui.shared

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.data.remote.Product
import be.supinfo.supermarketapp.util.TAG_VIEWMODEL
import javax.inject.Inject

class SharedViewModel(private val repository: Repository) :
    ViewModel() {

    // private val dataRepo: Repository = Repository()
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val prenom = MutableLiveData<String>()
    val products = repository.productsLiveData
    var selectedProduct = MutableLiveData<Product>()
    //val selectedProduct = MutableLiveData<Product>()

    //private val context = app
    val appTitle = MutableLiveData<String>()


    init {
        App.component.inject(this)
        Log.i(TAG_VIEWMODEL, "viewmodel")
        updateTitle()
    }

    fun updateTitle() {
        val signature =
            sharedPreferences.getString("signature", "Client")
        appTitle.value = "Client : $signature"
    }

    fun displayData() {
        prenom.value = "tzzefzefzefzefzefzefest"
    }

    fun refreshProducts() {
        repository.refreshData()
    }

    fun selectProduct(product: Product) {
        selectedProduct.value = product
        Log.i("oscour", "${selectedProduct.value?.description}")
    }

}