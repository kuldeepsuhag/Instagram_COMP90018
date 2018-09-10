package com.example.kulde.instagram.camera;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.kulde.instagram.R;

import java.io.File;
import java.io.IOException;

import static com.example.kulde.instagram.camera.BitmapFilter.changeBitmapContrastBrightness;


public class CameraFilterActivity extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private static final String LOGGING_TAG = "photo filter";
    private ImageView imageEdit;
    private ImageButton backToTake;
    private ImageButton oldStyle;
    private Bitmap capture;
    private Bitmap originalBitmap;
    private SeekBar brightness;
    private SeekBar contrast;
    private Bitmap tune;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_camera_filter, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        imageEdit = view.findViewById(R.id.picture_view);
        backToTake = view.findViewById(R.id.back);
        oldStyle = view.findViewById(R.id.yellow);
        ImageButton origin = view.findViewById(R.id.origin);
        ImageButton gray = view.findViewById(R.id.black);
        ImageButton invert = view.findViewById(R.id.neon);
        //ImageButton contrast_brightness = view.findViewById(R.id.contrast);
        brightness = view.findViewById(R.id.brightness);
        contrast = view.findViewById(R.id.contrast);

        reCapture_Listener();

        try {
            originalBitmap = setImageView(imageEdit);
            capture  = originalBitmap;

            changeConstrast_and_brightness();
        } catch (IOException e) {
            e.printStackTrace();
        }


        filterListener(imageEdit, gray, BitmapFilter.GRAY_STYLE, originalBitmap);
        filterListener(imageEdit, invert, BitmapFilter.INVERT_STYLE, originalBitmap);
        filterListener(imageEdit, oldStyle, BitmapFilter.OLD_STYLE, originalBitmap);

        origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageEdit.setImageBitmap(originalBitmap);
                contrast.setProgress(5);
                brightness.setProgress(255);
            }
        });


    }

    private void reCapture_Listener(){
        backToTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Fragment fragment:getActivity().getSupportFragmentManager().getFragments()) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
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


    private Bitmap setImageView(ImageView imageEdit) throws IOException {


        String encodedBitmap = getArguments().getString("image");
        int rotationDegrees = getArguments().getInt("rotate");
        byte[] decodedString = Base64.decode(encodedBitmap, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageEdit.setImageBitmap(decodedBitmap);
        imageEdit.setRotation(-rotationDegrees);
        return decodedBitmap;
    }

    private void changeConstrast_and_brightness(){

        contrast.setMax(10);
        brightness.setMax(510);
        contrast.setProgress((5));
        brightness.setProgress((255));
        contrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                Bitmap tune = changeBitmapContrastBrightness(capture, (float)(progress-1),(float)(brightness.getProgress()-255));
                imageEdit.setImageBitmap(tune);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if (progress <= 5) {
                    progress = 1 - progress / 5;
                } else {
                    progress = 1 + (progress / 10) * 9;
                }
                Bitmap tune = changeBitmapContrastBrightness(capture, (float)(contrast.getProgress()-1), (float)(progress-255));
                imageEdit.setImageBitmap(tune);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }


    private Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }


}
