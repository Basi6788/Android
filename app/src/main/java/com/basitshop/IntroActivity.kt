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

    ImageView shapeTop, shapeBottom, iconArrow;
    View containerMain;
    LinearLayout layoutButton;
    FrameLayout btnGlassAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Hooks
        shapeTop = findViewById(R.id.shape_top);
        shapeBottom = findViewById(R.id.shape_bottom);
        containerMain = findViewById(R.id.container_main);
        layoutButton = findViewById(R.id.layout_button);
        btnGlassAction = findViewById(R.id.btn_glass_action);
        iconArrow = findViewById(R.id.icon_arrow);

        // Load Animations
        Animation animFromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        Animation animFromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        Animation animLeftPop = AnimationUtils.loadAnimation(this, R.anim.left_pop_fade);
        Animation animButtonBounce = AnimationUtils.loadAnimation(this, R.anim.button_up_bounce);
        Animation animArrowShake = AnimationUtils.loadAnimation(this, R.anim.arrow_shake);

        // Set Animations
        shapeTop.startAnimation(animFromTop);
        shapeBottom.startAnimation(animFromBottom);
        containerMain.startAnimation(animLeftPop);
        
        // Button comes from bottom with bounce
        layoutButton.startAnimation(animButtonBounce);
        
        // Arrow keeps shaking/wiggling inside the button
        iconArrow.startAnimation(animArrowShake);

        // Click Listener to open Login Screen
        View.OnClickListener startAction = v -> {
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class); // LoginActivity class name check kar lena
            startActivity(intent);
            // Optional: Finish this activity so user can't go back
            // finish();
        };

        btnGlassAction.setOnClickListener(startAction);
        layoutButton.setOnClickListener(startAction); // Text area click karne par bhi kaam karega
    }
}
