package com.example.gasbookingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    TextView FullName;
    TextInputLayout name,Email,Phno,Aadhaar;
    Button book;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAuth = FirebaseAuth.getInstance();

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();

        FullName=(TextView)findViewById(R.id.full_name);
        name=(TextInputLayout)findViewById(R.id.FullName);
        Email=(TextInputLayout)findViewById(R.id.Email);
        Phno=(TextInputLayout)findViewById(R.id.phno);
        Aadhaar=(TextInputLayout)findViewById(R.id.Aadhar);
        book=(Button)findViewById(R.id.order_button);


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Dashboard.this,BookARefill.class);
                startActivity(intent);

            }
        });
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile!=null)
                {
                    String Name=userProfile.FullName;
                    String email=userProfile.email;
                    String phno=userProfile.phno;
                    String aadhar=userProfile.aadhar;
                    String address=userProfile.Address;
                    FullName.setText(Name);
                    name.getEditText().setText(address);
                    Email.getEditText().setText(email);
                    Phno.getEditText().setText(phno);
                    Aadhaar.getEditText().setText(aadhar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Somethng went wrong!",Toast.LENGTH_LONG).show();

            }
        });


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