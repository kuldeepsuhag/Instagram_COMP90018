package com.example.kulde.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kulde.instagram.Model.User;
import com.example.kulde.instagram.Model.UserAccountSetting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private TextView signintext;
    private EditText email, username, password;
    private Button register;
    private ProgressDialog progressDialog;
//    private FirebaseAuth firebaseAuth;

    private static final String TAG = "Signup Activity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase fbaseDB;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       // System.out.print("Hello");
//        firebaseAuth = FirebaseAuth.getInstance();
        setupFirebaseAuth();
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
            finish();
        }
        if(v == register){
            userRegister();
        }
    }

    private boolean checkUsernamExists(String username, DataSnapshot snap){
        Log.d(TAG, "check username if " + username + " is exists");
        User user = new User();

        for(DataSnapshot ds: snap.child(mAuth.getCurrentUser().getUid()).getChildren()){
            user.setUsername(ds.getValue(User.class).getUsername());
            if(user.getUsername().equals(username)) return true;
        }
        return false;
    }

    //firebase thing starts here

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth");
        mAuth = FirebaseAuth.getInstance();
        fbaseDB = FirebaseDatabase.getInstance();
        dbRef = fbaseDB.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    //somebody signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in" + user.getUid());
                    dbRef.addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(checkUsernamExists(username.getText().toString(), dataSnapshot)){
                                        Toast.makeText(SignUp.this,"Signup SUCCESSFUL",Toast.LENGTH_SHORT).show();
                                    } else{

                                        mAuth.signOut();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            }
                    );
                } else{
                    //nobodys here
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    //firebase thing end here

    private void userRegister(){
        String Email = email.getText().toString().trim();
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

        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SignUp.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(),LogIn.class));
//                            finish();
                        } else{
                            return;
                        }
                    }
                });

        User user = new User(mAuth.getCurrentUser().getUid(), 1, Email, Username);
        dbRef.child("users")
                .child(mAuth.getCurrentUser().getUid())
                .setValue(user);

        UserAccountSetting settings = new UserAccountSetting(
                "",
                "",
                0,
                0,
                0,
                "",
                Username);
        dbRef.child("user_account_setting").child(mAuth.getCurrentUser().getUid()).setValue(settings);


    }

}
