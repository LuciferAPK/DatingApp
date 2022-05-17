package com.example.csplusapp.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.csplusapp.main.MainActivity
import com.example.csplusapp.R
import com.example.csplusapp.databinding.ActivitySplashBinding
import com.example.csplusapp.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val user by lazy {
        FirebaseAuth.getInstance().currentUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        animation()
        timeDelay()
    }

    private fun animation() {
        binding.logo.animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        binding.textLogo.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        binding.textBio.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
    }

    private fun timeDelay() {
        Handler().postDelayed({
            nextActivity()
        }, 2000)
    }

    private fun nextActivity() {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}