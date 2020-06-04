package be.supinfo.supermarketapp.ui.details

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.MainActivity
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
    //private lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        (requireActivity() as AppCompatActivity).run {
//            supportActionBar?.show()
//        }
        setUpDetailsFragment()

        App.component.inject(this)

//        sharedViewModel =
//            ViewModelProvider(requireActivity(), viewModelFactory)
//                .get(SharedViewModel::class.java)

        productsDetailsViewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            ).get(ProductsDetailsViewModel::class.java)

        productsDetailsViewModel.selectedProduct.observe(viewLifecycleOwner, Observer {
            (requireActivity() as MainActivity).updateTitle(it.title)
        })

        requireActivity().title = productsDetailsViewModel.selectedProduct.value?.title

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        val binding = ProductsDetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = productsDetailsViewModel

        return binding.root
    }

    private fun setUpDetailsFragment() {
        (requireActivity() as MainActivity).run {
            activateToolBar(false)
            //showToolbar()
            manageFabVisibility(false)
            setDrawerEnabled(false)
        }
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(TAG_DETAIL_FRAGMENT, "$item.itemId")
        when (item.itemId) {
            android.R.id.home -> navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }
}