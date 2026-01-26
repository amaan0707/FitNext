// Hello World
package com.example.fitnext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText textInputEmailLogin,textInputPasswordLogin;
    private TextInputLayout textLayoutEmailLogin,textLayoutPasswordLogin;
    private TextView newUserTextview,forgotPasswordTextview;
    private Button loginBtn;
    private ImageView googleBtn,callBtn;
    ProgressBar progressBar;
    String email,password;
    FirebaseAuth auth;
   GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting views
        textLayoutEmailLogin=findViewById(R.id.textLayoutEmailLogin);
        textLayoutPasswordLogin=findViewById(R.id.textLayoutPasswordLogin);
        textInputEmailLogin=findViewById(R.id.textInputEmailLogin);
        textInputPasswordLogin=findViewById(R.id.textInputPasswordLogin);
        newUserTextview=findViewById(R.id.newUserTextview);
        forgotPasswordTextview=findViewById(R.id.forgotPasswordTextview);
        loginBtn=findViewById(R.id.loginBtn);
        googleBtn=findViewById(R.id.googleBtn);
        callBtn=findViewById(R.id.callBtn);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //rest of the code below
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = auth.getCurrentUser();

        // Check condition
        if (firebaseUser != null) {
            if(firebaseUser.isEmailVerified()) {
                // When user already sign in redirect to profile activity
                startActivity(new Intent(LoginActivity.this, DashBoardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }


        //the below code is used to prevent entering of the white space
        InputFilter noWhiteSpaceFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // Check each character being entered
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        // If a blank space is detected, prevent it from being entered
                        return "";
                    }
                }
                // Allow other characters
                return null;
            }
        };

        textInputPasswordLogin.setFilters(new InputFilter[]{noWhiteSpaceFilter});



        //TextWatcher implementation for Email Field
        textInputEmailLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        //TextWatcher implementation for Password Field
        textInputPasswordLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePassword(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        });


        //setting the newUser Textview
        newUserTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        //setting the forgotPassword Textview
        forgotPasswordTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        //setting the login Button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this method is called for the login purpose
                loginUser();

            }
        });

        //setting the google SignIn Button
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithGoogle();
            }
        });


        //setting the call SignIn Button
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, PhoneCallLoginActivity.class));
            }
        });


        //below are the codes of google sign in

        // Initialize sign in options the client-id is copied form google-services.json file
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("18928278849-5hvr52d78ok1svdvsgkb51apflslci00.apps.googleusercontent.com")
                .requestEmail()
                .requestId()
                .build();

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);
        googleSignInClient.signOut();

    }

    //the below method is used to login with google
    private void loginWithGoogle() {
        // Initialize sign in intent
        Intent intent = googleSignInClient.getSignInIntent();
        // Start activity for result
        startActivityForResult(intent, 100);
    }


    //the below method calls the validate() method which is used to login
    private void loginUser() {
        email = textInputEmailLogin.getText().toString().trim();
        password = textInputPasswordLogin.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            textLayoutEmailLogin.setError("Email is required");
            textLayoutEmailLogin.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textLayoutEmailLogin.setError("Not a valid email address");
            textLayoutEmailLogin.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            textLayoutPasswordLogin.setError("Password is required");
            textLayoutPasswordLogin.requestFocus();
            return;
        }

        if(password.length() < 6){
            textLayoutPasswordLogin.setError("Minimum 6 characters required");
            textLayoutPasswordLogin.requestFocus();
            return;
        }

        //the below method is invoked to make the user logged in
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            validate(email, password);
        }




    }


    //the below method is used to validate the email and password after clicking on Login Button
    public void validate(String userEmail, String userPassword){

        progressBar.setVisibility(View.VISIBLE);

        //using the firebase code to sign in with email and password
        auth.signInWithEmailAndPassword(userEmail,
                        userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            if(auth.getCurrentUser().isEmailVerified()){
                                Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, DashBoardActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            }else{
                                Toast.makeText(LoginActivity.this, "Please verify your email address"
                                        , Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid credentials or the account doesn't exist"
                                    , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //items selected on the app bar handled in this method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle the Up arrow button of the app bar
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;

            // Other menu item cases can be added here if needed

        }

        return super.onOptionsItemSelected(item);
    }


    //the below method is used for validating the email
    private void validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            textLayoutEmailLogin.setError("Email is required");
            textLayoutEmailLogin.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textLayoutEmailLogin.setError("Not a valid email address");
            textLayoutEmailLogin.requestFocus();
            return;
        }


        // Clear the error if the email is valid
        textLayoutEmailLogin.setError(null);
    }


    //the below method is used for validating the password
    private void validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            textLayoutPasswordLogin.setError("Password is required");
            textLayoutPasswordLogin.requestFocus();
            return;
        }
        if (password.length() < 6) {
            textLayoutPasswordLogin.setError("Minimum 6 characters required");
            textLayoutPasswordLogin.requestFocus();
            return;
        }


        textLayoutPasswordLogin.setError(null);
    }


    //the below code is used for resetting the password
    public void resetPassword(){
        final String resetEmail = textInputEmailLogin.getText().toString();

        if (resetEmail.isEmpty()) {
            textLayoutEmailLogin.setError("Enter email to reset password");
            textLayoutEmailLogin.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        //using the firebase code to send email for resetting the password
        auth.sendPasswordResetEmail(resetEmail)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "An email has been sent to reset password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            // check condition
            if (signInAccountTask.isSuccessful()) {
                // When google sign in successful initialize string
                String s = "Google sign in successful";
                // Display Toast
                displayToast(s);
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential
                        auth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Check condition
                                if (task.isSuccessful()) {
                                    // When task is successful redirect to profile activity display Toast
                                    startActivity(new Intent(LoginActivity.this, DashBoardActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

//
                                } else {
                                    // When task is unsuccessful display Toast
                                    displayToast("Authentication Failed :" + task.getException().getMessage());
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

}
