package com.example.jump4yourlife;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.List;

public class Player extends Sprite {

    boolean StopJump = true; // a variable type boolean that stops the jump of the player
    boolean StopRight = true; // a variable type boolean that stops going right for the player
    boolean StopLeft = true; // a variable type boolean that stops going left for the player
    int Currentplat = -1; // the current platform that the player is standing on

    public Player(float x, float y, float vx, float vy, Bitmap bitmap, JumpGame jumpGame, Paint paint, boolean IsActive, boolean StopJump, boolean StopRight, boolean StopLeft, int Currentplat) {
        super(x, y, vx, vy, bitmap, jumpGame, paint, IsActive);

        this.StopJump = StopJump;
        this.StopRight = StopRight;
        this.StopLeft = StopLeft;
        this.Currentplat = Currentplat;
    }

    public boolean isStopJump() {
        return StopJump;
    }

    public void setStopJump(boolean stopJump) {
        StopJump = stopJump;
    }

    public boolean isStopRight() {
        return StopRight;
    }

    public void setStopRight(boolean stopRight) {
        StopRight = stopRight;
    }

    public boolean isStopLeft() {
        return StopLeft;
    }

    public void setStopLeft(boolean stopLeft) {
        StopLeft = stopLeft;
    }

    public int getCurrentplat() {
        return Currentplat;
    }

    public void setCurrentplat(int currentplat) {
        Currentplat = currentplat;
    }


    public void Jump(List<Platform> platforms, int floor) {
        if (IsActive) {
            if (!StopJump) {
                if (vy < 0) //פחמז
                {
                    //if(y<=100){
                    //    vy=0;
                    //}
                    y += vy;
                    vy += 5;
//                    for(int i=0;i<platforms.size();i++)     // For now doesnt work
//                    {
//                        if (((y <= platforms.get(i).getY() + platforms.get(i).getBitmap().getHeight()-20 && y>=platforms.get(i).getY() + platforms.get(i).getBitmap().getHeight() -65) && (x >= platforms.get(i).getX()-bitmap.getWidth()+35 && x <= platforms.get(i).getX() + platforms.get(i).getBitmap().getWidth()-50)) && platforms.get(i).isActive())
//                        {
//                            if(vy==-65 && Currentplat != 0)
//                                vy=65;
//                            if(vy==-45 && Currentplat != 1)
//                                vy=45;
                    //vy=0;
//                        }
                    //                   }
                }
                if (vy >= 0)//עחצמ
                {
                    //Log.d("y",""+y);
                    y += vy;
                    vy += 5;
                }

            }
            if (vy > 95 && (Currentplat == 0 || Currentplat == -1)) {
                y = floor;
            }
            if (y == floor) {
                StopJump = true;
                vy = -70;
                //vy = -1 * vy;
            }
            if (Currentplat == 4 && y < platforms.get(0).y && y > platforms.get(0).y - bitmap.getHeight() && x >= platforms.get(0).x && x <= platforms.get(0).x + platforms.get(0).bitmap.getWidth() - 20) {
                y = platforms.get(0).getY() - bitmap.getHeight() + 60;
//                StopJump=true;
//                vy=35;
            }
            if (OnPlatform(platforms) == 1) {
                StopJump = true;
                //Log.d("vy",""+vy);
                vy = -vy;
                if (Currentplat == 0) {
                    vy = 35;
                }
                //Log.d("vy",""+vy);
            }
            if (OnPlatform(platforms) == -1) {
                StopJump = false;
            }
            Log.d("vyonly", "" + vy);
            Log.d("Currentplat", "" + Currentplat);
        }
    }

    public int OnPlatform(List<Platform> platforms) {
        for (int i = 0; i < platforms.size(); i++) {
//            if(((y > platforms.get(i).getY()-200 && y<platforms.get(i).getY() +100) && (x >= platforms.get(i).getX()-bitmap.getWidth()+35 && x <= platforms.get(i).getX() + platforms.get(i).getBitmap().getWidth()-50)) && platforms.get(i).IsActive == true)
//            {
//                if(vy<0){
//                    vy=-vy;
//                }
//                Currentplat=i;
//                Log.d("i",""+i);
//                return 1;
//            }
            if (y == platforms.get(i).getY() - bitmap.getHeight() + 60 && (x >= platforms.get(i).getX() - bitmap.getWidth() + 35 && x <= platforms.get(i).getX() + platforms.get(i).getBitmap().getWidth() - 50)) {
                if (vy < 0) {
                    vy = -vy; //-739=X-201+50
                }
                Currentplat = i;
                Log.d("iforever", "" + i);
                return 1;
            }
            if ((y > platforms.get(i).getY() - 200 && y < platforms.get(i).getY() + 100) && (x <= platforms.get(i).getX() - bitmap.getWidth() + 35 || x >= platforms.get(i).getX() + platforms.get(i).getBitmap().getWidth() - 50)) {
                return -1;
            }
        }
        return 0;
    }


    public void Right(List<Platform> platforms) {
        if (IsActive) {
            //for(int i =0;i<platforms.size();i++)    // for now not needed
            //{
            if (Currentplat != -1) {
                if ((y > platforms.get(Currentplat).getY() - bitmap.getHeight() + 60 && y <= platforms.get(Currentplat).getY() + platforms.get(Currentplat).getBitmap().getHeight()) && (x + bitmap.getWidth() >= platforms.get(Currentplat).getX() && x + bitmap.getWidth() <= platforms.get(Currentplat).getX() + platforms.get(Currentplat).getBitmap().getWidth())) {
                    StopRight = true;
                }
                if (Currentplat != 4)
                    if ((y > platforms.get(Currentplat + 1).getY() - bitmap.getHeight() + 60 && y <= platforms.get(Currentplat + 1).getY() + platforms.get(Currentplat + 1).getBitmap().getHeight()) && (x + bitmap.getWidth() >= platforms.get(Currentplat + 1).getX() && x + bitmap.getWidth() <= platforms.get(Currentplat + 1).getX() + platforms.get(Currentplat + 1).getBitmap().getWidth())) {
                        StopRight = true;
                    }
            }
            if (Currentplat == -1) {
                if (((y > platforms.get(Currentplat + 1).getY() - bitmap.getHeight() + 60 && y <= platforms.get(Currentplat + 1).getY() + platforms.get(Currentplat + 1).getBitmap().getHeight()) && (x + bitmap.getWidth() >= platforms.get(Currentplat + 1).getX() && x + bitmap.getWidth() <= platforms.get(Currentplat + 1).getX() + platforms.get(Currentplat + 1).getBitmap().getWidth()))) {
                    StopRight = true;
                }
                if (((y > platforms.get(Currentplat + 2).getY() - bitmap.getHeight() + 60 && y <= platforms.get(Currentplat + 2).getY() + platforms.get(Currentplat + 2).getBitmap().getHeight()) && (x + bitmap.getWidth() >= platforms.get(Currentplat + 2).getX() && x + bitmap.getWidth() <= platforms.get(Currentplat + 2).getX() + platforms.get(Currentplat + 1).getBitmap().getWidth()))) {
                    StopRight = true;
                }
            }
            //}
            if (!StopRight) {
                x += 20;
                if (x > jumpGame.getWidth()) {
                    this.x = -bitmap.getWidth();
                }
                StopRight = true;
            }
        }
    }

    public void Left(List<Platform> platforms) {
        if (IsActive) {
            //for(int i =0;i<platforms.size();i++)     // for now not needed
            //{
            if (Currentplat != -1) {
                if ((y > platforms.get(Currentplat).getY() - bitmap.getHeight() + 60 && y <= platforms.get(Currentplat).getY() + platforms.get(Currentplat).getBitmap().getHeight()) && (x <= platforms.get(Currentplat).getX() + platforms.get(Currentplat).getBitmap().getWidth() && x >= platforms.get(Currentplat).getX())) {
                    StopLeft = true;
                }
                if (Currentplat != 4)
                    if ((y > platforms.get(Currentplat + 1).getY() - bitmap.getHeight() + 60 && y <= platforms.get(Currentplat + 1).getY() + platforms.get(Currentplat + 1).getBitmap().getHeight()) && (x <= platforms.get(Currentplat + 1).getX() + platforms.get(Currentplat + 1).getBitmap().getWidth() && x >= platforms.get(Currentplat + 1).getX())) {
                        StopLeft = true;
                    }
            }
            if (Currentplat == -1) {
                if ((y > platforms.get(Currentplat + 1).getY() - bitmap.getHeight() + 60 && y <= platforms.get(Currentplat + 1).getY() + platforms.get(Currentplat + 1).getBitmap().getHeight()) && (x <= platforms.get(Currentplat + 1).getX() + platforms.get(Currentplat + 1).getBitmap().getWidth() && x >= platforms.get(Currentplat + 1).getX())) {
                    StopLeft = true;
                }
                if ((y > platforms.get(Currentplat + 2).getY() - bitmap.getHeight() + 60 && y <= platforms.get(Currentplat + 2).getY() + platforms.get(Currentplat + 2).getBitmap().getHeight()) && (x <= platforms.get(Currentplat + 2).getX() + platforms.get(Currentplat + 2).getBitmap().getWidth() && x >= platforms.get(Currentplat + 2).getX())) {
                    StopLeft = true;
                }
            }

            //}
            if (!StopLeft) {
                x -= 20;
                if (x < -bitmap.getWidth()) {
                    this.x = jumpGame.getWidth();
                }
                StopLeft = true;
            }
        }
    }
}
