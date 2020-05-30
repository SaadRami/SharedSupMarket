package be.supinfo.supermarketapp.ui


import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import be.supinfo.supermarketapp.R


class BaseActivity : AppCompatActivity() {

    //    ActionBar mActionBar;

    private lateinit var mActionBar: ActionBar

    fun activateToolBar(isHome: Boolean) {
        mActionBar = supportActionBar!!
        if (mActionBar == null) {
            val toolBar: Toolbar = findViewById(R.id.toolbar);
            if (toolBar != null) {
                setSupportActionBar(toolBar)
                mActionBar = supportActionBar!!
            }
        }
//        if (mActionBar != null) {
//            if (isMain) {
//                if (isRoot) {
//                    mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//                } else {
//                    mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
//                }
//            } else {
//                mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
//            }
//            mActionBar.setDisplayHomeAsUpEnabled(true);
//        }
    }

//    fun setActionBarTitle(String title) {
//        if (mActionBar != null) {
//            mActionBar.setTitle(title);
//        }
//    }
}
