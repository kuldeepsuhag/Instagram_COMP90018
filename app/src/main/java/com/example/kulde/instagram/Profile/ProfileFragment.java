package com.example.kulde.instagram.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kulde.instagram.LogIn;
import com.example.kulde.instagram.Model.User;
import com.example.kulde.instagram.Model.UserAccountSettings;
import com.example.kulde.instagram.Model.UserSettings;
import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.FirebaseMethods;
import com.example.kulde.instagram.Utils.Navigation;
import com.example.kulde.instagram.Utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment{
    private static final String TAG = "ProfileFragment";

    private static final int ACTIVITY_NUM = 4;

    private TextView mPosts, mFollowers, mFollowing, mDisplayName, mUsername, mWebsite, mDescription;
    private ProgressBar mProgressBar;
    private CircleImageView mProfilePhoto;
    private GridView gridView;
    private Toolbar toolbar;
    private ImageView profileMenu;
    private BottomNavigationView bottomNavigationView;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;


    private Context thisActivity;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mDisplayName = (TextView) view.findViewById(R.id.display_name);
//        Log.d(TAG, "onCreateView() called  " + mDisplayName.getText());
        mUsername = (TextView) view.findViewById(R.id.profileName);
        mWebsite = (TextView) view.findViewById(R.id.website);
        mDescription = (TextView) view.findViewById(R.id.description);
        Log.d(TAG, "onCreateView() called  " + mDescription.getText());
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_image);
        mPosts = (TextView) view.findViewById(R.id.tvPosts);
        mFollowers = (TextView) view.findViewById(R.id.tvFollowers);
        mFollowing = (TextView) view.findViewById(R.id.tvFollowing);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        gridView = (GridView) view.findViewById(R.id.gridView);
        toolbar = (Toolbar) view.findViewById(R.id.profileToolBar);
        profileMenu = (ImageView) view.findViewById(R.id.profileMenu);
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavViewwBar);

        thisActivity = getActivity();
        mFirebaseMethods = new FirebaseMethods(getActivity());
        navigation();
        setupToolbar();
        setupFirebaseAuth();
        return view;
    }

    private void setupToolbar(){
//        Toolbar toolbar = (Toolbar)findViewById(R.id.profileToolBar);
        ((Profile)getActivity()).setSupportActionBar(toolbar);
//        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "onMenuItemClick: ");
                switch(item.getItemId()) {
                    case R.id.profileMenu:
                        Log.d(TAG, "onMenuItemClick: Naviagting to Profile References");
                }
                return false;
            }
        });
//        ImageView profileMenu = (ImageView)findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigating to account Settings");
                Intent intent = new Intent(thisActivity, AccountSettings.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Function Bar Setup
     */
    public void navigation(){

        Navigation.enablenavigation(thisActivity, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    //firebase thing starts here


    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    //somebody signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in" + user.getUid());
                } else{
                    //nobodys here
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database
                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));

                //retrieve images for the user in question
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setProfileWidgets(UserSettings userSettings) {
        //Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getSettings().getUsername());


//        User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + settings.getProfile_photo());

        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, ""); //error happened java.lang.IllegalArgumentException: view must not be null

//        Glide.with(getActivity())
//                .load(settings.getProfile_photo())
//                .into(mProfilePhoto);

        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mPosts.setText(String.valueOf(settings.getPosts()));
        mFollowing.setText(String.valueOf(settings.getFollowing()));
        mFollowers.setText(String.valueOf(settings.getFollowers()));
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
//        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    //firebase thing end here
}
