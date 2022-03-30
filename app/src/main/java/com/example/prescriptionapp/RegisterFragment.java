package com.example.prescriptionapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.prescriptionapp.databinding.ActivityRegistrationBinding;

public class RegisterFragment extends Fragment {

    private RegistrationViewModel registrationViewModel;
    private ActivityRegistrationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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