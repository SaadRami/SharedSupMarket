package be.supinfo.supermarketapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.BaseFragment
import be.supinfo.supermarketapp.MainActivity
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.data.remote.Product
import be.supinfo.supermarketapp.data.remote.Rayon
import be.supinfo.supermarketapp.ui.cart.CartViewModel
import be.supinfo.supermarketapp.ui.details.ProductsDetailsViewModel
import be.supinfo.supermarketapp.ui.products.ProductsViewModel
import be.supinfo.supermarketapp.ui.shared.SharedViewModel
import be.supinfo.supermarketapp.util.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
const val DISPLAY_LIST = "list"
const val DISPLAY_GRID = "grid"
const val DIALOG_ID_DISPLAY_PRODUCT = 1
const val DIALOG_ID_CONFIRMATION = 2

class MainFragment : BaseFragment(), RayonsRecyclerViewAdapter.RayonListener,
    ProductsRecyclerViewAdapter.ProductListener, AppDialog.DialogEvents {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var productsDetailsViewModel: ProductsDetailsViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerViewRayons: RecyclerView
    private lateinit var recyclerViewRecommended: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var navController: NavController

    private lateinit var recommendedAdapter: ProductsRecyclerViewAdapter
    private lateinit var rayonAdapter: RayonsRecyclerViewAdapter
    private lateinit var displayMode: String
    private lateinit var clparent: CoordinatorLayout


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        App.component.inject(this)


        setUpFragment(getString(R.string.Accueil), true, true, true, true)
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        clparent = requireActivity().findViewById(R.id.cl_parent_view_main)

        drawerLayout = requireActivity().findViewById(R.id.drawerLayout)
        recyclerViewRayons = view.findViewById(R.id.rvProductList)
        recyclerViewRecommended = view.findViewById(R.id.rvRecommendedlist)
        //  displayMode = MyHelper.getDisplayMode(requireContext())
//
//        recyclerView.layoutManager = when (displayMode) {
//            DISPLAY_LIST -> {
//                LinearLayoutManager(requireContext())
//            }
//            DISPLAY_GRID -> {
//                GridLayoutManager(requireContext(), 2)
//            }
//            else -> LinearLayoutManager(requireContext())
//        }

        sharedViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel::class.java)

        cartViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(CartViewModel::class.java)

        productsViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(ProductsViewModel::class.java)

        productsDetailsViewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            ).get(ProductsDetailsViewModel::class.java)

        mainViewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            ).get(MainViewModel::class.java)

        swipeRefresh = view.findViewById(R.id.srRefreshProducts)
        swipeRefresh.setOnRefreshListener { mainViewModel.refreshProducts() }
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        // productsViewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)

        mainViewModel.recommendedProducts.observe(viewLifecycleOwner, Observer {
//            val productListener = object : ProductsRecyclerViewAdapter.ProductListener {
//                override fun onProductItemClick(product: Product) {
//
//                }
//            }
//            for (prd in it) {
//                Log.i("OSCOUR", prd.description)
//            }
            recommendedAdapter =
                ProductsRecyclerViewAdapter(requireContext(), it, this, false)
            recyclerViewRecommended.adapter = recommendedAdapter

        })

        mainViewModel.rayons.observe(viewLifecycleOwner, Observer {
            rayonAdapter = RayonsRecyclerViewAdapter(
                requireContext(),
                it, this
            )
            recyclerViewRayons.adapter = rayonAdapter
            swipeRefresh.isRefreshing = false
            Log.i(TAG_FRAGMENT, "Observer notified")
        })


        Log.i(TAG_FRAGMENT, "${mainViewModel.rayons.hasActiveObservers()}")

//        productsViewModel.appTitle.observe(viewLifecycleOwner, Observer {
//            requireActivity().title = it
//        })

        (requireActivity() as MainActivity).run {
            fab.setOnClickListener { navController.navigate(R.id.action_main_to_cart) }
        }
        // Inflate the layout for this fragment
        return view
    }

//    private fun setUpProductsFragment() {
//        val callback = object : OnBackPressedCallback(
//            true
//        ) {
//            override fun handleOnBackPressed() {
//                requireActivity().finish()
//            }
//        }
//        (requireActivity() as MainActivity).run {
//            setDrawerEnabled(true)
//            activateToolBar(true)
//            showToolbar()
//            manageFabVisibility(true)
//            onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
//            updateTitle(getString(R.string.Accueil))
//        }
//        setHasOptionsMenu(true)
//    }

    override fun onResume() {
//        productsViewModel.updateTitle()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //inflater.inflate(R.menu.list_grid_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_grid_item -> {
                MyHelper.setDisplayMode(requireContext(), DISPLAY_GRID)
                recyclerViewRayons.layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerViewRayons.adapter = rayonAdapter
            }
            R.id.menu_list_item -> {
                MyHelper.setDisplayMode(requireContext(), DISPLAY_LIST)
                recyclerViewRayons.layoutManager = LinearLayoutManager(requireContext())
                recyclerViewRayons.adapter = rayonAdapter
            }
            R.id.menu_settings -> {
                navController.navigate(R.id.action_main_to_settings)
            }
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return true
    }


//    override fun onItemClick(product: Product) {
//        Log.i(TAG_FRAGMENT, product.description)
//        // productsViewModel.selectedProduct.value = product
////        productsDetailsViewModel =
////            ViewModelProviders.of(this, viewModelFactory)
////                .get(ProductsDetailsViewModel::class.java)
//        productsDetailsViewModel.selectProduct(product)
//        navController.navigate(R.id.action_main_to_products)
//    }

    override fun onRayonItemClick(rayon: Rayon) {
        Log.i("zavi", "test")
        productsViewModel.selectRayon(rayon)
        navController.navigate(R.id.action_main_to_products)
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

    override fun onPositiveResult(idDialog: Int, args: Bundle) {
        if (idDialog == 1 || idDialog == 2) {
            var product: Product = mainViewModel.selectedProduct.value!!
            cartViewModel.addProductInCart(product)
            Log.i(TAG_MAIN_FRAGMENT, "${cartViewModel.productsInCartMLD.value?.size}")
            sharedViewModel.incrementFabCount(1)
            Snackbar.make(
                clparent,
                getString(R.string.item_added_to_cart),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }


    override fun onNegativeResult(idDialog: Int, args: Bundle) {
    }

    override fun onDialogCanceled(idDialog: Int) {
    }

    override fun onRemoveProductInCart(product: Product) {
    }

    override fun onUpdateFabAndTotal(quantity: Int, updateFlag: String, price: Double) {
        TODO("Not yet implemented")
    }
}