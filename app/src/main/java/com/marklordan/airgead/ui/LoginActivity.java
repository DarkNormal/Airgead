package com.marklordan.airgead.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marklordan.airgead.R;
import com.marklordan.airgead.db.AirgeadContract;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    public interface ProgressListener {
        public void onProgressShown();
        public void onProgressDismissed();
    }

    private static final String TAG = LoginActivity.class.getSimpleName();

    private ProgressListener mProgressListener;

    public void setProgressListener(ProgressListener progressListener){
        mProgressListener = progressListener;
    }

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mSignInButton;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean previouslyLoggedIn = prefs.getBoolean(getString(R.string.previously_logged_in), false);
        if(previouslyLoggedIn){
            proceedToMainActivity();
        }
        else{
            initialiseUSerAccountOnDb();
        }

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                }
                else{
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };


        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mProgressBar = (ProgressBar) findViewById(R.id.loginProgressbar);

        mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(mEmailView.getText().toString(), mPasswordView.getText().toString());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private void signIn(String email, String password){
        if(!validateForm()){
            return;
        }

        showProgress();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dismissProgress();
                        Log.d(TAG, "onComplete: " + task.isSuccessful());
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(this.getClass().toString(), user.getEmail());
                            updateUI(user);
                        }
                        else{
                            Log.d(TAG, "onComplete: " + task.getException().getMessage());
                            Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private boolean validateForm(){
        boolean valid = true;
        String emailAddress = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        if(TextUtils.isEmpty(emailAddress)){
            mEmailView.setError("Required");
            valid = false;
        }else
            mEmailView.setError(null);

        if(TextUtils.isEmpty(password)){
            mPasswordView.setError("Required");
            valid = false;
        }
        else
            mPasswordView.setError(null);

        return valid;
    }

    private void updateUI(FirebaseUser user){
        if(user != null){
            proceedToMainActivity();
        }



    }
    public void skipLoginProcess(View v){
        proceedToMainActivity();
    }

    public void proceedToMainActivity(){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putBoolean(getString(R.string.previously_logged_in), true);
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initialiseUSerAccountOnDb() {
        ContentValues values = new ContentValues();
        values.put(AirgeadContract.AccountTable.Cols._ID, 1);
        values.put(AirgeadContract.AccountTable.Cols.BALANCE, 0);
        values.put(AirgeadContract.AccountTable.Cols.SAVINGS_TARGET, 0);
        try {
            getContentResolver().insert(AirgeadContract.AccountTable.CONTENT_URI, values);
        }
        catch(SQLException ex){
            Log.d(TAG, "initialiseUSerAccountOnDb: " + ex.getMessage());
        }
    }

    private void showProgress() {
        // show the progress and notify the listener
        mProgressBar.setVisibility(View.VISIBLE);
        notifyListener(mProgressListener);
    }

    private void dismissProgress() {
        // hide the progress and notify the listener
        mProgressBar.setVisibility(View.INVISIBLE);
        notifyListener(mProgressListener);
    }

    public boolean isInProgress() {
        // return true if progress is visible
        return mProgressBar.isShown();
    }

    private void notifyListener(ProgressListener listener) {
        if (listener == null){
            return;
        }
        if (isInProgress()){
            listener.onProgressShown();
        }
        else {
            listener.onProgressDismissed();
        }
    }

}

