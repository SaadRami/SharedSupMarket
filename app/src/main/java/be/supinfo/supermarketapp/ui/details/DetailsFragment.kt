package be.supinfo.supermarketapp.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.databinding.ProductsDetailsFragmentBinding
import be.supinfo.supermarketapp.util.TAG_DETAIL_FRAGMENT
import be.supinfo.supermarketapp.util.ViewModelFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var productsDetailsViewModel: ProductsDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        (requireActivity() as AppCompatActivity).run {
//            supportActionBar?.show()
//        }

        App.component.inject(this)

        productsDetailsViewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(ProductsDetailsViewModel::class.java)

        productsDetailsViewModel.selectedProduct.observe(viewLifecycleOwner, Observer {
            Log.i(TAG_DETAIL_FRAGMENT, it.description) /* <- HERE IS THE PROBLEM, THIS CODE BLOCK DOESNT RUN, THE OBSERVER DONT GET DATA FROM OBSERVABLE (MUTABLELIVEDATA) */
        })

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        val binding = ProductsDetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = productsDetailsViewModel

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(TAG_DETAIL_FRAGMENT, "$item.itemId")
        if (item.itemId == android.R.id.home) {
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }
}