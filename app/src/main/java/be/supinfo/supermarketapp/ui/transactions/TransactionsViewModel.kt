package be.supinfo.supermarketapp.ui.transactions

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.data.Repository
import javax.inject.Inject

class TransactionsViewModel(private val repository: Repository) :
    ViewModel() {

    val transactions = repository.transactionsWithProductsAndCartProductsLiveData

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    init {
        App.component.inject(this)
    }

    fun getTransactionsWithProductsAndProductsInCart() {
        repository.getAllTransactionsWithProductsInCart()
    }

}