package com.zaher.news247.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zaher.news247.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText password1;
    Button signupButton;
    CoordinatorLayout coordinatorLayout;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email_sign_up);
        password = findViewById(R.id.password_sign_up);
        password1 = findViewById(R.id.password1_sign_up);
        signupButton = findViewById(R.id.sign_up_button);
        coordinatorLayout = findViewById(R.id.sign_up_layout);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //signup logic goes here
                if(email.getText().toString().isEmpty() || email.getText().toString().equals("")){
                    Snackbar snackbar = Snackbar.make(coordinatorLayout,R.string.valid_email,Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else if(!isValidEmail(email.getText().toString())){
                    Snackbar snackbar = Snackbar.make(coordinatorLayout,R.string.valid_email,Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if(!password.getText().toString().equals(password1.getText().toString())){
                    Snackbar snackbar = Snackbar.make(coordinatorLayout,R.string.password_2,Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else if(password.getText().toString().length() <6 ){
                    Snackbar snackbar = Snackbar.make(coordinatorLayout,R.string.password_6,Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else{
                    signup(email.getText().toString(),password.getText().toString());
                }
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
    public  void signup(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Snackbar snackbar = Snackbar.make(coordinatorLayout,R.string.signup_success,Snackbar.LENGTH_LONG);
                            snackbar.show();
                            Intent intent = new Intent(getBaseContext(),NewsActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar snackbar = Snackbar.make(coordinatorLayout,R.string.signup_error,Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });
    }
}
