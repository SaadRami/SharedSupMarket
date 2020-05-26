package be.supinfo.supermarketapp.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import be.supinfo.supermarketapp.MainActivity
import be.supinfo.supermarketapp.R
import com.google.android.material.snackbar.Snackbar

const val splashTimeOut: Long = 3000L
const val REQUEST_PERMISSION_CODE = 13

class SplashActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            performSplash()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_PERMISSION_CODE
            )
        }

        setContentView(R.layout.activity_splash)
    }

    @SuppressLint("ResourceType")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                performSplash()
            } else {
                val rootView = findViewById<ConstraintLayout>(R.id.rootView)
                Snackbar.make(rootView, "Permission denied", Snackbar.LENGTH_LONG).show()
                //  finish()
            }
        }
    }

    private fun performSplash() {
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            startActivity(Intent(this, MainActivity::class.java))
            // close this activity
            finish()
        }, splashTimeOut)
    }

}