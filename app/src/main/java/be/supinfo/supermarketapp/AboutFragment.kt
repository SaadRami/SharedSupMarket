package be.supinfo.supermarketapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController

class AboutFragment : BaseFragment() {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setUpFragment(getString(R.string.about), false, false, false, true)
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}