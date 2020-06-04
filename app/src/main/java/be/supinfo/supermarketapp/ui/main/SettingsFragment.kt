package be.supinfo.supermarketapp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.PreferenceFragmentCompat
import be.supinfo.supermarketapp.MainActivity
import be.supinfo.supermarketapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    lateinit var navController: NavController

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        setUpSettingsFragment()
    }

    private fun setUpSettingsFragment() {
        (requireActivity() as MainActivity).run {
            activateToolBar(false)
            updateTitle(getString(R.string.Settings))
            manageFabVisibility(false)
            setDrawerEnabled(false)
        }
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.setGroupVisible(R.id.about_settings, false)
        super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }
}