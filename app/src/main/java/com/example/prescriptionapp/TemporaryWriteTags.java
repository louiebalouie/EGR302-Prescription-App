package com.example.prescriptionapp;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;
import com.google.firebase.database.DatabaseReference;


public class TemporaryWriteTags {

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    public TemporaryWriteTags(DatabaseReference database) {
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
    }

    // [START rtdb_write_new_user]
    public void writeNewUser(String userId, String name, String username, String password, int phoneNum) {
        User user = new User(name, username, password, phoneNum);

        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END rtdb_write_new_user]

    public void writeNewUserWithTaskListeners(String userId, String name, String username, String password, int phoneNum) {
        User user = new User(name, username, password, phoneNum);

        // [START rtdb_write_new_user_task]
        mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // All is well
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed --- could be for invalid info
                        // Redirect to page??
                    }
                });
        // [END rtdb_write_new_user_task]
    }


    private String getUid() {
        return "";
    }


}
