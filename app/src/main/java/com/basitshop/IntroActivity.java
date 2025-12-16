package com.basitshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    // Variables
    ImageView shapeTop, shapeBottom, iconArrow;
    LinearLayout containerMain, layoutButton;
    FrameLayout btnGlassAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure layout file name matches exactly
        setContentView(R.layout.activity_intro);

        // 1. IDs find kar rahe hain
        shapeTop = findViewById(R.id.shape_top);       // Ye top_left.png wali image hai
        shapeBottom = findViewById(R.id.shape_bottom); // Ye bottom_right.png wali image hai
        containerMain = findViewById(R.id.container_main);
        layoutButton = findViewById(R.id.layout_button);
        btnGlassAction = findViewById(R.id.btn_glass_action);
        iconArrow = findViewById(R.id.icon_arrow);

        // 2. Animations Load kar rahe hain
        // Ye tabhi chalengi jab aap upar di gayi 5 files res/anim folder mein dalenge
        Animation animFromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        Animation animFromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        Animation animLeftPop = AnimationUtils.loadAnimation(this, R.anim.left_pop_fade);
        Animation animButtonBounce = AnimationUtils.loadAnimation(this, R.anim.button_up_bounce);
        Animation animArrowShake = AnimationUtils.loadAnimation(this, R.anim.arrow_shake);

        // 3. Animations Start kar rahe hain
        // Top Left PNG image slide down hogi
        shapeTop.startAnimation(animFromTop);
        
        // Bottom Right PNG image slide up hogi
        shapeBottom.startAnimation(animFromBottom);
        
        // Beech wala text left se aayega
        containerMain.startAnimation(animLeftPop);
        
        // Button neeche se bounce karega
        layoutButton.startAnimation(animButtonBounce);
        
        // Arrow hilti rahegi
        iconArrow.startAnimation(animArrowShake);

        // 4. Click Listener
        View.OnClickListener startAction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };

        btnGlassAction.setOnClickListener(startAction);
        layoutButton.setOnClickListener(startAction);
    }
}