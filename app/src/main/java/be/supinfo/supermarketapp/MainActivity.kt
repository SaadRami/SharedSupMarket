package be.supinfo.supermarketapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import be.supinfo.supermarketapp.util.MyHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    // drawer layout will have to be accessible from all functions, its gonna be declared as property (private)
    private val drawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawerLayout)
    }


    private lateinit var sb: StringBuilder


    // private var myArray = arrayOf(1, 2, 3, 4)

    // Lazy instantiation
    // We're only going to initialize the array when we reference it in our code
    // private val myViews by lazy { arrayOf<View>(fab, toolbar) }
    // private val myViews by lazy { arrayOf<View>(findViewById(R.id.fab), findViewById(R.id.toolbar) }


    private lateinit var fab: FloatingActionButton

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_nav)
        setSupportActionBar(toolbar)


//        prenom_value = savedInstanceState?.getString(ET_PRENOM) ?: ""
//        et_prenom.setText(prenom_value)


        //viewModel.prenom.observe(this, Observer { tvTest.text = it })

//        for (i in 0 until myArray.size) {
//            Log.i(LOG_TAG_INDICES, "$i")
//        }

        //  text = savedInstanceState.getString(EDITTEXT_VALUE)

//        fab.setOnClickListener {
//            handleClickFabEvent(it)
//        }


//        for (myView in myViews) {
//            println(myView.isVisible)
//        }

        //viewModel.displayData()

        val navView = findViewById<NavigationView>(R.id.nav_view)
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        navView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_nav_drawer,
            R.string.close_nav_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        fab = findViewById(R.id.fab)
        //fab.setOnClickListener { viewModel.displayData() }

//        buttonIntent.setOnClickListener {
//            goToAnotherActivity()
//        }

//        lifecycle.addObserver(MyLifeCycleObserver(LOG_TAG_MAIN_ACTIVITY_DETAILS))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
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
        if (item.itemId == R.id.about) {
            AboutActivity.start(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main2, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun handleClickFabEvent(it: View) {
//        for(i in 0 until collection.size){
//
//        }
        MyHelper.blFunction()
        Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun onStart() {
        super.onStart()
    }


//    override fun onSaveInstanceState(outState: Bundle) {
//        // Save states in case of orientation changing
//        outState.putString(ET_PRENOM, et_prenom.text.toString())
//        super.onSaveInstanceState(outState)
//    }

}