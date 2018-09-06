package com.example.kulde.instagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import javax.security.auth.login.LoginException;

public class SignUp extends AppCompatActivity {
    TextView signintext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        TextView signin = (TextView)findViewById(R.id.singintv);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,LogIn.class);
                startActivity(intent);
            }
        });
    }
}
