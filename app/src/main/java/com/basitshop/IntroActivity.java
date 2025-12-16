package com.basitshop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

        // --- 1. THE ULTIMATE EDGE FIX ---
        // Ye line layout ko force karegi ke wo Status Bar aur Navigation Bar ke NEECHAY draw ho.
        // Is se wo "edges" wala gap khatam ho jayega aur status bar bhi nazar ayega.
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, 
                   WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_intro);

        // --- 2. IDs Find Karna ---
        shapeTop = findViewById(R.id.shape_top);
        shapeBottom = findViewById(R.id.shape_bottom);
        txtBrandName = findViewById(R.id.txt_brand_name);
        layoutButton = findViewById(R.id.layout_button);
        btnActionContainer = findViewById(R.id.btn_action_container);
        iconArrow = findViewById(R.id.icon_arrow);

        // --- 3. Animations ---
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

        // --- 4. Click Listener ---
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

        // --- 5. Permissions ---
        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[0]),
                    PERMISSION_REQUEST_CODE);
        }
    }
}
