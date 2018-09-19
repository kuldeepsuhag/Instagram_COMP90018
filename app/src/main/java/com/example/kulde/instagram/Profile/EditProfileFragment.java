package com.example.kulde.instagram.Profile;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

public class EditProfileFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "EditProfileFragment";
    private ImageView mProfilePhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile,container,false);
        mProfilePhoto=(ImageView)view.findViewById(R.id.profile_photo);

        setProfileImage();
        ImageView backarrow = (ImageView)view.findViewById(R.id.backIcon);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigating back to Profile Activity");
                getActivity().finish();
            }
        });
        return view;

    }



    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: Setting Profile Image");
        String imgURL = "https://wallpaperbrowse.com/media/images/3848765-wallpaper-images-download.jpg";
        UniversalImageLoader.setImage(imgURL, mProfilePhoto, null,"");
    }
}
