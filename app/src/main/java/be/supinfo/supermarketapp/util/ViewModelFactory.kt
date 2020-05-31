package be.supinfo.supermarketapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.ui.details.ProductsDetailsViewModel
import be.supinfo.supermarketapp.ui.main.ProductsViewModel
import javax.inject.Inject

class ViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var repository: Repository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        App.component.inject(this)

        return when {
            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                ProductsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProductsDetailsViewModel::class.java) -> {
                ProductsDetailsViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}