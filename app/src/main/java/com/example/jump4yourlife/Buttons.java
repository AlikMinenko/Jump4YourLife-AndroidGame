package com.example.jump4yourlife;

import android.graphics.Bitmap;
import android.graphics.Paint;

public class Buttons extends Sprite {

    //private boolean IsPressed=false;

    public Buttons(float x, float y, float vx, float vy, Bitmap bitmap, JumpGame jumpGame, Paint paint, boolean IsActive){
        super(x,y,vx,vy,bitmap,jumpGame,paint,IsActive);
    }


    public boolean DidUserTouchMe(float x,float y)
    {
        if(IsActive)
        {
            if((x >= this.x && x<= this.x +this.bitmap.getWidth()) && (y >= this.y && y<= this.y +this.bitmap.getHeight()) ){
                return true;
            }
        }
        return  false;
    }

}
