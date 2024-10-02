package com.estellore.menu

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the action bar title and subtitle
        supportActionBar?.title = "Abigail Dave Estellore"
        supportActionBar?.subtitle = "Eldroid"
    }

    // Inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    // Handle item selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.searchmenu -> {
                // Open a fragment when the search icon is clicked
                openFragment(MyFragment())
                true
            }
            R.id.fldr_menu -> {
                // Show a dialog fragment when the folder icon is clicked
                showDialogFragment()
                true
            }
            R.id.sub_exit -> {
                // Exit the app when "Exit App" is selected from the overflow menu
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Public method to open a fragment
    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    // Method to show a dialog fragment
    fun showDialogFragment() {
        val dialogFragment = MyDialogFragment()
        dialogFragment.show(supportFragmentManager, "MyDialog")
    }
}
