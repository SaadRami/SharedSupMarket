package be.supinfo.supermarketapp.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.util.VIEWMMODEL_TAG

class MyViewModel(app: Application) : AndroidViewModel(app) {

    // à injecter au moment du run time ultérieurement
    private val dataRepo: Repository = Repository(app)
    val products = dataRepo.products

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
        // By setting a value we're publishing them
    }

    fun displayData() {
        prenom.value = "tzzefzefzefzefzefzefest"
    }

    fun callWebservice(){
        dataRepo.performCall()
    }


}