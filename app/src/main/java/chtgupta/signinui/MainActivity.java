package chtgupta.signinui;

import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    View arrowSignUp, arrowLogin;
    ConstraintLayout loginLayout, signUpLayout;
    TextView textLogin, textSignUp;
    LinearLayout root;
    LinedRelativeLayout loginLinedRelativeLayout, signUpLinedRelativeLayout;
    ImageView logo_login, logo_sign_up;
    EditText login_email, login_password, sign_up_name, sign_up_email, sign_up_password;
    CheckBox loginCheckBox, signUpCheckBox;
    Button login_button, sign_up_button;

    State state = State.LOGIN;

    private final int SWITCH_DURATION = 300;
    private final float INTERPOLATOR_TENSION = 1.1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrowSignUp = findViewById(R.id.arrowSignUp);
        arrowLogin = findViewById(R.id.arrowLogin);
        loginLayout = findViewById(R.id.loginLayout);
        signUpLayout = findViewById(R.id.signUpLayout);
        textLogin = findViewById(R.id.textLogin);
        textSignUp = findViewById(R.id.textSignUp);
        root = findViewById(R.id.root);
        loginLinedRelativeLayout = findViewById(R.id.loginLinedRelativeLayout);
        signUpLinedRelativeLayout = findViewById(R.id.signUpLinedRelativeLayout);
        logo_login = findViewById(R.id.logo_login);
        logo_sign_up = findViewById(R.id.logo_sign_up);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        loginCheckBox = findViewById(R.id.loginCheckBox);
        login_button = findViewById(R.id.login_button);

        sign_up_name = findViewById(R.id.sign_up_name);
        sign_up_email = findViewById(R.id.sign_up_email);
        sign_up_password = findViewById(R.id.sign_up_password);
        signUpCheckBox = findViewById(R.id.signUpCheckBox);
        sign_up_button = findViewById(R.id.sign_up_button);

        root.post(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        prepareUI();
                    }
                });
            }
        });

        arrowSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpLayout();
            }
        });

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpLayout();
            }
        });

        arrowLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginLayout();
            }
        });

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginLayout();
            }
        });

    }

    private void prepareUI() {

        int height = textLogin.getMeasuredHeight() + (int) Utils.pxFromDp(this, 32);

        loginLayout.setLayoutParams(new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, root.getMeasuredHeight() - height));
        signUpLayout.setLayoutParams(new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, height));

        loginLinedRelativeLayout.setLineColor(getResources().getColor(android.R.color.white));
        signUpLinedRelativeLayout.setLineColor(getResources().getColor(R.color.red));
        loginLinedRelativeLayout.setAnimDuration(SWITCH_DURATION);
        loginLinedRelativeLayout.setInterpolator(new OvershootInterpolator(INTERPOLATOR_TENSION));

        Typeface typeface = Typeface.createFromAsset(getAssets(), "Poppins-SemiBold.ttf");
        textLogin.setTypeface(typeface);
        textSignUp.setTypeface(typeface);

        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "Karla-Regular.ttf");

        login_email.setTypeface(typeface1);
        login_password.setTypeface(typeface1);
        loginCheckBox.setTypeface(typeface1);
        login_button.setTypeface(typeface1);

        sign_up_name.setTypeface(typeface1);
        sign_up_email.setTypeface(typeface1);
        sign_up_password.setTypeface(typeface1);
        signUpCheckBox.setTypeface(typeface1);
        sign_up_button.setTypeface(typeface1);

        Glide.with(this).load(R.drawable.ic_logo_light).into(logo_login);
        Glide.with(this).load(R.drawable.ic_logo_dark).into(logo_sign_up);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            disableAutoFill();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void disableAutoFill() {
        getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
    }

    private void showSignUpLayout() {

        if (state == State.SIGN_UP || state == State.IN_TRANSIT) {
            return;
        }

        final int totalHeight = root.getMeasuredHeight();
        int height = root.getMeasuredHeight() - textLogin.getMeasuredHeight() - (int) Utils.pxFromDp(this, 32);

        ValueAnimator animator = ValueAnimator.ofInt(signUpLayout.getMeasuredHeight(), height);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                signUpLayout.setLayoutParams(new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        val));
                loginLayout.setLayoutParams(new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        totalHeight - val));
            }

        });
        animator.setDuration(SWITCH_DURATION);
        animator.setInterpolator(new OvershootInterpolator(INTERPOLATOR_TENSION));
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();

        state = State.SIGN_UP;

    }

    private void showLoginLayout() {

        if (state == State.LOGIN || state == State.IN_TRANSIT) {
            return;
        }

        final int totalHeight = root.getMeasuredHeight();
        int height = root.getMeasuredHeight() - textSignUp.getMeasuredHeight() - (int) Utils.pxFromDp(this, 32);

        ValueAnimator animator = ValueAnimator.ofInt(loginLayout.getMeasuredHeight(), height);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                loginLayout.setLayoutParams(new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        val));
                signUpLayout.setLayoutParams(new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        totalHeight - val));
            }
        });
        animator.setDuration(SWITCH_DURATION);
        animator.setInterpolator(new OvershootInterpolator(INTERPOLATOR_TENSION));
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();


        state = State.LOGIN;

    }

}
