package com.example.gasbookingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Payment extends AppCompatActivity {

    TextInputLayout amountEt, noteEt, nameEt, upiIdEt;
    Button send,Cod;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    GMailSender sender;
    String User="etravelsrs@gmail.com";
    String password="rishisahana";
    String e,s,t;

    final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        sender=new GMailSender(User,password);

        send = findViewById(R.id.send);
        Cod=findViewById(R.id.COD);
        amountEt = findViewById(R.id.amount);
        noteEt = findViewById(R.id.note);
        nameEt = findViewById(R.id.name);
        upiIdEt = findViewById(R.id.upi);
        Intent intent=getIntent();
       int Num=intent.getIntExtra("num",0);
       float price=intent.getFloatExtra("Price",0);
       float total=Num*price;
       amountEt.getEditText().setText(Float.toString(total));


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the values from the EditTexts
                String amount = amountEt.getEditText().toString();
                String note = noteEt.getEditText().toString();
                String name = nameEt.getEditText().toString();
                String upiId = upiIdEt.getEditText().toString();
                payUsingUpi(amount, upiId, name, note);
            }
        });
        Cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, 7);


                reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userProfile=snapshot.getValue(User.class);
                        if(userProfile!=null) {
                            String Name = userProfile.FullName;
                            String Address = userProfile.Address;
                            String amount = amountEt.getEditText().getText().toString().trim();
                            String newDate = sdf.format(cal.getTime());
                            t="Hello "+Name+"\n"+"Deliver to : "+Address+"\n"+"Payment Amount : "+amount+"\n"+"Delivery Date : "+newDate;
                             e = userProfile.email;
                             s="Gas Refill Order Confirmation";
                            new MyAsyncClass().execute();



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),"Somethng went wrong!",Toast.LENGTH_LONG).show();

                    }
                });
                Intent i= new Intent(getApplicationContext(),Dashboard.class);
                startActivity(i);
            }
        });


    }


    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(Payment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }
    }


    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(Payment.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override

        protected Void doInBackground(Void... mApi) {
            try {

                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail(s, t, User, e);
                Log.d("send", "done");
            }
            catch (Exception ex) {
                Log.d("exceptionsending", ex.toString());
            }
            return null;
        }

        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            pDialog.cancel();

            Toast.makeText(Payment.this, "Order Confirmed", Toast.LENGTH_SHORT).show();

        }
    }


}