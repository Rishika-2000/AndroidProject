package com.example.gasbookingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    Button signin,signup;
    private FirebaseAuth mAuth;


    TextInputLayout FullName,Address,phno,emailid,password,aadhar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_register);
       signin=(Button)findViewById(R.id.goback);
        signup=(Button)findViewById(R.id.signup);

        FullName=(TextInputLayout) findViewById(R.id.Name);
        Address=(TextInputLayout)findViewById(R.id.Address);
        phno=(TextInputLayout)findViewById(R.id.phno);
        emailid=(TextInputLayout)findViewById(R.id.emailid);
        password=(TextInputLayout)findViewById(R.id.password );
        aadhar=(TextInputLayout)findViewById(R.id.Aadhar);




        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(register.this,login.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String Name=FullName.getEditText().getText().toString().trim();
                String Add=Address.getEditText().getText().toString().trim();
                String ph=phno.getEditText().getText().toString().trim();
                String email=emailid.getEditText().getText().toString().trim();
                String pass=password.getEditText().getText().toString().trim();
                String Aadhar=aadhar.getEditText().getText().toString().trim();
                boolean f=false;
               if(Name.isEmpty())
                {
                    FullName.setError("Required");
                    FullName.requestFocus();
                    return;
                }
                if(Add.isEmpty())
                {
                    Address.setError("Required");
                    Address.requestFocus();
                    return;
                }
                if(Aadhar.isEmpty())
                {
                    aadhar.setError("Required");
                    aadhar.requestFocus();
                    return;
                }
                if(ph.isEmpty())
                {
                    phno.setError("Required");
                    phno.requestFocus();
                    return;
                }
                if(email.isEmpty())
                {
                    emailid.setError("Required");
                    emailid.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    emailid.setError("Provide a valid email");
                    emailid.requestFocus();
                    return;

                }
                if(pass.isEmpty())
                {
                    password.setError("Required");
                    password.requestFocus();
                    return;
                }
                if(pass.length()<6)
                {
                    password.setError("Password too weak");
                    password.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user= new User(Name,Add,ph,email,Aadhar,f);
                                     FirebaseDatabase.getInstance().getReference("Users")
                                             .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                             .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful())
                                             {

                                                 Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();

                                                 Intent intent= new Intent(register.this,login.class);
                                                 startActivity(intent);
                                             }
                                             else
                                             {

                                                 Toast.makeText(getApplicationContext(),"Registration Failed! Try Again",Toast.LENGTH_LONG).show();

                                             }
                                         }
                                     });

                                }else
                                {
                                    Toast.makeText(getApplicationContext(),"Registration Failed! Try Again",Toast.LENGTH_LONG).show();


                                }
                            }
                        });



            }
        });
    }
}