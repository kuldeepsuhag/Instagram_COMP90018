package com.example.kulde.instagram.UserFeed;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.example.kulde.instagram.LogIn;
import com.example.kulde.instagram.Model.Comment;
import com.example.kulde.instagram.Model.Likes;
import com.example.kulde.instagram.Model.Notice;
import com.example.kulde.instagram.Model.Photo;
import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.GridImageAdapter;
import com.example.kulde.instagram.Utils.Like;
import com.example.kulde.instagram.Utils.Navigation;
import com.example.kulde.instagram.Utils.NotificationFeedListAdapter;
import com.example.kulde.instagram.Utils.SectionPagerAdapter;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.kulde.instagram.R;

public class MyFeedFragment extends Fragment{
    private static final String TAG = "MyFeedFragment";
    private static final int ACTIVITY_NUM = 3;
    private Context mContext;
    private BottomNavigationView bottomNavigationView;
    private ListView listView;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("message");
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private NotificationFeedListAdapter adapter;
    private HashMap<String,String> nameList =new HashMap<String,String>();
    private HashMap<Integer,String> idList = new HashMap<Integer,String>();
    private HashMap<Integer,String> dateList = new HashMap<Integer,String>();
    private HashMap<Integer,String> typeList = new HashMap<Integer,String>();

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;

    //widgets
    private ViewPager viewPager;

    public void onUpdate() {
        Log.d(TAG, "ElasticListView: updating list view...");

//        getFollowing();
    }


    public void onLoad() {
        Log.d(TAG, "ElasticListView: loading...");

        // Notify load is done
//        mListView.notifyLoaded();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Userfeed activity = (Userfeed) getActivity();
//        nameList = activity.getNameList();
        View view = inflater.inflate(R.layout.fragment_userfeed, container, false);
        listView = (ListView) view.findViewById(R.id.listview);


//        setupFirebaseAuth();
        setupListview();
        return view;
    }

    private void setupListview(){
        Log.d(TAG, "setupListview: Settig up");

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final ArrayList<Comment> comments = new ArrayList<>();
        final ArrayList<Notice> notices = new ArrayList<>();
        final ArrayList<Likes> likes = new ArrayList<>();

        Query query = databaseReference
                .child(getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot singlesnapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot dsnapshot: singlesnapshot.child(getString(R.string.field_comments)).getChildren()){
                        Notice notice = new Notice();
                        notice.setUser_id_to(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                        notice.setAction("commented");
                        notice.setDate_created(dsnapshot.getValue(Comment.class).getDate_created());
                        notice.setUser_id_from(dsnapshot.getValue(Comment.class).getUser_id());
                        comments.add(dsnapshot.getValue(Comment.class));
                        notices.add(notice);
                    }

                    for(DataSnapshot dsnapshot: singlesnapshot.child(getString(R.string.field_likes)).getChildren()){
                        Notice notice = new Notice();
                        notice.setUser_id_to(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                        notice.setAction("liked");
                        notice.setDate_created(dsnapshot.getValue(Likes.class).getDate_created());
                        notice.setUser_id_from(dsnapshot.getValue(Likes.class).getUser_id());
                        likes.add(dsnapshot.getValue(Likes.class));
                        notices.add(notice);
                    }
                }

//                for (Map.Entry<Integer,String> userid: idList.entrySet()){
//                    Log.d(TAG, "setupListview: display " + dateList);
//                    comments.add(nameList.get(userid.getValue()) +" just commented on your photo. ");
//                }

                Log.d(TAG, "setupListview: display " + notices.size());
                adapter = new NotificationFeedListAdapter(getActivity(),R.layout.list_item_layout,notices);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Query Cancelled");
            }
        });
    }

    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(mContext, LogIn.class);
            startActivity(intent);
        }
    }
//
//    private void setupFirebaseAuth() {
//        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
//        mAuth = FirebaseAuth.getInstance();
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myRef = mFirebaseDatabase.getReference();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if(mAuthListener != null){
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
}
