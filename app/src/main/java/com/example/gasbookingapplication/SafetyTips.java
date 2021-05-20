package com.example.gasbookingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SafetyTips extends AppCompatActivity {
    TextView t;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_tips);
        mAuth = FirebaseAuth.getInstance();
        t=(TextView)findViewById(R.id.textView3);
        t.setText(R.string.content);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu,menu);


        return true;
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
            case R.id.logout:
                mAuth.signOut();
                Intent i4 = new Intent(getApplicationContext(), login.class);
                startActivity(i4);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}