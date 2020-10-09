package com.example.mynavigation.activity

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.mynavigation.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        setSupportActionBar(toolbar)
        setupActionBar()
        setupNavigationMenu()
        setupBottomNavMenu()

        setDestinationChangedListener()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val retValue = super.onCreateOptionsMenu(menu)

        if (navigationView == null) {
            menuInflater.inflate(R.menu.overflow_menu, menu)
            return true
        }
        return retValue

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//      return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
//      return NavigationUI.navigateUp(navController, drawerLayout)
        return navController.navigateUp(appBarConfiguration)
    }

    private fun init() {
        toolbar = findViewById(R.id.toolbar)

        navController = findNavController(R.id.my_nav_host_fragment)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        //The Up button will not be displayed when on these destinations.
        //appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.deepLinkFragment), drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
    }

    private fun setDestinationChangedListener() {
        navController.addOnDestinationChangedListener { controller, destination, _ ->

            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }
            Toast.makeText(
                this@MainActivity, "Navigated to $dest",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("NavigationActivity", "Navigated to $dest")

            // prevent nav gesture if not on start destination
            if (destination.id == controller.graph.startDestination) {
                drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }

        }
    }

    private fun setupBottomNavMenu() {
//        bottomNav?.let { NavigationUI.setupWithNavController(it,navController) }
        bottomNavigationView?.setupWithNavController(navController)
    }

    private fun setupNavigationMenu() {
//        navigationView?.let { NavigationUI.setupWithNavController(it, navController) }
        navigationView?.setupWithNavController(navController)
    }

    private fun setupActionBar() {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}

