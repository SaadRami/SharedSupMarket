package be.supinfo.supermarketapp.util

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.ui.details.ProductsDetailsViewModel
import be.supinfo.supermarketapp.ui.main.ProductsViewModel
import be.supinfo.supermarketapp.ui.shared.SharedViewModel
import javax.inject.Inject

class ViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var repository: Repository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        App.component.inject(this)

        return when {
            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                Log.i("viewmodelfactory", "productsvm instantiated")
                ProductsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProductsDetailsViewModel::class.java) -> {
                Log.i("viewmodelfactory", "productsdetailsvm instantiated")
                ProductsDetailsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SharedViewModel::class.java) -> {
                Log.i("viewmodelfactory", "sharedviewmodel instantiated")
                SharedViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}