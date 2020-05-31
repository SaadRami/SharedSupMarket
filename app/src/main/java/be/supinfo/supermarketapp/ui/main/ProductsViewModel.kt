package be.supinfo.supermarketapp.ui.main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.util.TAG_VIEWMODEL
import javax.inject.Inject

class ProductsViewModel(private val repository: Repository) :
    ViewModel(), LifecycleObserver {

    // private val dataRepo: Repository = Repository()
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val prenom = MutableLiveData<String>()
    val products = repository.productsLiveData
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

}