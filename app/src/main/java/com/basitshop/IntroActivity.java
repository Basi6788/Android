package com.basitshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    // Variables
    ImageView shapeTop, shapeBottom, iconArrow;
    TextView txtBrandName; // Changed from LinearLayout to TextView
    LinearLayout layoutButton;
    FrameLayout btnGlassAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // 1. IDs find kar rahe hain
        shapeTop = findViewById(R.id.shape_top);
        shapeBottom = findViewById(R.id.shape_bottom);
        txtBrandName = findViewById(R.id.txt_brand_name); // New ID
        layoutButton = findViewById(R.id.layout_button);
        btnGlassAction = findViewById(R.id.btn_glass_action);
        iconArrow = findViewById(R.id.icon_arrow);

        // 2. Animations Load kar rahe hain
        Animation animFromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        Animation animFromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        Animation animLeftPop = AnimationUtils.loadAnimation(this, R.anim.left_pop_fade);
        Animation animButtonBounce = AnimationUtils.loadAnimation(this, R.anim.button_up_bounce);
        Animation animArrowShake = AnimationUtils.loadAnimation(this, R.anim.arrow_shake);

        // 3. Animations Start kar rahe hain
        shapeTop.startAnimation(animFromTop);
        shapeBottom.startAnimation(animFromBottom);
        
        // Ab animation seedha BASITSHOP text par lagegi
        txtBrandName.startAnimation(animLeftPop);
        
        layoutButton.startAnimation(animButtonBounce);
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