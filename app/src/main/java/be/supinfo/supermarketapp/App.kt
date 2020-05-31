package be.supinfo.supermarketapp

import android.app.Application
import be.supinfo.supermarketapp.di.component.AppComponent
import be.supinfo.supermarketapp.di.component.DaggerAppComponent
import be.supinfo.supermarketapp.di.modules.ApplicationModule
import be.supinfo.supermarketapp.di.modules.RepositoryModule
import be.supinfo.supermarketapp.di.modules.ViewModelFactoryModule
import be.supinfo.supermarketapp.util.BASE_URL


class App : Application() {
    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().applicationModule(ApplicationModule(this))
            .repositoryModule(RepositoryModule(BASE_URL))
            .viewModelFactoryModule(ViewModelFactoryModule())
            .build()
    }
}