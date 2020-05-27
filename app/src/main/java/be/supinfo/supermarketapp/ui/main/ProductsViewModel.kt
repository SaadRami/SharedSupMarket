package be.supinfo.supermarketapp.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import be.supinfo.supermarketapp.data.Product
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.util.TAG_VIEWMODEL

class ProductsViewModel(app: Application) : AndroidViewModel(app) {

    private val dataRepo: Repository = Repository(app)
    val products = dataRepo.LDProducts
    val prenom = MutableLiveData<String>()
    val selectedProduct = MutableLiveData<Product>()
    private val context = app
    val appTitle = MutableLiveData<String>()


    init {
        Log.i(TAG_VIEWMODEL, "viewmodel")
        updateTitle()
    }

    fun updateTitle() {
        val signature =
            PreferenceManager.getDefaultSharedPreferences(context).getString("signature", "Client")
        appTitle.value = "Client : $signature"
    }

    fun displayData() {
        prenom.value = "tzzefzefzefzefzefzefest"
    }

    fun refreshProducts() {
        dataRepo.refreshData()
    }
}