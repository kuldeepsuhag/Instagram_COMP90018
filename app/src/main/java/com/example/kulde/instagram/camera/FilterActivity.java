package com.example.kulde.instagram.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.kulde.instagram.R;
import com.example.kulde.instagram.Utils.CommResources;

public class FilterActivity extends AppCompatActivity {

    private ImageView imageEdit;
    private Bitmap bitmapEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        imageEdit = findViewById(R.id.picture_view);
        // initial image edit studio desktop
        bitmapEdit = setImageEdit();

        // initial filter stylish module
        setFilterListener();

        // initial re-take photo module
        reCapture_Listener();


    }

    private void refresh(ImageButton origin){
        // strip off all filters and go back to original style
        origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // brightness.setProgress(30);
                // contrast.setProgress(1);
                imageEdit.setImageBitmap(bitmapEdit);
            }
        });
    }

    private void setFilterListener(){
        ImageButton oldStyle = findViewById(R.id.yellow);
        ImageButton origin = findViewById(R.id.origin);
        ImageButton gray = findViewById(R.id.black);
        ImageButton invert = findViewById(R.id.neon);
        ImageButton lime = findViewById(R.id.lime_stur);
        ImageButton night = findViewById(R.id.nigth_wh);
        ImageButton awe = findViewById(R.id.awe);

        filterListener(imageEdit, gray, BitmapFilter.GRAY_STYLE, bitmapEdit);
        filterListener(imageEdit, invert, BitmapFilter.BLUE_MESS, bitmapEdit);
        filterListener(imageEdit, oldStyle, BitmapFilter.OLD_STYLE, bitmapEdit);
        filterListener(imageEdit, lime, BitmapFilter.LIME_STUTTER, bitmapEdit);
        filterListener(imageEdit, awe, BitmapFilter.AWE_STRUCK, bitmapEdit);
        filterListener(imageEdit, night, BitmapFilter.NIGHT_WHIS, bitmapEdit);

        refresh(origin);

    }

    private void filterListener(final ImageView image, final ImageButton button, final int styleNo, final Bitmap originBitmap){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommResources.edit_template = BitmapFilter.changeStyle(originBitmap, styleNo);
                image.setImageBitmap(CommResources.edit_template);
            }
        });

    }

    private void reCapture_Listener(){
        ImageButton backToTake = findViewById(R.id.back);
        backToTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to take photo again
                Intent intent = new Intent(FilterActivity.this, TakePhotoActivity.class);
                startActivity(intent);
            }
        });
    }



    private Bitmap setImageEdit(){
        Bitmap decodedBitmap = CommResources.photoFinishBitmap;
        int rotationDegrees = CommResources.rotationdegree;
        imageEdit.setImageBitmap(decodedBitmap);
        imageEdit.setRotation(-rotationDegrees);
        return decodedBitmap;
    }
}
