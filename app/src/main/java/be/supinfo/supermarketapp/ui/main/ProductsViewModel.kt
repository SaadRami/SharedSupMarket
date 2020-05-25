package be.supinfo.supermarketapp.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import be.supinfo.supermarketapp.data.Product
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.util.VIEWMMODEL_TAG

class ProductsViewModel(app: Application) : AndroidViewModel(app) {


    private val dataRepo: Repository = Repository(app)
    val products = dataRepo.products
    val prenom = MutableLiveData<String>()
    val selectedProduct = MutableLiveData<Product>()
    private val context = app

    init {
        Log.i(VIEWMMODEL_TAG, "viewmodel")
    }

    fun displayData() {
        prenom.value = "tzzefzefzefzefzefzefest"
    }

    fun refreshProducts() {
        dataRepo.refreshData()
    }
}