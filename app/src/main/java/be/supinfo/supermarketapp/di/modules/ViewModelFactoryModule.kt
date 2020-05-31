package be.supinfo.supermarketapp.di.modules

import be.supinfo.supermarketapp.util.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelFactoryModule {
    @Singleton
    @Provides
    fun providesViewModelFactory(): ViewModelFactory = ViewModelFactory()
}