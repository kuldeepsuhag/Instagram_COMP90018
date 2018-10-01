package com.example.kulde.instagram.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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


import com.example.kulde.instagram.Model.Photo;
import com.example.kulde.instagram.Model.User;
import com.example.kulde.instagram.Model.UserAccountSettings;
import com.example.kulde.instagram.Model.UserSettings;
import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.FirebaseMethods;
import com.example.kulde.instagram.Utils.GridImageAdapter;
import com.example.kulde.instagram.Utils.Navigation;
import com.example.kulde.instagram.Utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private TextView mPosts, mFollowers, mFollowing, mDisplayname, mUsername, mWebsite, mDescription,editprofile;
    private ProgressBar mProgressbar;
    private CircleImageView mProfilephoto;
    private GridView gridView;
    private Toolbar toolbar;
    private ImageView profileMenu;
    private BottomNavigationView bottomNavigationView;
    private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 3;
    private Context mContext;
    private FirebaseMethods mFirebaseMethods;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebasedatabase;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container,false);
        mDisplayname = (TextView)view.findViewById(R.id.display_name);
        mUsername = (TextView)view.findViewById(R.id.profileName);
        mWebsite = (TextView)view.findViewById(R.id.website);
        mDescription = (TextView)view.findViewById(R.id.description);
        mProfilephoto = (CircleImageView) view.findViewById(R.id.profile_image);
        mPosts = (TextView)view.findViewById(R.id.tvPosts);
        mFollowers = (TextView)view.findViewById(R.id.tvFollowers);
        mFollowing = (TextView)view.findViewById(R.id.tvFollowing);
        mProgressbar = (ProgressBar)view.findViewById(R.id.progressBar);
        gridView = (GridView)view.findViewById(R.id.gridView);
        toolbar = (Toolbar) view.findViewById(R.id.profileToolBar);
        profileMenu = (ImageView)view.findViewById(R.id.profileMenu);
        bottomNavigationView =(BottomNavigationView)view.findViewById(R.id.bottomNavViewwBar);
        mContext = getActivity();
        mFirebaseMethods = new FirebaseMethods(getActivity());
        TextView editProfile = (TextView)view.findViewById(R.id.textEditProfile);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to " + mContext.getString(R.string.edit_profile));
                Intent intent = new Intent(getActivity(), AccountSettings.class);
                intent.putExtra(getString(R.string.calling_activity), getString(R.string.profileactivity));
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });


        Log.d(TAG, "onCreateView: stared");
        navigation();
        setupToolbar();
        setupFirebaseAuth();
        setupGridview();
        return view;

    }

    private void setupGridview(){
        Log.d(TAG, "setupGridview: Settig up Image Grid");
        final ArrayList<Photo> photos = new ArrayList<>();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference
                .child(getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singlesnapshot : dataSnapshot.getChildren()){
                    photos.add(singlesnapshot.getValue(Photo.class));
                }
                Log.d(TAG, "onDataChange: Photos retrieved, Setting up Image Grid");
                int gridwidth = getResources().getDisplayMetrics().widthPixels;
                int imageWidth = gridwidth/NUM_GRID_COLUMNS;
                gridView.setColumnWidth(imageWidth);
                ArrayList<String> imgUrls = new ArrayList<>();
                for(int i = 0; i<photos.size(); i++){
                    imgUrls.add(photos.get(i).getImage_path());
                }
                GridImageAdapter adapter = new GridImageAdapter(getActivity(),R.layout.layout_grid_imageview,"",imgUrls);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Query Cancelled");
            }
        });
    }
    private void setProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database" + userSettings.toString());
        User user = userSettings.getUser();
        UserAccountSettings settings =  userSettings.getSettings();
        UniversalImageLoader.setImage(settings.getProfile_photo(),mProfilephoto,null,"");
        mDisplayname.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mPosts.setText(String.valueOf(settings.getPosts()));
        mFollowing.setText(String.valueOf(settings.getFollowing()));
        mFollowers.setText(String.valueOf(settings.getFollowers()));
        mProgressbar.setVisibility(View.GONE);


    }

    private void setupToolbar() {
        ((Profile)getActivity()).setSupportActionBar(toolbar);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigting to account setting");

                Intent intent = new Intent(mContext, AccountSettings.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
    }
        /**
     * Function Bar Setup
     */
    public void navigation(){
        Navigation.enablenavigation(mContext,getActivity(), bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth");
        mAuth = FirebaseAuth.getInstance();
        mFirebasedatabase = FirebaseDatabase.getInstance();
        myRef = mFirebasedatabase.getReference();
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
                //retrieve User Information from database
                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));

                //retrieve Images for the users
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

