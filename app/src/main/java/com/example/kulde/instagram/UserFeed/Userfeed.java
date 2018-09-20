package com.example.kulde.instagram.UserFeed;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kulde.instagram.Home.FragmentHome;
import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.Navigation;
import com.example.kulde.instagram.Utils.SectionPagerAdapter;

public class Userfeed extends AppCompatActivity{

    private static final String TAG = "Feed Activity";
    private static final int ACTIVITY_NUM = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_userfeed);
        Log.d(TAG, "onCreate: Starting feed activity.");
        navigation();
        setupViewPager();
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

    private void setupViewPager(){
        SectionPagerAdapter adapter =  new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserFeedFragment());
//        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager_container);
//        viewPager.setAdapter(adapter);
//        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setIcon(R.drawable.nuhuu);

    }
}
