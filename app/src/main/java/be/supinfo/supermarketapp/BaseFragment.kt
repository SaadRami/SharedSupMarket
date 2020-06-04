package be.supinfo.supermarketapp

import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import be.supinfo.supermarketapp.util.TAG_DETAIL_FRAGMENT

open class BaseFragment : Fragment() {
    private lateinit var navController: NavController

    fun setUpFragment(
        title: String,
        isHome: Boolean,
        hideFab: Boolean,
        isDrawerEnabled: Boolean,
        toolbarFlag: Boolean
    ) {
        (requireActivity() as MainActivity).run {
            activateToolBar(isHome)
            updateTitle(title)
            manageFabVisibility(hideFab)
            setDrawerEnabled(isDrawerEnabled)
            manageToolbarVisilibty(toolbarFlag)
        }
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(TAG_DETAIL_FRAGMENT, "$item.itemId")
        when (item.itemId) {
            android.R.id.home -> navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onPrepareOptionsMenu(menu: Menu) {
//        menu.findItem(R.id.menu_settings).isVisible = false
//        super.onPrepareOptionsMenu(menu)
//    }
}