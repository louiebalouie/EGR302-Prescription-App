package com.example.prescriptionapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;



public class RegistrationViewModel extends ViewModel {

    private MutableLiveData<RegistrationFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<RegistrationResult> loginResult = new MutableLiveData<>();
    private RegistrationRepository registrationRepository;

    RegistrationViewModel(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    LiveData<RegistrationFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<RegistrationResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password, String password2, String name, String phone) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = registrationRepository.login(email, password, password2, name, phone);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new RegistrationResult(new RegistrationInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new RegistrationResult(R.string.login_failed));
        }
    }


    // TODO Bug with password matching second password but when either is changed the flag is not removed for register access
    public void loginDataChanged(String email, String password, String password2, String name, String phone) {
        if (!isPasswordValid(password,password2)) {
            loginFormState.setValue(new RegistrationFormState(null, null, R.string.invalid_password,R.string.invalid_password));
        }  else {
            loginFormState.setValue(new RegistrationFormState(true));
        }
    }

    // Username validation check
    private boolean isUserNameValid(String username, String username2) {
        if (username == null && (username.trim().length() < 5 || username2.trim().length() < 5)) {
            return false;
        } else if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return username.trim().toString().equals(username2.trim().toString());
        }
    }

    // Password validation check
    private boolean isPasswordValid(String password, String password2) {
        if (password == null || password2 == null || password.trim().length() < 7 || password2.trim().length() < 7) {
            return false;
        } else {
            return password.trim().toString().equals(password2.trim().toString());
        }
    }

}