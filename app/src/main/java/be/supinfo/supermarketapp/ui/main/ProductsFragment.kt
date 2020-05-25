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

//        (requireActivity() as AppCompatActivity).run {
//            supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        }
        setHasOptionsMenu(true)

        // SUBSCRIBE TO CHANGES IN VM DATA
        // To Subscrie to changes in a ViewModel's data
        // from the datalive object, call the observe function, this function takes a lifecycle owner (this) + an instance of an observer
        // class that has a single function
        // it : is the value published from the viewModel
        val view = inflater.inflate(R.layout.products_fragment, container, false)
        recyclerView = view.findViewById(R.id.rvProductList)

        swipeRefresh = view.findViewById(R.id.srRefreshProducts)

        swipeRefresh.setOnRefreshListener { viewModel.refreshProducts() }

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)


        // Creating and attaching a viewModel to an Activity
        // ViewModelProvider is responsible of instatiating the viewmodel an return its reference
        viewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)
        viewModel.products.observe(viewLifecycleOwner, Observer {
//            for (product in it) {
//                Log.i(VIEWMMODEL_TAG, "${product.productTitle}  (\$${product.price})")
//            }

            val adapter = ProductsRecyclerViewAdapter(
                requireContext(),
                it, this
            )
            recyclerView.adapter = adapter
            swipeRefresh.isRefreshing = false
//            val sb = StringBuilder()
//            for (product in it) {
//                sb.append("${product.title}  (\$${product.price})")
//                    .append("\n")
//            }
            //tvProducts.text = sb
        })


        // Inflate the layout for this fragment
        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
////        view.findViewById<Button>(R.id.button_first).setOnClickListener {
////            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
////        }
//
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//    }

    override fun onItemClick(product: Product) {
        Log.i(FRAGMENT_TAG, product.title)
        navController.navigate(R.id.action_go_to_details)
        viewModel.selectedProduct.value = product
    }


}