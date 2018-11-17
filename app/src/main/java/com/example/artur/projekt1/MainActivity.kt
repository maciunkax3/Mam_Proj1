package com.example.artur.projekt1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.content.Intent



class MainActivity : AppCompatActivity() {
    private lateinit var mDrawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.nav_camera -> {
                    val intent = Intent(this, DetectActivity::class.java)
                    // start your next activity
                    startActivity(intent)
                }
                R.id.nav_VR -> {
                    val intent = Intent(this, SimpleVrPanoramaActivity::class.java)
                    // start your next activity
                    startActivity(intent)
                }
            }


            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            true
        }
    }

}
