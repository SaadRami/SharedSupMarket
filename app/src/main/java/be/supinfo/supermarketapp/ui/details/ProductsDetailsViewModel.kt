package be.supinfo.supermarketapp.ui.details

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.data.remote.Product
import javax.inject.Inject

class ProductsDetailsViewModel @Inject constructor(private val repository: Repository) :
    ViewModel(),
    LifecycleObserver {
    val selectedProduct = MutableLiveData<Product>()


    init {
    }

    fun selectProduct(product: Product) {
        Log.i("oscour", "lol")
        selectedProduct.value = product
    }
}