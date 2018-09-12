package com.example.kulde.instagram.camera;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;


import com.example.kulde.instagram.camera.filters.GrayFilter;

import com.example.kulde.instagram.camera.filters.OldFilter;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;


public class BitmapFilter {

    /**
     * filter style id;
     */
    public static final int GRAY_STYLE = 1; // gray scale

    public static final int BLUE_MESS = 2; // invert the colors

    public static final int OLD_STYLE = 3; // old photo

    public static final int AWE_STRUCK = 4;

    public static final int LIME_STUTTER = 5;

    public static final int NIGHT_WHIS = 6;

    static
    {
        System.loadLibrary("NativeImageProcessor");
    }




    /**
     * change bitmap filter style
     * @param bitmap
     * @param styleNo, filter sytle id
     */
    public static Bitmap changeStyle(Bitmap bitmap, int styleNo, Object... options) {
        if (styleNo == GRAY_STYLE) {
            return GrayFilter.changeToGray(bitmap);
        }

        else if (styleNo == BLUE_MESS) {

            Filter fooFilter = SampleFilters.getBlueMessFilter();

            bitmap=bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap ouputImage = fooFilter.processFilter(bitmap);
            return ouputImage;
           // return InvertFilter.chageToInvert(bitmap);
        }

        else if (styleNo == OLD_STYLE) {
            return OldFilter.changeToOld(bitmap);
        }

        else if (styleNo == AWE_STRUCK) {
            Filter fooFilter = SampleFilters.getAweStruckVibeFilter();

            bitmap=bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap ouputImage = fooFilter.processFilter(bitmap);
            return ouputImage;
        }

        else if (styleNo == LIME_STUTTER) {
            Filter fooFilter = SampleFilters.getLimeStutterFilter();
            bitmap=bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap ouputImage = fooFilter.processFilter(bitmap);
            return ouputImage;

        }

        else if (styleNo == NIGHT_WHIS) {
            Filter fooFilter = SampleFilters.getNightWhisperFilter();
            bitmap=bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap ouputImage = fooFilter.processFilter(bitmap);
            return ouputImage;
        }

        return bitmap;
    }

    public static Bitmap changeBitmapContrastBrightness(Bitmap bitmap, float contrast, float brightness){

        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return ret;
    }
}
