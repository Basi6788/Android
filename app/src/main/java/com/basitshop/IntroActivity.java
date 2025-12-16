package com.basitshop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    ImageView shapeTop, shapeBottom, iconArrow;
    TextView txtBrandName;
    LinearLayout layoutButton;
    FrameLayout btnActionContainer;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ================================================================
        //  THE UNIVERSAL FULLSCREEN FIX (HAR PHONE KE LIYE)
        // ================================================================
        
        // 1. Layout No Limits: Ye layout ko screen ke hardware edges tak le jayega
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, 
                   WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // 2. Hide Navigation Bar (Optional): Agar neechay walay buttons bhi hatane hain
        // w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        
        // ================================================================

        setContentView(R.layout.activity_intro);

        // IDs
        shapeTop = findViewById(R.id.shape_top);
        shapeBottom = findViewById(R.id.shape_bottom);
        txtBrandName = findViewById(R.id.txt_brand_name);
        layoutButton = findViewById(R.id.layout_button);
        btnActionContainer = findViewById(R.id.btn_action_container);
        iconArrow = findViewById(R.id.icon_arrow);

        // Animations
        Animation animFromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        Animation animFromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        Animation animLeftPop = AnimationUtils.loadAnimation(this, R.anim.left_pop_fade);
        Animation animButtonBounce = AnimationUtils.loadAnimation(this, R.anim.button_up_bounce);
        Animation animArrowShake = AnimationUtils.loadAnimation(this, R.anim.arrow_shake);

        shapeTop.startAnimation(animFromTop);
        shapeBottom.startAnimation(animFromBottom);
        txtBrandName.startAnimation(animLeftPop);
        layoutButton.startAnimation(animButtonBounce);
        iconArrow.startAnimation(animArrowShake);

        // Click Logic
        View.OnClickListener startAction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        btnActionContainer.setOnClickListener(startAction);
        layoutButton.setOnClickListener(startAction);

        // Permissions Check
        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        }
    }
}
