package com.example.vediocallingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;
    EditText emailid,password,nameid;
    Button loginbtn,signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth=FirebaseAuth.getInstance();

        emailid=findViewById(R.id.emailid);
        password=findViewById(R.id.password);
        loginbtn=findViewById(R.id.loginbtn);
        signupbtn=findViewById(R.id.signupbtn);
        nameid=findViewById(R.id.nameid);
        database=FirebaseFirestore.getInstance();



        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass,name;
                email=emailid.getText().toString();
                pass=password.getText().toString();
                name=nameid.getText().toString();

                User user=new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //success
                            database.collection("User")
                                    .document().set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(Signup.this,LoginActivity.class));
                                }
                            });
                            Toast.makeText(Signup.this,"Account is created",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Signup.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });
    }
}