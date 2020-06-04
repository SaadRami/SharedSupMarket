package be.supinfo.supermarketapp.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import be.supinfo.supermarketapp.BaseFragment
import be.supinfo.supermarketapp.R

private lateinit var navController: NavController

class RegisterFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpFragment(getString(R.string.register), false, false, false, true)
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}