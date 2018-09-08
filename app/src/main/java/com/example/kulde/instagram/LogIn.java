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

import org.w3c.dom.Text;


public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private Button signin;
    private EditText email;
    private EditText password;
    //private String email;
    //private String password;
    private TextView signup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainPage.class));

        }
        progressDialog = new ProgressDialog(this);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.passwordet);
        signup = findViewById(R.id.signuptext);
        signin = findViewById(R.id.btsignin);
        signup.setOnClickListener(this);
        signin.setOnClickListener(this);
    }

    //TextView signuptext = (TextView)this.findViewById(R.id.signuptext);
    public void onClick(View v) {
        if (v == signup) {
            Intent intent = new Intent(LogIn.this, SignUp.class);
            startActivity(intent);
        }
        if(v == signin){
            userLogin();
        }
    }
    private void userLogin(){
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        if(TextUtils.isEmpty(Email)){
            Toast.makeText(this,"Please Enter a valid Mail ID",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Password)){
            Toast.makeText(this,"Please Enter a password",Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Login.....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //Toast.makeText(this,"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                        }
                        else{
                            Toast.makeText(LogIn.this,"Wrong Email ID and Password",Toast.LENGTH_SHORT).show();
                        }

                    } });
    }
}