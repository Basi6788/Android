package com.basitshop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    private ImageView shapeTop;
    private ImageView shapeBottom;
    private TextView txtBrandName;
    private FrameLayout swipeContainer;
    private FrameLayout swipeThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ===== SAFE FULLSCREEN (Android 6–16) =====
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            if (getWindow().getInsetsController() != null) {
                getWindow().getInsetsController().hide(
                        WindowInsets.Type.statusBars()
                );
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
        // =========================================

        setContentView(R.layout.activity_intro);

        shapeTop = findViewById(R.id.shape_top);
        shapeBottom = findViewById(R.id.shape_bottom);
        txtBrandName = findViewById(R.id.txt_brand_name);
        swipeContainer = findViewById(R.id.swipe_container);
        swipeThumb = findViewById(R.id.swipe_thumb);

        runIntroAnimations();
        setupSwipeToLogin();
    }

    private void runIntroAnimations() {
        try {
            if (shapeTop != null)
                shapeTop.startAnimation(
                        AnimationUtils.loadAnimation(this, R.anim.from_top));

            if (shapeBottom != null)
                shapeBottom.startAnimation(
                        AnimationUtils.loadAnimation(this, R.anim.from_bottom));

            if (txtBrandName != null)
                txtBrandName.startAnimation(
                        AnimationUtils.loadAnimation(this, R.anim.left_pop_fade));
        } catch (Exception ignored) {
        }
    }

    private void setupSwipeToLogin() {
        if (swipeContainer == null || swipeThumb == null) return;

        swipeThumb.setOnTouchListener(new View.OnTouchListener() {
            float downX;
            boolean done = false;

            @Override
            public boolean onTouch(View v, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = e.getRawX();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float delta = e.getRawX() - downX;
                        float max = swipeContainer.getWidth() - v.getWidth();
                        delta = Math.max(0, Math.min(delta, max));
                        v.setTranslationX(delta);

                        if (delta > swipeContainer.getWidth() * 0.7f && !done) {
                            done = true;
                            openLogin(v);
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (!done) {
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
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                })
                .start();
    }
}