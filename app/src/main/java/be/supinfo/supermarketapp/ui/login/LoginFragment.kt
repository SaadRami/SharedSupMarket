package be.supinfo.supermarketapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import be.supinfo.supermarketapp.BaseFragment
import be.supinfo.supermarketapp.R

class LoginFragment : BaseFragment() {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpFragment(getString(R.string.login), false, false, false, false)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)

        view.findViewById<Button>(R.id.btn_login)
            .setOnClickListener { navController.navigate(R.id.action_login_to_main) }
        view.findViewById<TextView>(R.id.tv_register)
            .setOnClickListener { navController.navigate(R.id.action_login_to_register) }

        return view
    }


}