package com.example.kulde.instagram;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class FriendSuggestion extends AppCompatActivity {
    private static final String TAG = "Friend Suggestion";
    private Context mContext = FriendSuggestion.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_friend_suggestion);
        Log.d(TAG, "onCreate: Starting......");

    }
}
