package be.supinfo.supermarketapp.ui.shared

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.data.Repository
import javax.inject.Inject

class SharedViewModel(private val repository: Repository) :
    ViewModel() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val appTitle = MutableLiveData<String>()
    val countML = MutableLiveData<Int>()
    private var count: Int = 0

    init {
        App.component.inject(this)
    }

    fun updateTitle(title: String) {
        appTitle.value = title
    }

    fun incrementFabCount(qtAdded: Int) {
        count += qtAdded
        countML.value = count
    }

    fun decrementFabCount(qtRemoved: Int) {
        count -= qtRemoved
        countML.value = count
    }

    fun clearFabCount() {
        count = 0
        countML.value = count
    }


}