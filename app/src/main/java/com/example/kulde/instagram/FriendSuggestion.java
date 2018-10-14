package com.example.kulde.instagram;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

public class FriendSuggestion extends AppCompatActivity {
    private static final String TAG = "Friend Suggestion";
    private Context mContext = FriendSuggestion.this;
    private ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_friend_suggestion);
        Log.d(TAG, "onCreate: Starting " + mContext.getString(R.string.friend_suggestion));
        listView = (ListView)findViewById(R.id.friendlist);
        getfriend();

    }
    private void getfriend(){

    }

    private void updateUserlist(){

    }
}
