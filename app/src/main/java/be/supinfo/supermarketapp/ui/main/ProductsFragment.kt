package be.supinfo.supermarketapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.data.Product
import be.supinfo.supermarketapp.util.FRAGMENT_TAG

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProductsFragment : Fragment(), ProductsRecyclerViewAdapter.ProductListener {
    private lateinit var viewModel: ProductsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.products_fragment, container, false)
        recyclerView = view.findViewById(R.id.rvProductList)

        swipeRefresh = view.findViewById(R.id.srRefreshProducts)

        swipeRefresh.setOnRefreshListener { viewModel.refreshProducts() }

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        viewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)
        viewModel.products.observe(viewLifecycleOwner, Observer {

            val adapter = ProductsRecyclerViewAdapter(
                requireContext(),
                it, this
            )
            recyclerView.adapter = adapter
            swipeRefresh.isRefreshing = false
        })

        // Inflate the layout for this fragment
        return view
    }


    override fun onItemClick(product: Product) {
        Log.i(FRAGMENT_TAG, product.title)
        navController.navigate(R.id.action_go_to_details)
        viewModel.selectedProduct.value = product
    }

}