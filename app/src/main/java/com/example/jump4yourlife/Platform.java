package com.example.jump4yourlife;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Platform extends Sprite {

    public Platform(float x, float y, float vx, float vy, Bitmap bitmap, JumpGame jumpGame, Paint paint, boolean IsActive){
        super(x,y,vx,vy,bitmap,jumpGame,paint,IsActive);
    }

    public void platend(Canvas canvas, Spike spike)
    {
        int alpha =250;
        paint = new Paint();
        paint.setAlpha(alpha);
        if ((spike.y >= canvas.getHeight() - (bitmap.getHeight()/2))) // && (y!=canvas.getHeight()))
        {
            while (alpha != 0) {
                Log.d("alphaaaaa", "" + alpha);
                alpha--;
                paint.setAlpha(alpha);
            }
        }
    }


//    public boolean platend(float y, Canvas canvas, Spike spike)
//    {
//        int alpha =250;
//        paint = new Paint();
//        paint.setAlpha(alpha);
//        if ((spike.y >= canvas.getHeight() - (bitmap.getHeight()/2))) // && (y!=canvas.getHeight()))
//        {
//            while (alpha != 0) {
//                Log.d("alphaaaaa", "" + alpha);
//                alpha--;
//                paint.setAlpha(alpha);
//            }
//            return true;
//        }
//        return false;
//    }

}
