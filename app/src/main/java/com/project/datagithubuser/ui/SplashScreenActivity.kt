package com.project.datagithubuser.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.project.datagithubuser.databinding.ActivitySplashScreenBinding
import com.project.datagithubuser.helper.Helper
import com.project.datagithubuser.helper.Helper.dataStore
import com.project.datagithubuser.ui.homepage.MainActivity
import com.project.datagithubuser.ui.settings.SettingPreferences
import com.project.datagithubuser.ui.settings.SettingViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isDarkMode()
        binding.ivSplash.apply {
            alpha = Helper.START_ALPHA_SPLASH
            animate().setDuration(Helper.DURATION_SPLASH).alpha(Helper.END_ALPHA_SPLASH)
                .withEndAction {
                    val move = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(move)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
        }
    }

    private fun isDarkMode() {
        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(
            this,
            ViewModelFactorySetting(pref)
        )[SettingViewModel::class.java]
        viewModel.getThemeSettings().observe(
            this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}
