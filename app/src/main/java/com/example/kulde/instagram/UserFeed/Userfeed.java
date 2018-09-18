package com.example.kulde.instagram.UserFeed;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.Navigation;

public class Userfeed extends AppCompatActivity{
    private static final String TAG = "Main Activity";
    private static final int ACTIVITY_NUM = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Log.d(TAG, "onCreate: Starting......");
        navigation();
    }

    /**
     * Function Bar Setup
     */
    public void navigation(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavViewwBar);
        Navigation.enablenavigation(Userfeed.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
