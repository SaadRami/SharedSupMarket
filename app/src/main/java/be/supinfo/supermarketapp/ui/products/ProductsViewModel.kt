package be.supinfo.supermarketapp.ui.products

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.data.remote.Rayon
import javax.inject.Inject

class ProductsViewModel(private val repository: Repository) :
    ViewModel() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val selectedRayon = MutableLiveData<Rayon>()
    val productsByRayon = repository.productsByRayon

    init {
        App.component.inject(this)
    }

    fun selectRayon(rayon: Rayon) {
        selectedRayon.value = rayon
        repository.fetchProductsByRayon(rayon)
    }

}