package com.example.countrynewsdisplayerapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.countrynewsdisplayerapp.R
import com.example.countrynewsdisplayerapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //Take the navigation controlles
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        //Support top toolbar
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.newsListFragment), binding.drawerLayout)

        //Custom toolbar
        binding.mainContent.myToolBar.setupWithNavController(navController, appBarConfiguration)

        //Set navigation drawer
        binding.navView.setupWithNavController(navController)
    }
}