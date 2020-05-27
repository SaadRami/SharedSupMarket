package be.supinfo.supermarketapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.data.Product
import be.supinfo.supermarketapp.util.MyHelper
import be.supinfo.supermarketapp.util.TAG_FRAGMENT

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

const val DISPLAY_LIST = "list"
const val DISPLAY_GRID = "grid"


class ProductsFragment : Fragment(), ProductsRecyclerViewAdapter.ProductListener {
    private lateinit var viewModel: ProductsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var navController: NavController
    private lateinit var adapter: ProductsRecyclerViewAdapter
    private lateinit var displayMode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)


        val view = inflater.inflate(R.layout.products_fragment, container, false)

        recyclerView = view.findViewById(R.id.rvProductList)

        displayMode = MyHelper.getDisplayMode(requireContext())



        recyclerView.layoutManager = when (displayMode) {
            DISPLAY_LIST -> {
                LinearLayoutManager(requireContext())
            }
            DISPLAY_GRID -> {
                GridLayoutManager(requireContext(), 2)
            }
            else -> LinearLayoutManager(requireContext())
        }

        swipeRefresh = view.findViewById(R.id.srRefreshProducts)

        swipeRefresh.setOnRefreshListener { viewModel.refreshProducts() }

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        viewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)
        viewModel.products.observe(viewLifecycleOwner, Observer {
            adapter = ProductsRecyclerViewAdapter(
                requireContext(),
                it, this
            )
            recyclerView.adapter = adapter
            swipeRefresh.isRefreshing = false
        })

        // Inflate the layout for this fragment
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.about).isVisible = false
        inflater.inflate(R.menu.list_grid_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_grid_item -> {
                MyHelper.setDisplayMode(requireContext(), DISPLAY_GRID)
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
                recyclerView.adapter = adapter
            }
            R.id.menu_list_item -> {
                MyHelper.setDisplayMode(requireContext(), DISPLAY_LIST)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(product: Product) {
        Log.i(TAG_FRAGMENT, product.title)
        navController.navigate(R.id.action_go_to_details)
        viewModel.selectedProduct.value = product
    }

}