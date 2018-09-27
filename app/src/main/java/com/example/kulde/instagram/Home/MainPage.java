package com.example.kulde.instagram.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kulde.instagram.LogIn;
import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.Navigation;
import com.example.kulde.instagram.Utils.SectionPagerAdapter;
import com.example.kulde.instagram.Utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nostra13.universalimageloader.core.ImageLoader;

//import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainPage extends AppCompatActivity {
    private static final String TAG = "MainPage";
    private static final int ACTIVITY_NUM = 0;

    private Context mainContext = MainPage.this;

    //firebase thing
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Log.d(TAG, "onCreate: Starting......");
        setupFirebaseAuth();
        initimageloader();
        navigation();
        setupViewpager();

    }

    //firebase thing starts here
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser");
        if(user == null){
            Intent intent = new Intent(mainContext, LogIn.class);
            startActivity(intent);
        }
    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                checkCurrentUser(user);
                if(user != null){
                    //somebody signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in" + user.getUid());
                } else{
                    //nobodys here
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void initimageloader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mainContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    //firebase thing end here

    /**
     * Function Bar Setup
     */
    public void navigation(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavViewwBar);
        Navigation.enablenavigation(MainPage.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
    private void setupViewpager(){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentHome());
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager_container);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.nuhuu);

    }


}
