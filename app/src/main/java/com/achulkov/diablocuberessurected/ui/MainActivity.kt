package com.achulkov.diablocuberessurected.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.ui.onboarding.OnboardingActivity
import com.achulkov.diablocuberessurected.ui.onboarding.OnboardingActivity.Companion.KEY_SHOW_WELCOME

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this)



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //todo
        if (sharedPreferences.getBoolean(KEY_SHOW_WELCOME, true)) {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
//temp
        startActivity(Intent(this, OnboardingActivity::class.java))
    }
}