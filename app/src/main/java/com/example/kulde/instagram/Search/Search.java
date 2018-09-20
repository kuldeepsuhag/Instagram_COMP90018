package com.example.kulde.instagram.Search;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.example.kulde.instagram.Model.User;
import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.Navigation;

import java.util.List;

public class Search extends AppCompatActivity{
    private static final String TAG = "Main Activity";
    private static final int ACTIVITY_NUM = 2;

    private EditText mSearchParam;
    private ListView mListView;

    private List<User> mUserList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search);
        Log.d(TAG, "onCreate: Starting......");
        hidesoftkeyboard();
        navigation();
    }

    private void searchforMatch(String keyword){
        Log.d(TAG, "searchforMatch: Searching for a match" + keyword);
        mUserList.clear();
        if(keyword.length() != 0){
            mUserList.clear();

        }else{
            mUserList.clear();
        }

    }

    private void hidesoftkeyboard(){
        if(getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }
    }

    /**
     * Function Bar Setup
     */
    public void navigation(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavViewwBar);
        Navigation.enablenavigation(Search.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
