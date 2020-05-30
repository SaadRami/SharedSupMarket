package be.supinfo.supermarketapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import be.supinfo.supermarketapp.util.LOG_MAINACTIVITY
import be.supinfo.supermarketapp.util.MyHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private val drawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawerLayout)
    }
    private lateinit var sb: StringBuilder
    private lateinit var navController: NavController
    private lateinit var fab: FloatingActionButton
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_nav)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_nav_drawer,
            R.string.close_nav_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        NavigationUI.setupWithNavController(toolbar, navController, drawerLayout)

        fab = findViewById(R.id.fab)

//        lifecycle.addObserver(MyLifeCycleObserver(LOG_TAG_MAIN_ACTIVITY_DETAILS))
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.i(LOG_MAINACTIVITY, "test")
        if (item.itemId == R.id.action_about) {
            drawerLayout.closeDrawer(GravityCompat.START)
            AboutActivity.start(this)
        }
        return true
    }

    private fun goToAnotherActivity() {
        var intent = Intent(this, AnotherActvity::class.java)
        startActivity(intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(LOG_MAINACTIVITY, "test")
        if (item.itemId == R.id.about) {
            AboutActivity.start(this)
            return true
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun handleClickFabEvent(it: View) {
        MyHelper.blFunction()
        Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

}