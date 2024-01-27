package com.example.dictionmaster

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.dictionmaster.di.appModule
import com.example.dictionmaster.ui.MainActivity
import com.example.dictionmaster.ui.viewmodels.DefinitionViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel by inject<DefinitionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startKoin {
            androidContext(this@SplashActivity)
            modules(appModule)
        }
        cleanCacheDatabase()
    }

    private fun cleanCacheDatabase() {
        viewModel.cleanCacheDatabase()
        viewModel.removeSplashScreen.observe(this) {
            removeSplashScreen()
        }
    }

    private fun removeSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }, 500)
    }
}