package be.supinfo.supermarketapp.ui.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.BaseFragment
import be.supinfo.supermarketapp.MainActivity
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.data.remote.Product
import be.supinfo.supermarketapp.ui.cart.CartViewModel
import be.supinfo.supermarketapp.ui.main.DIALOG_ID_CONFIRMATION
import be.supinfo.supermarketapp.ui.main.DIALOG_ID_DISPLAY_PRODUCT
import be.supinfo.supermarketapp.ui.main.MainViewModel
import be.supinfo.supermarketapp.ui.main.ProductsRecyclerViewAdapter
import be.supinfo.supermarketapp.ui.shared.SharedViewModel
import be.supinfo.supermarketapp.util.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class ProductsFragment : BaseFragment(), ProductsRecyclerViewAdapter.ProductListener,
    AppDialog.DialogEvents {
    private lateinit var navController: NavController
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var recyclerViewProductsByRayon: RecyclerView
    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.component.inject(this)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        setUpFragment("", false, true, false, true)
        val view = inflater.inflate(R.layout.fragment_products, container, false)
        recyclerViewProductsByRayon = view.findViewById(R.id.rcProducts_by_rayon)


        sharedViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel::class.java)
        cartViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(CartViewModel::class.java)

        mainViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(MainViewModel::class.java)

        productsViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(ProductsViewModel::class.java)

        productsViewModel.selectedRayon.observe(viewLifecycleOwner, Observer {
            (requireActivity() as MainActivity).updateTitle(it.title)
        })

        productsViewModel.productsByRayon.observe(viewLifecycleOwner, Observer {
            for (product in it) {
                Log.i(TAG_PRODUCTS_FRAGMENT, product.title)
            }
            recyclerViewProductsByRayon.adapter =
                ProductsRecyclerViewAdapter(requireContext(), it, this, false)
        })

        (requireActivity() as MainActivity).run {
            fab.setOnClickListener { navController.navigate(R.id.action_products_to_cart) }
        }

        return view
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onProductItemClick(product: Product) {
        mainViewModel.selectProduct(product)

        val args = Bundle().apply {
            putInt(DIALOG_ID, DIALOG_ID_DISPLAY_PRODUCT)
            putString(DIALOG_MESSAGE, "")
        }
        val dialog = AppDialog()
        dialog.arguments = args

        dialog.show(childFragmentManager, null)
    }

    override fun onPositiveResult(idDialog: Int, args: Bundle) {
        if (idDialog == 1 || idDialog == 2) {
            var product: Product = mainViewModel.selectedProduct.value!!
            cartViewModel.addProductInCart(product)
            sharedViewModel.incrementFabCount(1)
            Snackbar.make(
                requireView(),
                getString(R.string.item_added_to_cart),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onNegativeResult(idDialog: Int, args: Bundle) {

    }

    override fun onDialogCanceled(idDialog: Int) {
    }

    override fun onAddToCartClick(product: Product) {
        mainViewModel.selectProduct(product)
        val args = Bundle().apply {
            putInt(DIALOG_ID, DIALOG_ID_CONFIRMATION)
            putString(DIALOG_MESSAGE, getString(R.string.checkAddToCart))
        }
        val dialog = AppDialog()
        dialog.arguments = args
        dialog.show(childFragmentManager, null)
    }

    override fun onRemoveProductInCart(product: Product) {
    }

    override fun onUpdateFabAndTotal(quantity: Int, updateFlag: String, price: Double) {
        TODO("Not yet implemented")
    }
}