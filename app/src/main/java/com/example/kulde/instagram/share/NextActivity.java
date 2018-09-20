package com.example.kulde.instagram.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kulde.instagram.Home.MainPage;
import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.CommResources;
import com.example.kulde.instagram.Utils.FirebaseInteraction;
import com.example.kulde.instagram.Utils.FirebaseMethods;
import com.example.kulde.instagram.Utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NextActivity extends AppCompatActivity {

    private static final String TAG = "NextActivity";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    //widgets
    private EditText mCaption;

    //vars
    private String mAppend = "file:/";
    private int imageCount = 0;
    private String imgUrl;
    private Bitmap bitmap;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        mFirebaseMethods = new FirebaseMethods(NextActivity.this);
        mCaption = (EditText) findViewById(R.id.caption);

        setupFirebaseAuth();

        ImageView backArrow = (ImageView) findViewById(R.id.back);
//        backArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: closing the activity");
//                finish();
//            }
//        });


        TextView share = (TextView) findViewById(R.id.tvShare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to the final share screen.");

                //upload the image to firebase
                Toast.makeText(NextActivity.this, "Attempting to upload new photo", Toast.LENGTH_SHORT).show();
                String caption = mCaption.getText().toString();

                // upload a photo
                FirebaseInteraction uploadTask = new FirebaseInteraction(NextActivity.this, CommResources.edit_template,caption);
                uploadTask.execute();

                //go back to main
                Intent intent = new Intent(NextActivity.this, MainPage.class);
                startActivity(intent);
            }
        });

        setImage();
    }

    private void setImage(){
        Bitmap bmp;
        if(CommResources.edit_template != null){
            bmp = CommResources.edit_template;
        }else{
            bmp = CommResources.photoFinishBitmap;
        }
        intent = getIntent();
        ImageView image = (ImageView) findViewById(R.id.imageShare);
        image.setImageBitmap(bmp);
        image.setRotation(-CommResources.rotationdegree);

//        imgUrl = intent.getStringExtra("selected_image");
//        Log.d(TAG, "setImage: got new image url: " + imgUrl);
//        UniversalImageLoader.setImage(imgUrl, image, null, mAppend);
    }

    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        Log.d(TAG, "onDataChange: image count: " + imageCount);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }
}
