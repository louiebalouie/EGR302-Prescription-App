package com.example.prescriptionapp;

/**
 * Copyright 2021 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.prescriptionapp.databinding.ActivityRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class EmailPasswordActivity extends Fragment {

    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
    // [END on_start_check_user]

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this.getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(EmailPasswordActivity.this.getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
        // [END sign_in_with_email]
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(this.getActivity(), new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
        // [END send_email_verification]
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }



    private RegistrationViewModel registrationViewModel;
    private ActivityRegistrationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        getActivity().setContentView(binding.getRoot());

        registrationViewModel = new ViewModelProvider(this, new RegistrationViewModelFactory())
                .get(RegistrationViewModel.class);


        // Variables used to initialize text activity and input fields
        final EditText usernameEditText = binding.username;
        final EditText username2EditText = binding.username2;
        final EditText passwordEditText = binding.password;
        final EditText password2EditText = binding.password2;
        final EditText nameEditText = binding.name;
        final EditText phoneEditText = binding.phone;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;



        // TODO Error message displays even if the user can register
        registrationViewModel.getLoginFormState().observe(this, new Observer<RegistrationFormState>() {
            @Override
            public void onChanged(@Nullable RegistrationFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (!loginFormState.isDataValid()) {
//                    if (loginFormState.getUsernameError() != null) {
//                        usernameEditText.setError(getString(loginFormState.getUsernameError()));
//                    }
                    if (loginFormState.getUsername2Error() != null) {
                        username2EditText.setError(getString(loginFormState.getUsername2Error()));
                    }
//                    if (loginFormState.getPasswordError() != null) {
//                        passwordEditText.setError(getString(loginFormState.getPasswordError()));
//                    }
                    if (loginFormState.getPassword2Error() != null) {
                        password2EditText.setError(getString(loginFormState.getPassword2Error()));
                    }
                }
            }
        });


        registrationViewModel.getLoginResult().observe(this, new Observer<RegistrationResult>() {
            @Override
            public void onChanged(@Nullable RegistrationResult registrationResult) {
                if (registrationResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (registrationResult.getError() != null) {
                    showLoginFailed(registrationResult.getError());
                }
                if (registrationResult.getSuccess() != null) {
                    updateUiWithUser(registrationResult.getSuccess());
                }
                getActivity().setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registrationViewModel.loginDataChanged(usernameEditText.getText().toString(),username2EditText.getText().toString(),
                        passwordEditText.getText().toString(),password2EditText.getText().toString(), nameEditText.getText().toString(), phoneEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        username2EditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        password2EditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registrationViewModel.login(usernameEditText.getText().toString(),username2EditText.getText().toString(),
                            passwordEditText.getText().toString(),password2EditText.getText().toString(), nameEditText.getText().toString(), phoneEditText.getText().toString());
                }
                return false;
            }
        });

        password2EditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registrationViewModel.login(usernameEditText.getText().toString(),username2EditText.getText().toString(),
                            passwordEditText.getText().toString(),password2EditText.getText().toString(), nameEditText.getText().toString(), phoneEditText.toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                registrationViewModel.login(usernameEditText.getText().toString(),username2EditText.getText().toString(),
                        passwordEditText.getText().toString(),password2EditText.getText().toString(), nameEditText.getText().toString(), phoneEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(RegistrationInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getActivity().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getActivity().getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}