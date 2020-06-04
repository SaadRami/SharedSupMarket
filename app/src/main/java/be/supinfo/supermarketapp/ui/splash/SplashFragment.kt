package be.supinfo.supermarketapp.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import be.supinfo.supermarketapp.BaseFragment
import be.supinfo.supermarketapp.R
import com.google.android.material.snackbar.Snackbar

const val SPLASH_TIME_OUT = 5000L
const val REQUEST_PERMISSION_CODE = 13

class SplashFragment : BaseFragment() {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpFragment("", false, false, false, false)
        checkPermissions()
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            goToMain()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_PERMISSION_CODE
            )
        }
    }

    @SuppressLint("ResourceType")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goToMain()
            } else {
                val rootView = requireView().findViewById<ConstraintLayout>(R.id.root_fragment_view)
                Snackbar.make(rootView, "Permission denied", Snackbar.LENGTH_LONG).show()
                //  finish()
            }
        }
    }


    private fun goToMain() {
        Handler().postDelayed({
            navController.navigate(R.id.action_splash_to_login)
        }, SPLASH_TIME_OUT)
    }

}