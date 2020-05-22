package be.supinfo.supermarketapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import be.supinfo.supermarketapp.VIEWMMODEL_TAG
import be.supinfo.supermarketapp.util.MyHelper

class MyViewModel(app: Application) : AndroidViewModel(app) {

//    Always save the application reference. This is being passed in as the viewModel object is being created,
//    but it won't persist for the lifetime of the viewModel, and so I'll handle that by creating a new variable here
//    called context, and I'll reference it from the app object, and now I'll be able to use the context to get resources
//    or do other things that are part of the Android structure.

    val prenom = MutableLiveData<String>()
    private val context = app

    // This is where we're going to store our data
    // Initialisation des elements présents dans l'UI
    init {
        Log.i(VIEWMMODEL_TAG, "viewmodel")
        // prenom refers to the live data object, to save its value use .value
        prenom.value = ""
        // By setting a value we're publishing them
    }

    fun displayData() {
        prenom.value = MyHelper.blFunction()
    }
}