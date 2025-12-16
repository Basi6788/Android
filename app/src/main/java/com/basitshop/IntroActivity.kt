package com.basitshop

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // Views ko ID se link karna
        val topDesign = findViewById<ImageView>(R.id.img_top_design)
        val bottomDesign = findViewById<ImageView>(R.id.img_bottom_design)
        val brandName = findViewById<TextView>(R.id.txt_brand_name)

        // Animations Load karna (Make sure anim folder me files hain)
        val animTop = AnimationUtils.loadAnimation(this, R.anim.top_down)
        val animBottom = AnimationUtils.loadAnimation(this, R.anim.bottom_up)
        val animText = AnimationUtils.loadAnimation(this, R.anim.left_fade_in)

        // Animations Start karna
        topDesign.startAnimation(animTop)
        bottomDesign.startAnimation(animBottom)
        brandName.startAnimation(animText)
    }
}

