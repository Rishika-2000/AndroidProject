package com.example.gasbookingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    ImageView imageView;
    TextView logo,slogan;
    TextInputLayout email,password;
    private FirebaseAuth mAuth;
    Button signup,signin,fp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        signup=(Button)findViewById(R.id.signup);
        signin=(Button)findViewById(R.id.signin);
        fp=(Button)findViewById(R.id.forgotpassword);
        email=(TextInputLayout)findViewById(R.id.emailid);
        password=(TextInputLayout)findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail= new EditText(v.getContext());
                AlertDialog.Builder resetPasswordDialog= new AlertDialog.Builder(v.getContext());
                resetPasswordDialog.setTitle("Reset Password");
                resetPasswordDialog.setMessage("Enter your E-Mail to receive reset link");
                resetPasswordDialog.setView(resetMail);

                resetPasswordDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail=resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Reset link sent successfully",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Error Reset link not sent"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                resetPasswordDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                resetPasswordDialog.create().show();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(login.this,register.class);
                startActivity(intent);

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String email_id=email.getEditText().getText().toString().trim();
             String pass=password.getEditText().getText().toString().trim();
                if(email_id.isEmpty())
                {
                    email.setError("Required");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email_id).matches())
                {
                    email.setError("Provide a valid email");
                    email.requestFocus();
                    return;

                }
                if(pass.isEmpty())
                {
                    password.setError("Required");
                    password.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email_id,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent2= new Intent(login.this,Dashboard.class);
                            startActivity(intent2);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Login Failed! Check Credentials",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });




    }
}