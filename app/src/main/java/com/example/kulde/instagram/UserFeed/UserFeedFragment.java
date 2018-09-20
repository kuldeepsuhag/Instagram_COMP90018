package com.example.kulde.instagram.UserFeed;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.eschao.android.widget.elasticlistview.ElasticListView;
//import com.eschao.android.widget.elasticlistview.LoadFooter;
//import com.eschao.android.widget.elasticlistview.OnLoadListener;
//import com.eschao.android.widget.elasticlistview.OnUpdateListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.kulde.instagram.R;

public class UserFeedFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    public void onUpdate() {
        Log.d(TAG, "ElasticListView: updating list view...");

//        getFollowing();
    }


    public void onLoad() {
        Log.d(TAG, "ElasticListView: loading...");

        // Notify load is done
//        mListView.notifyLoaded();
    }


    //vars
//    private ArrayList<Photo> mPhotos;
//    private ArrayList<Photo> mPaginatedPhotos;
    private ArrayList<String> mFollowing;
    private int recursionIterator = 0;
    //    private ListView mListView;
//    private ElasticListView mListView;
//    private MainFeedListAdapter adapter;
    private int resultsCount = 0;
//    private ArrayList<UserAccountSettings> mUserAccountSettings;
    //    private ArrayList<UserStories> mAllUserStories = new ArrayList<>();
    private JSONArray mMasterStoriesArray;

    private RecyclerView mRecyclerView;
//    public StoriesRecyclerViewAdapter mStoriesAdapter;


    /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  View view = inflater.inflate(R.layout.fragment_userfeed, container, false);
//        mListView = (ElasticListView) view.findViewById(R.id.listView);

        initListViewRefresh();
//        getFollowing();

      //  return view;
    }
    */

    private void initListViewRefresh(){
//        mListView.setHorizontalFadingEdgeEnabled(true);
//        mListView.setAdapter(adapter);
//        mListView.enableLoadFooter(true)
//                .getLoadFooter().setLoadAction(LoadFooter.LoadAction.RELEASE_TO_LOAD);
//        mListView.setOnUpdateListener(this)
//                .setOnLoadListener(this);
//        mListView.requestUpdate();
    }



    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        if (mRecyclerView == null) {
            TextView textView = new TextView(getActivity());
            textView.setText("Stories");
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setTextSize(14);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            textView.setLayoutParams(params);
//            mListView.addHeaderView(textView);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView = new RecyclerView(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
//            mListView.addHeaderView(mRecyclerView);
        }
    }

}