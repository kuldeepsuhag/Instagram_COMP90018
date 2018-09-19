package com.example.kulde.instagram.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kulde.instagram.R;

import java.util.ArrayList;

public class AccountSettings extends AppCompatActivity {

    private static final String TAG = "AccountSettings act";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.account_settings_main);
            Log.d(TAG, "onCreate started");
            createList();
    }

    private void createList(){
        ListView menuList = findViewById(R.id.listOptionSettings);
        ArrayList<String> aList = new ArrayList<>();
        aList.add("Edit your profile");
        aList.add("Sign out");

        ArrayAdapter adapter = new ArrayAdapter(AccountSettings.this, android.R.layout.simple_list_item_1, aList);
        menuList.setAdapter(adapter);
    }
}
