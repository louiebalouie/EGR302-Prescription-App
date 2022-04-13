package com.example.prescriptionapp;


import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class RegistrationDataSource {

    public Result<LoggedInUser> login(String email, String password, String password2, String name, String phone) {

        try {

            // TODO this is where the database communication will go
//            try {
//                PrintStream output = new PrintStream("register.txt");
//                output.println("username: "+ username + " , Password: " + password );
//                output.close();
//            }
//            catch(Exception e) {
//                e.getStackTrace();
//            }
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}