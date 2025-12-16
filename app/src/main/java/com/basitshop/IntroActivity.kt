package com.basitshop // Make sure ye package name aapke project se match kare

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {

    // Variables declare kar rahe hain (Kotlin style)
    private lateinit var shapeTop: ImageView
    private lateinit var shapeBottom: ImageView
    private lateinit var iconArrow: ImageView
    private lateinit var containerMain: LinearLayout
    private lateinit var layoutButton: LinearLayout
    private lateinit var btnGlassAction: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // IDs find kar rahe hain
        shapeTop = findViewById(R.id.shape_top)
        shapeBottom = findViewById(R.id.shape_bottom)
        containerMain = findViewById(R.id.container_main)
        layoutButton = findViewById(R.id.layout_button)
        btnGlassAction = findViewById(R.id.btn_glass_action)
        iconArrow = findViewById(R.id.icon_arrow)

        // Animations Load kar rahe hain
        // Note: 'this' context sahi se pass ho raha hai
        val animFromTop = AnimationUtils.loadAnimation(this, R.anim.from_top)
        val animFromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom)
        val animLeftPop = AnimationUtils.loadAnimation(this, R.anim.left_pop_fade)
        val animButtonBounce = AnimationUtils.loadAnimation(this, R.anim.button_up_bounce)
        val animArrowShake = AnimationUtils.loadAnimation(this, R.anim.arrow_shake)

        // Animations Start kar rahe hain
        shapeTop.startAnimation(animFromTop)
        shapeBottom.startAnimation(animFromBottom)
        containerMain.startAnimation(animLeftPop)
        
        // Button neeche se bounce karega
        layoutButton.startAnimation(animButtonBounce)
        
        // Arrow hilti rahegi (Shake animation)
        iconArrow.startAnimation(animArrowShake)

        // Click Listener (Kotlin Lambda Style)
        val startAction = View.OnClickListener {
            // LoginActivity par jane ke liye Intent
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // finish() // Agar wapis nahi ana to ye line uncomment kar den
        }

        // Dono elements par click listener set kar diya
        btnGlassAction.setOnClickListener(startAction)
        layoutButton.setOnClickListener(startAction)
    }
}
