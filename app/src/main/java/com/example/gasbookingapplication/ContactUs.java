package com.example.gasbookingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.prof:
                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(i);
                return true;
            case R.id.safety:
                Intent i2 = new Intent(getApplicationContext(), SafetyTips.class);
                startActivity(i2);
                return true;
            case R.id.contact:
                Intent i3 = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(i3);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }
}