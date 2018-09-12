package com.example.kulde.instagram.camera.filters;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.widget.ImageView;
import android.widget.SeekBar;

public class BnCFilter {

    SeekBar seekbar;
    static int fb, c = 1;
    public static int BRIGHTNESS = 1;
    public static int CONTRAST = 2;
    private ImageView vi;
    private int mode;

    public BnCFilter(SeekBar seekbar, ImageView vi, int mode) {
        this.seekbar = seekbar;
        this.vi = vi;
        this.mode = mode;
        if (mode == BRIGHTNESS) {
            seekbar.setMax(100);
        }
        else {
            seekbar.setMax(8);
        }
        seekbar.setProgress(1);
        setSeekbarListener();
    }

    private void setSeekbarListener() {

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //set the progress value
                if (mode == BRIGHTNESS)

                    vi.setColorFilter(brightIt(progress));

                if (mode == CONTRAST)
                    vi.setColorFilter(contrastIt(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mode == BRIGHTNESS) {
                    int progress = seekBar.getProgress();
                    fb = progress;
                    vi.setColorFilter(brightIt(seekBar.getProgress()));
                }
                if (mode == CONTRAST) {
                    int progress = seekBar.getProgress();
                    c = progress;
                    vi.setColorFilter(contrastIt(seekBar.getProgress()));
                }

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mode == BRIGHTNESS) {
                    int progress = seekBar.getProgress();
                    fb = progress;
                    vi.setColorFilter(brightIt(seekBar.getProgress()));
                }
                if (mode == CONTRAST) {
                    int progress = seekBar.getProgress();
                    c = progress;
                    vi.setColorFilter(contrastIt(seekBar.getProgress()));
                }
            }
        });

    }

    private ColorMatrixColorFilter brightIt(int fb) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[]{
                c, 0, 0, 0, fb,
                0, c, 0, 0, fb,
                0, 0, c, 0, fb,
                0, 0, 0, 1, 0});

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(cmB);

        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);

        return f;
    }

    private ColorMatrixColorFilter contrastIt(int c) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[]{
                c, 0, 0, 0, fb,
                0, c, 0, 0, fb,
                0, 0, c, 0, fb,
                0, 0, 0, 1, 0});

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(cmB);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);
        return f;
    }


}
