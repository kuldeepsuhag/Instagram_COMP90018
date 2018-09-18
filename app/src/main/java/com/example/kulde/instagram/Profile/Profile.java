package com.example.kulde.instagram.Profile;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.Navigation;

//import static com.example.kulde.instagram.R.menu.profile_menu;

public class Profile extends AppCompatActivity{
    private static final String TAG = "Profile Activity";
    private static final int ACTIVITY_NUM = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: Activity Profile Starting......");
        //navigation();
        setupToolbar();
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);
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
    }

    /**
     * Function Bar Setup
     */
    public void navigation(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavViewwBar);
        Navigation.enablenavigation(Profile.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
