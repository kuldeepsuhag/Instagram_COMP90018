package com.example.kulde.instagram.camera;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;


import com.example.kulde.instagram.camera.filters.GrayFilter;

import com.example.kulde.instagram.camera.filters.InvertFilter;

import com.example.kulde.instagram.camera.filters.OldFilter;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;


public class BitmapFilter {

    /**
     * filter style id;
     */
    public static final int GRAY_STYLE = 1; // gray scale

    public static final int INVERT_STYLE = 2; // invert the colors

    public static final int OLD_STYLE = 3; // old photo

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

        else if (styleNo == INVERT_STYLE) {

            Filter fooFilter = SampleFilters.getBlueMessFilter();

            bitmap=bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap ouputImage = fooFilter.processFilter(bitmap);
            return ouputImage;
           // return InvertFilter.chageToInvert(bitmap);
        }

        else if (styleNo == OLD_STYLE) {
            return OldFilter.changeToOld(bitmap);
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
