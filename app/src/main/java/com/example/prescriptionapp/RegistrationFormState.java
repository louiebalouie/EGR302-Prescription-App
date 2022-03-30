package com.example.prescriptionapp;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class RegistrationFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer username2Error;

    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer password2Error;
    private boolean isDataValid;

    RegistrationFormState(@Nullable Integer usernameError, @Nullable Integer username2Error, @Nullable Integer passwordError, @Nullable Integer password2Error) {
        this.usernameError = usernameError;
        this.username2Error = username2Error;
        this.passwordError = passwordError;
        this.password2Error = password2Error;
        this.isDataValid = false;
    }

    RegistrationFormState(boolean isDataValid) {
        this.usernameError = null;
        this.username2Error = null;
        this.passwordError = null;
        this.password2Error = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }
    @Nullable
    Integer getUsername2Error() {
        return username2Error;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }
    @Nullable
    Integer getPassword2Error() {
        return password2Error;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}