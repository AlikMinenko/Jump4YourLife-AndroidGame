package com.example.jump4yourlife;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Spike extends Sprite {

    public Spike(float x, float y, float vx, float vy, Bitmap bitmap, JumpGame jumpGame, Paint paint, boolean IsActive){
        super(x,y,vx,vy,bitmap,jumpGame,paint,IsActive);
    }

    public void spikeend(Canvas canvas,Platform platform)
    {
        int alpha =250;
        paint = new Paint();
        paint.setAlpha(alpha);
        if((this.y >= canvas.getHeight() - (platform.bitmap.getHeight()/2)))
        {
            while (alpha != 0) {
                alpha--;
                paint.setAlpha(alpha);
            }
        }
    }
}
