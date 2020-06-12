package be.supinfo.supermarketapp.di.component

import be.supinfo.supermarketapp.BaseActivity
import be.supinfo.supermarketapp.MainActivity
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.di.modules.ApplicationModule
import be.supinfo.supermarketapp.di.modules.RepositoryModule
import be.supinfo.supermarketapp.di.modules.ViewModelFactoryModule
import be.supinfo.supermarketapp.ui.cart.CartFragment
import be.supinfo.supermarketapp.ui.cart.CartViewModel
import be.supinfo.supermarketapp.ui.details.DetailsFragment
import be.supinfo.supermarketapp.ui.main.MainFragment
import be.supinfo.supermarketapp.ui.main.MainViewModel
import be.supinfo.supermarketapp.ui.products.ProductsFragment
import be.supinfo.supermarketapp.ui.products.ProductsViewModel
import be.supinfo.supermarketapp.ui.shared.SharedViewModel
import be.supinfo.supermarketapp.ui.transactions.TransactionsFragment
import be.supinfo.supermarketapp.ui.transactions.TransactionsViewModel
import be.supinfo.supermarketapp.util.AppDialog
import be.supinfo.supermarketapp.util.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class, ViewModelFactoryModule::class])
interface AppComponent {
    fun inject(repository: Repository)
    fun inject(viewModelFactory: ViewModelFactory)
    fun inject(mainViewModel: MainViewModel)
    fun inject(productDetailsFragment: DetailsFragment)
    fun inject(mainFragment: MainFragment)
    fun inject(sharedViewMode: SharedViewModel)
    fun inject(baseActivity: BaseActivity)
    fun inject(productsViewModel: ProductsViewModel)
    fun inject(productsFragment: ProductsFragment)
    fun inject(appDialog: AppDialog)
    fun inject(cartViewModel: CartViewModel)
    fun inject(cartFragment: CartFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(transactionsFragment: TransactionsFragment)
    fun inject(transactionsViewModel: TransactionsViewModel)
}