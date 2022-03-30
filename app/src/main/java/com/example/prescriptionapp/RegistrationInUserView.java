package com.example.prescriptionapp;

/**
 * Class exposing authenticated user details to the UI.
 */
class RegistrationInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    RegistrationInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}