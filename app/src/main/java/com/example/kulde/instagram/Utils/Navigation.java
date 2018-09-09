package com.example.kulde.instagram.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.kulde.instagram.R;

public class Navigation {
    private static final String TAG = "Navigation";
    public static void enablenavigation(final Context context, BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ic_house:
                        break;
                    case R.id.ic_add:
                        break;
                    case R.id.ic_profile:
                        break;
                    case R.id.ic_search:
                        break;
                    case R.id.ic_like:
                        break;
                }
            }
        });
    }
}
