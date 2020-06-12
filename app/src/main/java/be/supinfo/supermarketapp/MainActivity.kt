package be.supinfo.supermarketapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import be.supinfo.supermarketapp.ui.transactions.TransactionsViewModel
import be.supinfo.supermarketapp.util.MyHelper
import be.supinfo.supermarketapp.util.TAG_MAIN_ACTIVITY
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val drawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawerLayout)
    }
    private lateinit var sb: StringBuilder
    private lateinit var navController: NavController
    private lateinit var fab: FloatingActionButton
    private lateinit var navGraph: NavGraph
    private lateinit var transactionsViewModel: TransactionsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_nav)

        App.component.inject(this)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navGraph = navController.navInflater.inflate(R.navigation.nav_graph);
        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
//        val toggle = ActionBarDrawerToggle(
//            this, drawerLayout, toolbar, R.string.open_nav_drawer,
//            R.string.close_nav_drawer
//        )
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//        NavigationUI.setupWithNavController(toolbar, navController, drawerLayout)

        transactionsViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(TransactionsViewModel::class.java)


        fab = findViewById(R.id.fab)
        sharedViewModel.countML.observe(this, Observer {
            fabCount.text = "$it"
        })

//        lifecycle.addObserver(MyLifeCycleObserver(LOG_TAG_MAIN_ACTIVITY_DETAILS))
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.i(TAG_MAIN_ACTIVITY, "test")
        when (item.itemId) {
            R.id.nav_about -> {
                navController.navigate(R.id.action_main_to_about)
            }
            R.id.nav_settings -> {
                navController.navigate(R.id.action_main_to_settings)
            }
            R.id.nav_transactions -> {
                transactionsViewModel.getTransactionsWithProductsAndProductsInCart()
                navController.navigate(R.id.action_main_to_transactions)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun goToAnotherActivity() {
        var intent = Intent(this, AnotherActvity::class.java)
        startActivity(intent)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.about) {
//            AboutActivity.start(this)
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun handleClickFabEvent(it: View) {
        MyHelper.blFunction()
        Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    fun setDrawerEnabled(enabled: Boolean) {
        var lockMode: Int = if (enabled) {
            DrawerLayout.LOCK_MODE_UNLOCKED
        } else {
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        }
        drawerLayout.setDrawerLockMode(lockMode)
//        toggle.setDrawerIndicatorEnabled(enabled);
    }

    fun manageFabVisibility(isHide: Boolean) {
        if (!isHide) {
            fabCount.visibility = View.GONE
            fab.visibility = View.GONE
        } else {
            fabCount.visibility = View.VISIBLE
            fab.visibility = View.VISIBLE
        }
    }


}