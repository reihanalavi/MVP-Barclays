package com.reihanalavi.mvpbarclays.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.reihanalavi.mvpbarclays.R
import com.reihanalavi.mvpbarclays.fragments.TeamsListFragment
import kotlinx.android.synthetic.main.activity_bottom_navigation.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger

class MainActivity : AppCompatActivity(), AnkoLogger {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()

    }

    private fun setupViews() {
        val navHost = supportFragmentManager
            .findFragmentById(R.id.fragment_navHost) as NavHostFragment
        navController = navHost.navController
        NavigationUI.setupWithNavController(bottomNav_main, navController)
    }
}
