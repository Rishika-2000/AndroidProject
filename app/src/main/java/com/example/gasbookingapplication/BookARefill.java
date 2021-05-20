package com.example.gasbookingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookARefill extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    TextInputLayout FullName,Address,Phno,Number;
    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_a_refill);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        FullName=(TextInputLayout)findViewById(R.id.Name);
        Address=(TextInputLayout) findViewById(R.id.Address);
        Phno=(TextInputLayout) findViewById(R.id.phno);
        Number=(TextInputLayout)findViewById(R.id.number);
        pay=(Button)findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String number=Number.getEditText().getText().toString().trim();
                int Num=Integer.parseInt(number);
                Intent intent= new Intent(BookARefill.this,Payment.class);
                intent.putExtra("num",Num);
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
                    FullName.getEditText().setText(Name);
                    String address=userProfile.Address;
                    Address.getEditText().setText(address);
                    String phno=userProfile.phno;
                    Phno.getEditText().setText(phno);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}