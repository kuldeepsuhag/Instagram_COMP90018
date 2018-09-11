package com.example.kulde.instagram.share;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kulde.instagram.R;
import com.example.kulde.instagram.camera.TakePhotoActivity;

public class PhotoFragment extends Fragment {
    private static final String TAG = "PhotoFragment";

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_photo,container,false);
        Log.d(TAG,"onCreateView:started.");

        Button openCam = (Button) view.findViewById(R.id.btnLaunchCamera);
        openCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Move to TakePhotoActivity.");
                Intent intent = new Intent(getActivity(), TakePhotoActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
