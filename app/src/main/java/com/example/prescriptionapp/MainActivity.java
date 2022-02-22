package com.example.prescriptionapp;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "For help Email: blaked.poulson@calbaptist.edu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //getSupportFragmentManager().beginTransaction().add(R.id.container, new NavigationFragment()).commit();
    }
@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void goHome (View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FirstFragment()).commit();
        //startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

    public void goDrugs (View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Drugs()).commit();
    }

    public void goPrescriptions (View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new PrescriptionLog()).commit();
    }

    public void onScan (View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SecondFragment()).commit();
    }
}