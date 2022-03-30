package com.example.prescriptionapp;



/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class RegistrationRepository {

    private static volatile RegistrationRepository instance;

    private RegistrationDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private RegistrationRepository(RegistrationDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegistrationRepository getInstance(RegistrationDataSource dataSource) {
        if (instance == null) {
            instance = new RegistrationRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String username2, String password, String password2, String name, String phone) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, username2, password, password2, name, phone);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}