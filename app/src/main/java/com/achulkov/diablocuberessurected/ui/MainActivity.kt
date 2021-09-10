package com.achulkov.diablocuberessurected.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.databinding.ActivityMainBinding
import com.achulkov.diablocuberessurected.ui.onboarding.OnboardingActivity
import com.achulkov.diablocuberessurected.ui.onboarding.OnboardingActivity.Companion.KEY_SHOW_WELCOME

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (sharedPreferences.getBoolean(KEY_SHOW_WELCOME, true)) {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_host) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)


    }
}