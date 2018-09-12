package com.example.kulde.instagram.camera;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.kulde.instagram.R;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;


import java.io.IOException;


public class CameraFilterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private static final String LOGGING_TAG = "photo filter";
    private ImageView imageEdit;
    private ImageButton backToTake;

    private Bitmap capture;
    private Bitmap originalBitmap;
    private SeekBar brightness;
    private SeekBar contrast;
    private Bitmap bright;
    private Bitmap contra;

    static
    {
        System.loadLibrary("NativeImageProcessor");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera_filter, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        imageEdit = view.findViewById(R.id.picture_view);
        backToTake = view.findViewById(R.id.back);
        ImageButton oldStyle = view.findViewById(R.id.yellow);
        ImageButton origin = view.findViewById(R.id.origin);
        ImageButton gray = view.findViewById(R.id.black);
        ImageButton invert = view.findViewById(R.id.neon);
        ImageButton lime = view.findViewById(R.id.lime_stur);
        ImageButton night = view.findViewById(R.id.nigth_wh);
        ImageButton awe = view.findViewById(R.id.awe);
        //ImageButton contrast_brightness = view.findViewById(R.id.contrast);
        brightness = view.findViewById(R.id.brightness);
        contrast = view.findViewById(R.id.contrast);

        reCapture_Listener();

        try {
            originalBitmap = setImageView(imageEdit);
            capture  = originalBitmap;

            changeConstrast_and_brightness();

            filterListener(imageEdit, gray, BitmapFilter.GRAY_STYLE, originalBitmap);
            filterListener(imageEdit, invert, BitmapFilter.BLUE_MESS, originalBitmap);
            filterListener(imageEdit, oldStyle, BitmapFilter.OLD_STYLE, originalBitmap);
            filterListener(imageEdit, lime, BitmapFilter.LIME_STUTTER, originalBitmap);
            filterListener(imageEdit, awe, BitmapFilter.AWE_STRUCK, originalBitmap);
            filterListener(imageEdit, night, BitmapFilter.NIGHT_WHIS, originalBitmap);



            origin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    brightness.setProgress(30);
                    contrast.setProgress(1);
                    imageEdit.setImageBitmap(originalBitmap);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }





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

                brightness.setProgress(30);
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


        contrast.setMax(30);
        contrast.setProgress((1));
        contrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            Filter myFilter = new Filter();

            //capture=capture.copy(Bitmap.Config.ARGB_8888, true);
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)

            {
                Bitmap input=capture.copy(Bitmap.Config.ARGB_8888, true);
                if (progress == 0) {
                    progress = 1;
                }
                myFilter.addSubFilter(new ContrastSubfilter(progress));
                Bitmap outputImage = myFilter.processFilter(input);
                //imageEdit.setColorFilter(ColorFilterGenerator.adjustContrast(progress));
                /*
                if (progress <= 5) {
                    progress = 1 - progress / 5;
                } else {
                    progress = 1 + (progress / 10) * 9;
                }
                contra = changeBitmapContrastBrightness(capture, (float)progress,0);
                */
                imageEdit.setImageBitmap(outputImage);

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

        brightness.setMax(60);
        brightness.setProgress(30);

        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            Filter myFilter = new Filter();
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                myFilter.addSubFilter(new BrightnessSubfilter(30));
                progress = progress - 30;
                Bitmap input=capture.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap ouputImage = myFilter.processFilter(input);


                //capture = changeBitmapContrastBrightness(capture, 5.f, (float)progress-255);
                imageEdit.setImageBitmap(ouputImage);
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



}
