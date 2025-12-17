package com.basitshop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen (hide status + nav)
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.getInsetsController().hide(
                    WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars()
            );
        } else {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }

        setContentView(R.layout.activity_intro);

        FrameLayout swipeContainer = findViewById(R.id.swipe_container);
        View swipeThumb = findViewById(R.id.swipe_thumb);

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
                        float dx = e.getRawX() - downX;
                        float max = swipeContainer.getWidth() - v.getWidth() - 8;
                        dx = Math.max(0, Math.min(dx, max));
                        v.setTranslationX(dx);

                        if (dx > swipeContainer.getWidth() * 0.7f && !done) {
                            done = true;
                            openLogin(v);
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (!done) v.animate().translationX(0).setDuration(200).start();
                        return true;
                }
                return false;
            }

            void openLogin(View v) {
                v.animate().translationX(swipeContainer.getWidth())
                        .setDuration(250)
                        .withEndAction(() -> {
                            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                            finish();
                        }).start();
            }
        });
    }
}