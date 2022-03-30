package com.example.prescriptionapp;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class RegistrationResult {
    @Nullable
    private RegistrationInUserView success;
    @Nullable
    private Integer error;

    RegistrationResult(@Nullable Integer error) {
        this.error = error;
    }

    RegistrationResult(@Nullable RegistrationInUserView success) {
        this.success = success;
    }

    @Nullable
    RegistrationInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}