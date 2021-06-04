package com.example.gasbookingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin extends AppCompatActivity {
    TextInputLayout commercial,domestic;
    Button price,logout,addadmin;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAuth = FirebaseAuth.getInstance();
        commercial=(TextInputLayout)findViewById(R.id.commercial);
        domestic=(TextInputLayout)findViewById(R.id.domestic);
        price=(Button)findViewById(R.id.price);
        logout=(Button)findViewById(R.id.logout);
        addadmin=(Button)findViewById(R.id.admin);
        reference=FirebaseDatabase.getInstance().getReference("Price");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Price price=snapshot.getValue(Price.class);
                if(price!=null)
                {
                    float com= price.commercial;
                    float dom= price.domestic;

                    commercial.getEditText().setText(Float.toString(com));
                    domestic.getEditText().setText(Float.toString(dom));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Somethng went wrong!",Toast.LENGTH_LONG).show();

            }
        });
        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com=commercial.getEditText().getText().toString().trim();
                float c=Float.parseFloat(com);
                String dom=domestic.getEditText().getText().toString().trim();
                float d=Float.parseFloat(dom);
                Price p=new Price(c,d);
                reference.setValue(p);
                Toast.makeText(getApplicationContext(),"Price Updated",Toast.LENGTH_SHORT).show();

            }
        });
        addadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Admin.this,AdminRegister.class);
                startActivity(intent1);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(Admin.this, login.class);
                startActivity(i);
            }
        });
    }
}