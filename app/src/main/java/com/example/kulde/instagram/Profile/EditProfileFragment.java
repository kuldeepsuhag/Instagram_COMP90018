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

public class EditProfileFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";
    private ImageView mProfilePhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile,container,false);
        mProfilePhoto=(ImageView)view.findViewById(R.id.profile_photo);
        initimageloader();
        setProfileImage();
        return view;

    }

    private void initimageloader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getActivity());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: Setting Profile Image");
        String imgURL = "https://www.facebook.com/photo.php?fbid=1847134018925773&set=a.1387256711580175&type=3&theater";
        UniversalImageLoader.setImage(imgURL, mProfilePhoto, null,"");
    }
}
