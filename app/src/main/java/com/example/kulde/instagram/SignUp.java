package com.example.kulde.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.security.auth.login.LoginException;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private TextView signintext;
    private EditText email, username, password;
    private Button register;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       // System.out.print("Hello");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        username = (EditText)findViewById(R.id.Etusername);
        email = (EditText)findViewById(R.id.Etemailid);
        password=(EditText)findViewById(R.id.Etpassword);
        register=(Button)findViewById(R.id.btsignup);
        signintext = (TextView)findViewById(R.id.singintv);
        signintext.setOnClickListener(this);
        register.setOnClickListener(this);
    }
    public void onClick(View v) {
        if (v == signintext) {
            Intent intent = new Intent(SignUp.this, LogIn.class);
            startActivity(intent);
        }
        if(v == register){
            userRegister();
        }
    }
    private void userRegister(){
        final String Email = email.getText().toString().trim();
        String Username = username.getText().toString().trim();
        String Password = password.getText().toString().trim();

        if(TextUtils.isEmpty(Email)){
            Toast.makeText(this,"Please Enter Email ID",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Username)){
            Toast.makeText(this,"Please Enter  Username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Password)){
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SignUp.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainPage.class));
                        }
                    }
                });
    }

}
