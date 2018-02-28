package com.zaher.news247.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zaher.news247.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button signInButton;
    private TextView signupText;
    private CoordinatorLayout coordinatorLayout;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
        coordinatorLayout = findViewById(R.id.signin_layout);
        email = (EditText) findViewById(R.id.email_sign_in);
        password = (EditText) findViewById(R.id.password_sign_in);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        signupText = (TextView) findViewById(R.id.sign_up_text);



        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // login logic goes here
                if(!isValidEmail(email.getText().toString())){
                    Snackbar snackbar = Snackbar.make(coordinatorLayout,"Please enter a valid email",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else if(email.getText().toString().isEmpty() || email.getText().toString().equals("")){
                    Snackbar snackbar = Snackbar.make(coordinatorLayout,"Please enter a valid email",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if(password.getText().toString().length()<6){
                    Snackbar snackbar = Snackbar.make(coordinatorLayout,"Please enter a valid password",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else{
                    signin(email.getText().toString(),password.getText().toString());
//                    Intent intent = new Intent(getBaseContext(),NewsActivity.class);
//                    startActivity(intent);

                }
            }
        });


        signupText.setClickable(true);
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open signup activity
                Intent intent = new Intent(getBaseContext(),SignupActivity.class);
                startActivity(intent);
            }
        });

        if(mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(getBaseContext(),NewsActivity.class);
            startActivity(intent);
        }

    }

    private void signin(final String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getBaseContext(),NewsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Email",email);
                            mFirebaseAnalytics.logEvent("UserSignIn",bundle);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar snackbar = Snackbar.make(coordinatorLayout,"You Entered a wrong Password, Or You don't have an account",Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                        // ...
                    }
                });
    }
    public static boolean isValidEmail(String email) {
        String expression = "^[\\w\\.]+@([\\w]+\\.)+[A-Z]{2,7}$";
        CharSequence inputString = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }
}
