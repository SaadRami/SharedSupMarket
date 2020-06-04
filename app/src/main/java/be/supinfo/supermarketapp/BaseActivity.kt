package be.supinfo.supermarketapp


import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import be.supinfo.supermarketapp.ui.shared.SharedViewModel
import be.supinfo.supermarketapp.util.ViewModelFactory
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {
    //    ActionBar mActionBar
    private var mActionBar: ActionBar? = null
    lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)

        sharedViewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(SharedViewModel::class.java)

        sharedViewModel.appTitle.observe(this, Observer { mActionBar!!.title = it })
        super.onCreate(savedInstanceState)
    }

    private val toolBar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    fun activateToolBar(isHome: Boolean) {
        if (mActionBar == null) {
            if (toolBar != null) {
                setSupportActionBar(toolBar)
                mActionBar = supportActionBar
                (mActionBar as ActionBar).setHomeAsUpIndicator(R.drawable.ic_menu)
            }
        }
        if (isHome) {
            (mActionBar as ActionBar).setHomeAsUpIndicator(R.drawable.ic_menu)
        } else {
            (mActionBar as ActionBar).setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        }

        (mActionBar as ActionBar).setDisplayHomeAsUpEnabled(true)
    }


    fun manageToolbarVisilibty(flag: Boolean) {
        if (supportActionBar == null) {
            setSupportActionBar(toolBar)
        }
        if (!flag) {
            supportActionBar!!.hide()
        } else {
            supportActionBar!!.show()
        }

    }

    fun updateTitle(title: String) {
        sharedViewModel.updateTitle(title)
    }

}