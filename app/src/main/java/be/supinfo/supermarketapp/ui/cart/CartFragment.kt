package be.supinfo.supermarketapp.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.BaseFragment
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.data.remote.Product
import be.supinfo.supermarketapp.ui.main.ADD
import be.supinfo.supermarketapp.ui.main.DIALOG_ID_CONFIRMATION
import be.supinfo.supermarketapp.ui.main.ProductsRecyclerViewAdapter
import be.supinfo.supermarketapp.ui.main.REMOVE
import be.supinfo.supermarketapp.ui.shared.SharedViewModel
import be.supinfo.supermarketapp.util.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cart.*
import javax.inject.Inject

class CartFragment : BaseFragment(), AppDialog.DialogEvents,
    ProductsRecyclerViewAdapter.ProductListener {
    private lateinit var navController: NavController
    private lateinit var cartViewModel: CartViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerViewProductsInCart: RecyclerView
    private lateinit var cart_parent_view: ConstraintLayout

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.component.inject(this)
        setUpFragment(getString(R.string.panier), false, false, false, true)

        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        recyclerViewProductsInCart = view.findViewById(R.id.rvProductsInCart)
        cart_parent_view = view.findViewById(R.id.cart_parent_view)


        sharedViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel::class.java)

        cartViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(CartViewModel::class.java)

//        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
//        view.findViewById<Button>(R.id.buyNo).setOnClickListener {
//            navController.navigateUp()
//        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner, Observer {
//            tvTotal_cart_value.text = String.format("%.2f€", it)
            tvTotal_cart_value.text = getString(R.string.price_format, it)
        })

        cartViewModel.productsInCartMLD.observe(viewLifecycleOwner, Observer {
            Log.i(LOG_CARTFRAGMENT, "${it.size}")
            recyclerViewProductsInCart.adapter =
                ProductsRecyclerViewAdapter(requireContext(), it, this, true)
        })

        return view
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_check -> {
                val args = Bundle().apply {
                    putInt(DIALOG_ID, DIALOG_ID_CONFIRMATION)
                    putString(DIALOG_MESSAGE, getString(R.string.confirm_buy))
                }
                val dialog = AppDialog()
                dialog.arguments = args
                dialog.show(childFragmentManager, null)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        menu.findItem(R.id.menu_check).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    override fun onPositiveResult(idDialog: Int, args: Bundle) {
        if (idDialog == 2) {
            cartViewModel.performTransaction()
        }
    }

    override fun onNegativeResult(idDialog: Int, args: Bundle) {

    }

    override fun onDialogCanceled(idDialog: Int) {
    }


    override fun onRemoveProductInCart(product: Product) {
        cartViewModel.removeProductInCart(product)
        sharedViewModel.decrementFabCount(product.quantityInCart)
        Snackbar.make(
            requireActivity().findViewById(R.id.cl_parent_view_main),
            getString(R.string.item_removed_from_cart),
            Snackbar.LENGTH_SHORT
        ).show()
        Log.i("test", "${sharedViewModel.countML.value}")

//        Snackbar.make(cart_parent_view, "Your message", Snackbar.LENGTH_LONG).apply {view.layoutParams = (view.layoutParams as CoordinatorLayout.LayoutParams).apply {setMargins(leftMargin, topMargin, rightMargin, 200)}}.show()
    }

    override fun onUpdateFabAndTotal(quantity: Int, updateFlag: String, price: Double) {
        when (updateFlag) {
            ADD -> {
                sharedViewModel.incrementFabCount(quantity)
                cartViewModel.incrementTotal(price)
            }
            REMOVE -> {
                sharedViewModel.decrementFabCount(quantity)
                cartViewModel.decrementTotal(price)
            }
        }
    }


    override fun onProductItemClick(product: Product) {
    }

    override fun onAddToCartClick(product: Product) {
    }
}