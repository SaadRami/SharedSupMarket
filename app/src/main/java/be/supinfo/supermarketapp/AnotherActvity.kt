package be.supinfo.supermarketapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class AnotherActvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another_actvity)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.second_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> sendData()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendData(): Boolean {
        val implicitIntent = Intent().apply {
            action = Intent.ACTION_SEND
//            putExtra(
//                Intent.EXTRA_TEXT,
//                "I check rayons: ${viewModel.headline.value}"
//            )
            putExtra(
                Intent.EXTRA_TEXT,
                "Test"
            )
            type = "text/plain"
        }
        startActivity(implicitIntent)
        return true
    }
}