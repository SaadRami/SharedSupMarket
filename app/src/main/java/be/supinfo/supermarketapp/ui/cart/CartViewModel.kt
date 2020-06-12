package be.supinfo.supermarketapp.ui.cart

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.data.remote.Product
import javax.inject.Inject

class CartViewModel(private val repository: Repository) :
    ViewModel() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val totalPrice = MutableLiveData<Double>()
    val productsInCartMLD = MutableLiveData<ArrayList<Product>>()
    private var productsInCart = ArrayList<Product>()
    private var ttl: Double = 0.0


    init {
        App.component.inject(this)
    }

//
//    public int findContact(String name) {
//        for (Contact contact : contactList) {
//            if (contact.getName().equals(name)) {
//                return contactList.indexOf(contact);
//            }
//        }
//        return -1;
//    }

    private fun findContactIndex(productTitle: String): Int {
        for (product in productsInCart) {
            if (product.title == productTitle) {
                return productsInCart.indexOf(product)
            }
        }
        return -1
    }


    fun addProductInCart(product: Product) {
        val productIndex: Int = findContactIndex(product.title)
        if (productIndex != -1) {
            productsInCart[productIndex].quantityInCart++
        } else {
            product.quantityInCart = 1
            productsInCart.add(product)
        }
        //  productsInCart.add(product)
        productsInCartMLD.value = productsInCart
        incrementTotal(product.price)
    }

    fun removeProductInCart(product: Product) {
        val foundIndex = findContactIndex(product.title)
        if (foundIndex >= 0) {
            val productFound = productsInCart[foundIndex]
            productsInCart.remove(productFound)
        }
        productsInCartMLD.value = productsInCart
        decrementTotal(product.price * product.quantityInCart)
        Log.i("ezr", totalPrice.value.toString())
    }

    fun incrementTotal(price: Double) {
        ttl += price
        totalPrice.value = ttl
    }

    fun decrementTotal(price: Double) {
        ttl -= price
        totalPrice.value = ttl
    }

    fun performTransaction() {
        repository.addTransaction(totalPrice.value, productsInCartMLD.value)
    }
}