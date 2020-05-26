package be.supinfo.supermarketapp.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.databinding.DetailsFragmentBinding
import be.supinfo.supermarketapp.ui.main.ProductsViewModel
import be.supinfo.supermarketapp.util.TAG_DETAIL_FRAGMENT

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        (requireActivity() as AppCompatActivity).run {
//            supportActionBar?.show()
//        }

        viewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)
        viewModel.selectedProduct.observe(requireActivity(), Observer {
            Log.i(TAG_DETAIL_FRAGMENT, it.description)
        })

        Log.i(TAG_DETAIL_FRAGMENT, "test")

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        val binding = DetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

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