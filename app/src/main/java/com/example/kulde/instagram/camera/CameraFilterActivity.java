package com.example.kulde.instagram.camera;

import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.kulde.instagram.MainPage;
import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.CommResources;
import com.example.kulde.instagram.camera.filters.BnCFilter;


import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class CameraFilterActivity extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match

    private static final String LOGGING_TAG = "photo filter";
    private ImageView imageEdit;
    private ImageButton backToTake;

    private Bitmap capture;
    private Bitmap originalBitmap;

    private LinearLayout editPanel;



    static
    {
        System.loadLibrary("NativeImageProcessor");
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_filter);

        imageEdit = findViewById(R.id.picture_view);
        backToTake = findViewById(R.id.back);
        ImageButton oldStyle = findViewById(R.id.yellow);
        ImageButton origin = findViewById(R.id.origin);
        ImageButton gray = findViewById(R.id.black);
        ImageButton invert = findViewById(R.id.neon);
        ImageButton lime = findViewById(R.id.lime_stur);
        ImageButton night = findViewById(R.id.nigth_wh);
        ImageButton awe = findViewById(R.id.awe);



        //reCapture_Listener();


        //originalBitmap = setImageView(imageEdit);
        //capture  = originalBitmap;

            //changeConstrast_and_brightness();
            /*
            filterListener(imageEdit, gray, BitmapFilter.GRAY_STYLE, originalBitmap);
            filterListener(imageEdit, invert, BitmapFilter.BLUE_MESS, originalBitmap);
            filterListener(imageEdit, oldStyle, BitmapFilter.OLD_STYLE, originalBitmap);
            filterListener(imageEdit, lime, BitmapFilter.LIME_STUTTER, originalBitmap);
            filterListener(imageEdit, awe, BitmapFilter.AWE_STRUCK, originalBitmap);
            filterListener(imageEdit, night, BitmapFilter.NIGHT_WHIS, originalBitmap);

            origin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   // brightness.setProgress(30);
                   // contrast.setProgress(1);
                    imageEdit.setImageBitmap(originalBitmap);
                }
            });
            */


    }


    private void reCapture_Listener(){
        backToTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to take photo again
                Intent intent = new Intent(CameraFilterActivity.this, TakePhotoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void filterListener(final ImageView image, final ImageButton button, final int styleNo, final Bitmap originBitmap){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                capture = BitmapFilter.changeStyle(originBitmap, styleNo);
                image.setImageBitmap(capture);


            }
        });

    }


    private Bitmap setImageView(ImageView imageEdit) {
        Bitmap bmp;

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageEdit.setImageBitmap(bmp);
        return bmp;
    }



}
