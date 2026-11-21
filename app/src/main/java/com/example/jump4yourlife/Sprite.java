package com.example.jump4yourlife;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Sprite {

    float x,y; // the coordinates of the object (x and y)
    float vx,vy; // the speed of the object (vx and vy)
    Bitmap bitmap; // the bitmap of the object
    JumpGame jumpGame; // the game where the object will be drawn
    Paint paint; // the paint of the object
    boolean IsActive; // a variable type boolean that tells if an object is active

    public Sprite(float x, float y, float vx, float vy, Bitmap bitmap, JumpGame jumpGame,Paint paint,boolean IsActive) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.bitmap = bitmap;
        this.jumpGame = jumpGame;
        this.paint = paint;
        this.IsActive =IsActive;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public JumpGame getJumpGame() {
        return jumpGame;
    }

    public void setJumpGame(JumpGame jumpGame) {
        this.jumpGame = jumpGame;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean isactive) {
        IsActive = isactive;
    }

    public synchronized void Draw(Canvas canvas){
        canvas.drawBitmap(bitmap,x,y,paint);
    }

}
