package com.basitshop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ImageView shapeTop;
    private ImageView shapeBottom;
    private TextView txtBrandName;
    private FrameLayout swipeContainer;
    private FrameLayout swipeThumb;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (window.getInsetsController() != null) {
                window.getInsetsController().hide(
                        WindowInsets.Type.statusBars()
                                | WindowInsets.Type.navigationBars()
                );
            }
        } else {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }

        setContentView(R.layout.activity_intro);

        shapeTop = findViewById(R.id.shape_top);
        shapeBottom = findViewById(R.id.shape_bottom);
        txtBrandName = findViewById(R.id.txt_brand_name);
        swipeContainer = findViewById(R.id.swipe_container);
        swipeThumb = findViewById(R.id.swipe_thumb);

        // Animations (safe)
        try {
            if (shapeTop != null) {
                Animation a = AnimationUtils.loadAnimation(this, R.anim.from_top);
                shapeTop.startAnimation(a);
            }
            if (shapeBottom != null) {
                Animation a = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
                shapeBottom.startAnimation(a);
            }
            if (txtBrandName != null) {
                Animation a = AnimationUtils.loadAnimation(this, R.anim.left_pop_fade);
                txtBrandName.startAnimation(a);
            }
        } catch (Exception ignored) {
        }

        setupSwipeToLogin();
        checkAndRequestPermissions();
    }

    private void setupSwipeToLogin() {
        if (swipeContainer == null || swipeThumb == null) return;

        swipeThumb.setOnTouchListener(new View.OnTouchListener() {

            float downX;
            boolean completed = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        downX = event.getRawX();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float delta = event.getRawX() - downX;
                        float max = swipeContainer.getWidth() - v.getWidth() - 8;
                        delta = Math.max(0, Math.min(delta, max));
                        v.setTranslationX(delta);

                        if (delta > swipeContainer.getWidth() * 0.7f && !completed) {
                            completed = true;
                            openLogin(v);
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (!completed) {
                            v.animate().translationX(0).setDuration(200).start();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    private void openLogin(View v) {
        v.animate()
                .translationX(swipeContainer.getWidth())
                .setDuration(250)
                .withEndAction(() -> {
                    startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                    finish();
                })
                .start();
    }

    private void checkAndRequestPermissions() {

        List<String> permissions = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }

        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this,
                    permissions.toArray(new String[0]),
                    PERMISSION_REQUEST_CODE
            );
        }
    }
}