package com.example.kulde.instagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {
    Button btsignin;
    EditText emailid;
    EditText passwordet;
    String email;
    String password;
    TextView signuptext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        TextView signuptext = (TextView)this.findViewById(R.id.signuptext);
        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this,SignUp.class);
                startActivity(intent);
            }
        });
    }
}
