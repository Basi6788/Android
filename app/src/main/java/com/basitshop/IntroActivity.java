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
        setContentView(R.layout.activity_intro);

        // IDs find kar rahe hain
        shapeTop = findViewById(R.id.shape_top);
        shapeBottom = findViewById(R.id.shape_bottom);
        containerMain = findViewById(R.id.container_main);
        layoutButton = findViewById(R.id.layout_button);
        btnGlassAction = findViewById(R.id.btn_glass_action);
        iconArrow = findViewById(R.id.icon_arrow);

        // Animations Load kar rahe hain
        // Agar yahan error aye to check karein ke res/anim folder mein files hain ya nahi
        Animation animFromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        Animation animFromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        Animation animLeftPop = AnimationUtils.loadAnimation(this, R.anim.left_pop_fade);
        Animation animButtonBounce = AnimationUtils.loadAnimation(this, R.anim.button_up_bounce);
        Animation animArrowShake = AnimationUtils.loadAnimation(this, R.anim.arrow_shake);

        // Animations Start kar rahe hain
        shapeTop.startAnimation(animFromTop);
        shapeBottom.startAnimation(animFromBottom);
        containerMain.startAnimation(animLeftPop);
        
        // Button neeche se bounce karega
        layoutButton.startAnimation(animButtonBounce);
        
        // Arrow hilti rahegi (Shake animation)
        iconArrow.startAnimation(animArrowShake);

        // Click Listener
        View.OnClickListener startAction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LoginActivity par jane ke liye
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
                // finish(); // Agar wapis nahi ana to ye uncomment karein
            }
        };

        // Dono elements par click listener set kar diya
        btnGlassAction.setOnClickListener(startAction);
        layoutButton.setOnClickListener(startAction);
    }
}
