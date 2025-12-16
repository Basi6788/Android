package com.basitshop

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val logo = findViewById<View>(R.id.img_logo)
        val btnGetStarted = findViewById<View>(R.id.btn_get_started)

        // 1. Logo Animation (Fade In + Scale)
        logo.alpha = 0f
        logo.scaleX = 0.5f
        logo.scaleY = 0.5f
        
        logo.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(1000)
            .withEndAction {
                // 2. Button Animation (Slide Up + Fade In)
                btnGetStarted.translationY = 100f
                btnGetStarted.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(800)
                    .start()
            }
            .start()

        // 3. Button Click -> Navigate to Login
        btnGetStarted.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // Outro Transition (Fade Out)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}

