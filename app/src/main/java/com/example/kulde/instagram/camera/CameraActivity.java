package com.example.kulde.instagram.camera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.kulde.instagram.R;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CameraFragment.newInstance())
                    .commit();
        }

    }
}
