package be.supinfo.supermarketapp.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.data.remote.Product

class ProductsDetailsViewModel(private val repository: Repository) :
    ViewModel() {
    var selectedProduct = MutableLiveData<Product>()

    init {
    }

//    fun selectProduct(product: Product) {
//        selectedProduct.value = product
//        Log.i("oscour", "${selectedProduct.value?.description}")
//    }

}