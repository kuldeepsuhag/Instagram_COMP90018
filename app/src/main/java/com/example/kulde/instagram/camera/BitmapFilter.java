package com.example.kulde.instagram.camera;

import android.graphics.Bitmap;

import com.example.kulde.instagram.camera.filters.GrayFilter;
import com.example.kulde.instagram.camera.filters.InvertFilter;
import com.example.kulde.instagram.camera.filters.OldFilter;

public class BitmapFilter {

    /**
     * filter style id;
     */
    public static final int GRAY_STYLE = 1; // gray scale
    public static final int RELIEF_STYLE = 2; // relief
    public static final int AVERAGE_BLUR_STYLE = 3; // average blur
    public static final int OIL_STYLE = 4; // oil painting
    public static final int NEON_STYLE = 5; // neon
    public static final int PIXELATE_STYLE = 6; // pixelate
    public static final int TV_STYLE = 7; // Old TV
    public static final int INVERT_STYLE = 8; // invert the colors
    public static final int BLOCK_STYLE = 9; // engraving
    public static final int OLD_STYLE = 10; // old photo
    public static final int SHARPEN_STYLE = 11; // sharpen
    public static final int LIGHT_STYLE = 12; // light
    public static final int LOMO_STYLE = 13; // lomo
    public static final int HDR_STYLE = 14; // HDR
    public static final int GAUSSIAN_BLUR_STYLE = 15; // gaussian blur
    public static final int SOFT_GLOW_STYLE = 16; // soft glow
    public static final int SKETCH_STYLE = 17; // sketch style
    public static final int MOTION_BLUR_STYLE = 18; // motion blur
    public static final int GOTHAM_STYLE = 19; // gotham style

    public static final int TOTAL_FILTER_NUM = GOTHAM_STYLE;

    /**
     * change bitmap filter style
     * @param bitmap
     * @param styleNo, filter sytle id
     */
    public static Bitmap changeStyle(Bitmap bitmap, int styleNo, Object... options) {
        if (styleNo == GRAY_STYLE) {
            return GrayFilter.changeToGray(bitmap);
        } else if (styleNo == INVERT_STYLE) {
            return InvertFilter.chageToInvert(bitmap);
        } else if (styleNo == OLD_STYLE) {
            return OldFilter.changeToOld(bitmap);
        }
        return bitmap;
    }
}
