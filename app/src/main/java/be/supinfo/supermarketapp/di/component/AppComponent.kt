package be.supinfo.supermarketapp.di.component

import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.di.modules.ApplicationModule
import be.supinfo.supermarketapp.di.modules.RepositoryModule
import be.supinfo.supermarketapp.di.modules.ViewModelFactoryModule
import be.supinfo.supermarketapp.ui.details.DetailsFragment
import be.supinfo.supermarketapp.ui.main.ProductsFragment
import be.supinfo.supermarketapp.ui.main.ProductsViewModel
import be.supinfo.supermarketapp.ui.shared.SharedViewModel
import be.supinfo.supermarketapp.util.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class, ViewModelFactoryModule::class])
interface AppComponent {
    fun inject(repository: Repository)
    fun inject(viewModelFactory: ViewModelFactory)
    fun inject(productsViewModel: ProductsViewModel)
    fun inject(productDetailsFragment: DetailsFragment)
    fun inject(productsFragment: ProductsFragment)
    fun inject(sharedViewMode: SharedViewModel)
}