package com.example.kulde.instagram.Home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kulde.instagram.R;

import static com.example.kulde.instagram.R.layout.main_page_fragment;

public class FragmentHome extends Fragment {
    private static final String TAG = "FragmentHome";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_page_fragment, container, false);
        return view;
    }
}
