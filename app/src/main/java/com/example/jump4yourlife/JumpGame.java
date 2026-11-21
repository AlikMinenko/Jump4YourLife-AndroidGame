package com.example.jump4yourlife;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ServiceCompat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class JumpGame extends View implements View.OnClickListener {

    Context context; // the context of the activity

    GameThread gameThread; // a thread that cooperates with the handler
    Handler gamehandler; // a handler that runs repeatedly

    Player player; // the player
    int count =0; // a variable type int for help for something to happen once

    ArrayList<Platform> plats; // list of platforms
    int sofer=0; // a variable type int for help
    boolean allowed = true; // a variable type boolean for help
    int speed=1; // the speed of the falling of the platforms and spikes and player

    ArrayList<Spike> spikes; // list of spikes
    int CurrentSpike = 0; // spike that is on the current platform that the player stands on

    Buttons buttonJump; // the jump buttom
    Buttons buttonLeft; // the go left buttom
    Buttons buttonRight; // the go right buttom

    Bitmap Background; // the clouds background
    Bitmap SpaceBackground; // the space background
    Paint backpaint; // paint for increasing and decreasing alpha of cloud background
    int alpha=250; // alpha for clouds background
    int counter=0; // a variable type int for the movement of the backgrounds

    Bitmap BigPLatBit; // the big platform at the start of the game
    int height=0; // a variable type int for help with the big platform
    int floor=0; // a variable type int for help with the big platform
    boolean active=true; // a variable type boolean for help with the big platform, true for drawing the big platform and ect
    int top=0; // a variable type int for help with the big platform
    int count2=10; // a variable type int for help for something to happen once

    Dialog d; // death dialog
    int count3 =0; // a variable type int for help for something to happen once
    //int count =30; //A PART FROM NOT ALLWAYS DROPPING

    Paint paint; // paint for the height text
    int points=0; // points that you get when you jump to platforms
    int OldPlat=-1; // the platform previous to the current one

    int count4 =0; // a variable type int for help for something to happen once
    //int count5 =0; // a variable type int for help for something to happen once

    Button btnmenu,btnleader; // buttons in death dialog after game

    SharedPreferences sp1; // sharedpreference that saves player information
    SharedPreferences sp2; // sharedpreference that has "IsFinished" and tells if game is finished or not (true or false)
    SharedPreferences newsp; // sharedpreference that has "BTNS" and in jumpgame (here) changes to either 1 or 2

    int buttonpressed=0; // a variable type int for help, changes to either 1 or 2 wehn a button in dialog is pressed

    final long FPS =1000; // the fps



    public JumpGame(Context context){
        super(context);
        this.context =context;

        gameThread = new GameThread();
        gameThread.start();
        gamehandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (rightispressedingame.get()){
                    player.setStopRight(false);
                    //Log.d("VIEW_LOG_TAG", "Touching Down");
                }
                if (leftispressedingame.get()){
                    player.setStopLeft(false);
                    //Log.d("VIEW_LOG_TAG", "Touching Down");
                }
                invalidate();
                return true;
            }
        });


        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.lastpixelboy);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.newup);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.newright);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),R.drawable.newleft);
        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(),R.drawable.newleftnewplattran);
        Bitmap bitmap6 = BitmapFactory.decodeResource(getResources(),R.drawable.smallspike);
        Log.d("spikeh",""+bitmap6.getHeight());
        Background = BitmapFactory.decodeResource(getResources(),R.drawable.sixclouds);
        SpaceBackground = BitmapFactory.decodeResource(getResources(),R.drawable.spacefour);
        BigPLatBit = BitmapFactory.decodeResource(getResources(),R.drawable.newplattran);
        height=BigPLatBit.getHeight()-100;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(70);
        paint.isFakeBoldText();
        backpaint = new Paint();
        backpaint.setAlpha(alpha);
        player = new Player(100,1300,50,-70,bitmap1,this,null,true,true,true,true,-1);
        buttonJump = new Buttons(830,1670,0,0,bitmap2,this,null,true);
        buttonRight = new Buttons(270,1670,0,0,bitmap3,this,null,true);
        buttonLeft = new Buttons(10,1670,0,0,bitmap4,this,null,true);
        plats =new ArrayList<Platform>();
        Platform plat0 = new Platform(100,566,0,0,bitmap5,this,null,true);
        plats.add(plat0);
        Platform plat1 = new Platform(1000,461,0,0,bitmap5,this,null,true);
        plats.add(plat1);
        Platform plat2 = new Platform(1700,-420,0,0,bitmap5,this,null,true);
        plats.add(plat2);
        Platform plat3 = new Platform(1000,-910,0,0,bitmap5,this,null,true);
        plats.add(plat3);
        Platform plat4 = new Platform(500,-910,0,0,bitmap5,this,null,true);
        plats.add(plat4);
        spikes = new ArrayList<Spike>();
        Spike spike0 = new Spike(300,0,0,0,bitmap6,this,null,true);
        spikes.add(spike0);
        Spike spike1 = new Spike(1200,0,0,0,bitmap6,this,null,true);
        spikes.add(spike1);
        Spike spike2 = new Spike(1900,0,0,0,bitmap6,this,null,true);
        spikes.add(spike2);
        Spike spike3 = new Spike(1200,0,0,0,bitmap6,this,null,true);
        spikes.add(spike3);
        Spike spike4 = new Spike(700,0,0,0,bitmap6,this,null,true);
        spikes.add(spike4);
        sp1 = getContext().getSharedPreferences("PlayerInformation",0);
        sp2 = getContext().getSharedPreferences("IsFinished",0);
        newsp = getContext().getSharedPreferences("BTNS",0);
        SharedPreferences.Editor editor=sp2.edit();
        editor.putBoolean("GameFinish",false);
        editor.commit();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        DrawSpaceBackground(canvas,SpaceBackground,0,counter,canvas.getWidth(),canvas.getHeight());
        DrawSpaceBackground(canvas,SpaceBackground,0,counter-canvas.getHeight(),canvas.getWidth(),canvas.getHeight());

        DrawBackground(canvas,Background,0,counter,canvas.getWidth(),canvas.getHeight(),backpaint);
        DrawBackground(canvas,Background,0,counter-canvas.getHeight(),canvas.getWidth(),canvas.getHeight(),backpaint);

//        int x=0;
//        x=player.getCurrentplat()-2;
//        if((player.getCurrentplat()>= 1) && (x!=plats.size()-1))
//        {
//            x++;
//            //Log.d("x",""+x);
//            plats.get(x).IsActive=false;
//        }


//        if(plats.get(0).y >= canvas.getHeight())
//        {
//            plats.get(0).IsActive=false;
//        }

        for (int i=0;i<plats.size();i++)
        {
            if(plats.get(i).y >= canvas.getHeight())
            {
                plats.get(i).y = -plats.get(i).bitmap.getHeight() - 200;
                //plats.get(0).IsActive=false;
            }
        }

        for (int i =0;i<spikes.size();i++)
        {
            if(spikes.get(i).y >= canvas.getHeight())
            {
                spikes.get(i).y = -plats.get(0).bitmap.getHeight() - 200;
            }
        }

        for (int i=0;i<spikes.size();i++)
        {
            spikes.get(i).Draw(canvas);
        }

        if(player.Currentplat == 0)
        {
            CurrentSpike =0;
        }
        if(player.Currentplat == 1)
        {
            CurrentSpike =1;
        }
        if(player.Currentplat == 2)
        {
            CurrentSpike =2;
        }
        if(player.Currentplat == 3)
        {
            CurrentSpike =3;
        }
        if(player.Currentplat == 4)
        {
            CurrentSpike =4;
        }

        for (int i=0;i<plats.size();i++)
        {
            if(plats.get(i).isActive() == true)
            {
                plats.get(i).Draw(canvas);
            }
            if(player.getCurrentplat() ==1)
            {
                if(top>canvas.getHeight())
                {
                    active=false;
                }
            }
        }


        if(points >= 30 && points < 40){ //&& count5 ==0){
            //speed=3;
            //count5++;
        }
        if(points >= 40 && points < 145){ // && count5 ==1) {
            //speed=5;
            if (alpha!=0) {
                alpha--;
            }
            backpaint.setAlpha(alpha);
            paint.setColor(Color.WHITE);
            //count5++;
        }
        if(points >= 145 && points < 200){ // && count5 ==2) {
            if (alpha!=250) {
                alpha++;
            }
            backpaint.setAlpha(alpha);
            paint.setColor(Color.BLACK);
            //speed=7;
            //count5++;
        }
        if(points >= 200 && points < 260){ // && count5 ==3) {
            if (alpha!=0) {
                alpha--;
            }
            backpaint.setAlpha(alpha);
            paint.setColor(Color.WHITE);
            //speed=9;
            //count5++;
        }
        if(points >= 260){ // && count5 ==4) {
            if (alpha!=250) {
                alpha++;
            }
            backpaint.setAlpha(alpha);
            paint.setColor(Color.BLACK);
            //speed=11;
            //count5++;
        }
        Log.d("speeed",""+speed);
        Log.d("alphaaa",""+alpha);


        if((player.getCurrentplat() >=1 || sofer > 0) && allowed)        //ALLWAYS DROPPING
        {
            Fall(plats,player,spikes);
            counter=counter%canvas.getHeight();
            counter=counter+2;
            if(sofer != 10)
                sofer++;
        }


        //Log.d("count",""+count);                                      //NOT ALLWAYS DROPPING
        //if(count != 0)
        //    count--;
        //if(player.getCurrentplat() >= 1 && count != 0 && player.StopJump ==true)
        //{
        //    player.Fall(plats);
        //}
        if(OldPlat == 4 && player.Currentplat == 0)
            OldPlat=-1;
        if(player.Currentplat != OldPlat && player.Currentplat> OldPlat && player.Currentplat != -1 && allowed)
        {
            points+=5;
            OldPlat++;
        }

        if(count2 !=0){
            top =canvas.getHeight()-(BigPLatBit.getHeight()/3);
            count2--;
        }
        BigPLatBit= Bitmap.createScaledBitmap(BigPLatBit,canvas.getWidth()+200,height,false);
        if(active==true){
            canvas.drawBitmap(BigPLatBit,-100,top,null);
        }


        if(count == 0)
        {
            floor=canvas.getHeight()-BigPLatBit.getHeight()-player.getBitmap().getHeight()+400;
            plats.get(0).setY(floor-420+player.bitmap.getHeight()-60);
            plats.get(1).setY(floor-615+player.bitmap.getHeight()-60); // =357   // 357=821-x+201-50 // x=615
            plats.get(2).setY(floor-915+player.bitmap.getHeight()-60); // =57   // 57=821-x+201-50 // x=915
            plats.get(3).setY(floor-1260+player.bitmap.getHeight()-60); // =-288   // -288=821-x+201-50 // x=1260
            plats.get(4).setY(floor-1560+player.bitmap.getHeight()-60); // =-588   // -588=821-x+201-50 // x=1560
            //plats.get(5).setY(floor-1905+player.bitmap.getHeight()-50); // =-888   // -888=821-x+201-50 // x=1860
            spikes.get(0).setY(plats.get(0).getY()-(spikes.get(0).getBitmap().getHeight()/3));
            spikes.get(1).setY(plats.get(1).getY()-(spikes.get(1).getBitmap().getHeight()/3));
            spikes.get(2).setY(plats.get(2).getY()-(spikes.get(2).getBitmap().getHeight()/3));
            spikes.get(3).setY(plats.get(3).getY()-(spikes.get(3).getBitmap().getHeight()/3));
            spikes.get(4).setY(plats.get(4).getY()-(spikes.get(4).getBitmap().getHeight()/3));
            player.setX(canvas.getWidth()/2);
            player.setY(floor);
            count++;
        }

        if(count4 == 0 && canvas.getWidth() > 1960)
        {
            plats.get(0).setX(100);
            plats.get(1).setX(1000);
            plats.get(2).setX(1700);
            plats.get(3).setX(1000);
            plats.get(4).setX(500);
            spikes.get(0).setX(300);
            spikes.get(1).setX(1200);
            spikes.get(2).setX(1900);
            spikes.get(3).setX(1200);
            spikes.get(4).setX(700);
            count4++;
        }

        if(count4 == 0 && canvas.getWidth() <= 1960)
        {
            plats.get(0).setX(200);
            plats.get(1).setX(1100);
            plats.get(2).setX(290);
            plats.get(3).setX(1220);
            plats.get(4).setX(450);
            spikes.get(0).setX(400);
            spikes.get(1).setX(1300);
            spikes.get(2).setX(490);
            spikes.get(3).setX(1420);
            spikes.get(4).setX(810);
            count4++;
        }

        if(IsDead(player,canvas,spikes) && count3 ==0){
            allowed=false;
            SharedPreferences.Editor editor=sp1.edit();
            editor.putInt("Height",points);
            editor.commit();
            Createdeathdialog();
            count3++;
        }
        if(IsDead(player,canvas,spikes))
        {
            player.setStopRight(true);
            player.setStopLeft(true);
            player.setStopJump(true);
        }
//        if(IsDead(player,canvas,spikes) && count4 ==0)
//        {
//            player.setStopRight(true);
//            player.setStopLeft(true);
//            player.setStopJump(true);
//            Createdeathdialog();
//            count4++;
//        }

//        for (int i =0;i<plats.size();i++)
//        {
//            plats.get(i).platend(plats.get(i).y,canvas);
//            //plats.get(i).alphaplus(plats.get(i).y,canvas);
//        }
        for (int i =0;i<plats.size();i++)
        {
            spikes.get(i).spikeend(canvas,plats.get(i));
            plats.get(i).platend(canvas,spikes.get(i));
        }


        player.Draw(canvas);
        Log.d("Justy",""+player.y);
        Log.d("stopright",""+player.StopRight);
        //Log.d("v",""+player.vy);

        canvas.drawText("Height: "+ points +"m",0,60,paint);

        buttonJump.Draw(canvas);
        buttonJump.setY(canvas.getHeight()-buttonJump.getBitmap().getHeight()-10);
        buttonJump.setX(canvas.getWidth()-buttonJump.getBitmap().getWidth()-10);
        player.Jump(plats,floor);

        buttonRight.Draw(canvas);
        buttonRight.setY(canvas.getHeight()-buttonRight.getBitmap().getHeight()-10);
        buttonRight.setX(buttonRight.getBitmap().getHeight()+30);
        player.Right(plats);

        buttonLeft.Draw(canvas);
        buttonLeft.setY(canvas.getHeight()-buttonLeft.getBitmap().getHeight()-10);
        buttonLeft.setX(10);
        player.Left(plats);

        Log.d("onplat",""+player.OnPlatform(plats));
        //Log.d("IsDead",""+IsDead(player,canvas,spikes));

//        invalidate();
    }




    final AtomicBoolean rightispressedingame = new AtomicBoolean(false); // a variable type atomicboolean for help for buttons left and right
    final AtomicBoolean leftispressedingame = new AtomicBoolean(false); // a variable type atomicboolean for help for buttons left and right


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Bitmap NoramlWay = BitmapFactory.decodeResource(getResources(),R.drawable.lastpixelboy);
        Bitmap ReverseWay = BitmapFactory.decodeResource(getResources(),R.drawable.reverselastpixelboy);

        int index=event.getActionIndex();

        float x=event.getX();
        float y=event.getY();
        Log.d("x",""+x);
        Log.d("index",""+index);




//        Thread ButtonThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
////                while (rightispressedingame.get()){
////                    player.setStopRight(false);
////
////                    //Log.d("VIEW_LOG_TAG", "Touching Down");
////                }
////                while (leftispressedingame.get()){
////                    player.setStopLeft(false);
////
////                    //Log.d("VIEW_LOG_TAG", "Touching Down");
////                }
//                Log.d("VIEW_LOG_TAG", "Not Touching");
//            }
//        });
//        ButtonThread.start();

        if(index ==1)
        {
            float z=event.getX(1);
            float w=event.getY(1);
            Log.d("z",""+z);

            if(player.StopJump==false)
            {
                buttonJump.IsActive =false;
            }
            else
            {
                buttonJump.IsActive =true;
            }
            if (buttonJump.DidUserTouchMe(z,w))
            {
                player.setVy(-70);
                player.setStopJump(false);
            }
            if(buttonRight.DidUserTouchMe(z,w))
            {
                if(event.getAction() ==MotionEvent.ACTION_DOWN || event.getAction() ==MotionEvent.ACTION_MOVE){
                    player.setBitmap(ReverseWay);
                    rightispressedingame.set(true);
                }
                if(event.getAction() ==MotionEvent.ACTION_UP)
                {
                    rightispressedingame.set(false);
                }
            }
            if(buttonLeft.DidUserTouchMe(z,w))
            {
                if(event.getAction() ==MotionEvent.ACTION_DOWN || event.getAction() ==MotionEvent.ACTION_MOVE){
                    player.setBitmap(NoramlWay);
                    leftispressedingame.set(true);
                }
                if(event.getAction() ==MotionEvent.ACTION_UP){
                    leftispressedingame.set(false);
                }
            }
        }

        if(player.StopJump==false)
        {
            buttonJump.IsActive =false;
        }
        else
        {
            buttonJump.IsActive =true;
        }
        if (buttonJump.DidUserTouchMe(x,y))
        {
            player.setVy(-70);
            player.setStopJump(false);
        }
        if (buttonRight.DidUserTouchMe(x,y))
        {
            if(event.getAction() ==MotionEvent.ACTION_DOWN || event.getAction() ==MotionEvent.ACTION_MOVE){
                player.setBitmap(ReverseWay);
                rightispressedingame.set(true);
                leftispressedingame.set(false);
            }
            if(event.getAction() ==MotionEvent.ACTION_UP){
                rightispressedingame.set(false);
            }
        }
        if(buttonLeft.DidUserTouchMe(x,y))
        {
            Log.d("didtouchleft","True");
            if(event.getAction() ==MotionEvent.ACTION_DOWN || event.getAction() ==MotionEvent.ACTION_MOVE){
                player.setBitmap(NoramlWay);
                leftispressedingame.set(true);
                rightispressedingame.set(false);
            }
            if(event.getAction() ==MotionEvent.ACTION_UP){
                leftispressedingame.set(false);
            }
        }
        if (!buttonRight.DidUserTouchMe(x,y) && !buttonLeft.DidUserTouchMe(x,y) && !buttonJump.DidUserTouchMe(x,y))
        {
            if(event.getAction() ==MotionEvent.ACTION_DOWN || event.getAction() ==MotionEvent.ACTION_MOVE){
            rightispressedingame.set(false);
            leftispressedingame.set(false);
            }
        }



//        if(event.getAction() ==MotionEvent.ACTION_DOWN || event.getAction() ==MotionEvent.ACTION_MOVE){
//            if (!buttonRight.DidUserTouchMe(x,y) && !buttonLeft.DidUserTouchMe(x,y) && !buttonJump.DidUserTouchMe(x,y))
//            {
//                rightispressedingame.set(false);
//                leftispressedingame.set(false);
//            }
//            if (buttonRight.DidUserTouchMe(x,y))
//            {
//                player.setBitmap(ReverseWay);
//                rightispressedingame.set(true);
//                leftispressedingame.set(false);
//            }
//            if (buttonLeft.DidUserTouchMe(x,y))
//            {
//                player.setBitmap(NoramlWay);
//                leftispressedingame.set(true);
//                rightispressedingame.set(false);
//            }
//        }
//        if(event.getAction() ==MotionEvent.ACTION_UP){
//            if (buttonRight.DidUserTouchMe(x,y))
//            {
//                rightispressedingame.set(false);
//            }
//            if (buttonLeft.DidUserTouchMe(x,y))
//            {
//                leftispressedingame.set(false);
//            }
//        }




////       Thread ButtonThread = new Thread(new Runnable() {
////            @Override
////            public void run() {
////                while(true) {
////                    try {
////                        Thread.sleep(1);
////                        if(player.StopJump ==false)
////                        {
////                            buttonJump.IsActive =false;
////                            jumpispressedingame=false;
////                        }
////                        if(player.StopJump==true){
////                            while (jumpispressedingame){
////                                Log.d("VIEW_LOG_TAG", "Touching Down");
////                                player.setVy(-70);
////                                player.setStopJump(false);
////                            }
////                        }
////                        while(rightispressedingame){
////                            player.setStopRight(false);
////                            player.setBitmap(ReverseWay);
////                        }
////                        while(leftispressedingame){
////                            player.setStopLeft(false);
////                            player.setBitmap(NoramlWay);
////                        }
////                    }
////                    catch (InterruptedException e)
////                    {
////                    e.printStackTrace();
////                    }
////                    Log.d("VIEW_LOG_TAG", "Not Touching");
////                }
////            }
////        });
//        ButtonThread = new ButtonThread();
//        ButtonThread.start();
//
//
//
//        if(index ==1)
//        {
//            z=event.getX(1);
//            w=event.getY(1);
//            Log.d("z",""+z);
//
//            if(player.StopJump==false)
//            {
//                buttonJump.IsActive =false;
//                jumpispressedingame=false;
//            }
//            else
//            {
//                buttonJump.IsActive =true;
//            }
//            if (buttonJump.DidUserTouchMe(z,w))
//            {
//                if (event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
//                    jumpispressedingame=true;
//                }
////                if (event.getAction() == MotionEvent.ACTION_MOVE) {
////                    jumpispressedingame=true;
////                }
//                if(event.getAction() == MotionEvent.ACTION_POINTER_UP ){
//                    jumpispressedingame=false;
//                }
//            }
//
//            if(buttonRight.DidUserTouchMe(z,w))
//            {
//                if (event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
//                    rightispressedingame=true;
//                }
////                if (event.getAction() == MotionEvent.ACTION_MOVE) {
////                    rightispressedingame=true;
////                }
//                if(event.getAction() == MotionEvent.ACTION_POINTER_UP ){
//                    rightispressedingame=false;
//                }
//            }
//            if(buttonLeft.DidUserTouchMe(z,w))
//            {
//                if (event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
//                    leftispressedingame=true;
//                }
////                if (event.getAction() == MotionEvent.ACTION_POINTER) {
////                    leftispressedingame=true;
////                }
//                if(event.getAction() == MotionEvent.ACTION_POINTER_UP ){
//                    leftispressedingame=false;
//                }
//            }
//        }
//
//        if(player.StopJump==false)
//        {
//            buttonJump.IsActive =false;
//            jumpispressedingame=false;
//        }
//        else
//        {
//            buttonJump.IsActive =true;
//        }
//        if (buttonJump.DidUserTouchMe(x,y))
//        {
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                jumpispressedingame=true;
//            }
//            if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                jumpispressedingame=true;
//            }
//            if(event.getAction() == MotionEvent.ACTION_UP ){
//                jumpispressedingame=false;
//            }
//        }
//        if(buttonRight.DidUserTouchMe(x,y))
//        {
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                rightispressedingame=true;
//            }
//            if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                rightispressedingame=true;
//            }
//            if(event.getAction() == MotionEvent.ACTION_UP ){
//                rightispressedingame=false;
//            }
//        }
//        if(buttonLeft.DidUserTouchMe(x,y))
//        {
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                leftispressedingame=true;
//            }
//            if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                leftispressedingame=true;
//            }
//            if(event.getAction() == MotionEvent.ACTION_UP ){
//                leftispressedingame=false;
//            }
//        }



        return true;
    }


    public static synchronized void DrawBackground(Canvas canvas,Bitmap bitmap,int x,int y,int width,int height,Paint paint)
    {
        Rect Source= new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        Rect Target= new Rect(x,y,x+width,y+height);
        canvas.drawBitmap(bitmap,Source,Target,paint);
    }

    public static synchronized void DrawSpaceBackground(Canvas canvas,Bitmap bitmap,int x,int y,int width,int height)
    {
        Rect Source= new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        Rect Target= new Rect(x,y,x+width,y+height);
        canvas.drawBitmap(bitmap,Source,Target,null);
    }

    public void Fall(List<Platform> platforms,Player player,List<Spike> spikes)
    {
        player.y+=speed;
        platforms.get(0).y+=speed;
        platforms.get(1).y+=speed;
        platforms.get(2).y+=speed;
        platforms.get(3).y+=speed;
        platforms.get(4).y+=speed;
        if(active==true){
            top+=speed;
        }
        for (int i=0;i<spikes.size();i++)
        {
            spikes.get(i).y+=speed;
        }
    }

    public class GameThread extends Thread
    {
        long steppersecond =1000/FPS;
        long starttime;
        long sleeptime;
        @Override
        public void run() {
            super.run();
            while(true)
            {
                starttime=System.currentTimeMillis();
                sleeptime =steppersecond -(System.currentTimeMillis()-starttime);
                if(sleeptime> 0){
                    try {
                        sleep(sleeptime);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        sleep(10);
                        gamehandler.sendEmptyMessage(0);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                gamehandler.sendEmptyMessage(0);
            }
        }
    }


    public boolean IsDead(Player player,Canvas canvas,List<Spike> spikes)
    {
        if(player.y > canvas.getHeight()){
            Intent intent2 = new Intent(getContext(),NotificationService.class);
            getContext().stopService(intent2);
            return true;
        }
        if(((player.x >= spikes.get(CurrentSpike).getX() && player.x <= spikes.get(CurrentSpike).getX()+spikes.get(CurrentSpike).getBitmap().getWidth()) || (player.x +player.getBitmap().getWidth() >= spikes.get(CurrentSpike).getX() && player.x+player.getBitmap().getWidth() <= spikes.get(CurrentSpike).getX()+spikes.get(CurrentSpike).getBitmap().getWidth()) || (player.x + (player.getBitmap().getWidth()/2) >= spikes.get(CurrentSpike).getX() && player.x + (player.getBitmap().getWidth()/2) <= spikes.get(CurrentSpike).getX()+spikes.get(CurrentSpike).getBitmap().getWidth())) && player.OnPlatform(plats) == 1) {
            Intent intent2 = new Intent(getContext(),NotificationService.class);
            getContext().stopService(intent2);
            return true;
        }
        return false;
    }

    public void Createdeathdialog(){
        d=new Dialog(context);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.death_dialog);
        d.setCancelable(false);
        btnmenu=d.findViewById(R.id.btnmainmenu);
        btnleader=d.findViewById(R.id.btnleaderboard);
        btnmenu.setOnClickListener(this);
        btnleader.setOnClickListener(this);
        d.show();
    }

    @Override
    public void onClick(View v) {
        if(v==btnmenu){
            buttonpressed=1;
            SharedPreferences.Editor editor=sp2.edit();
            editor.putBoolean("GameFinish",true);
            editor.commit();
            LinearLayout parentLyt = (LinearLayout) this.getParent();
            parentLyt.removeView(this);
            parentLyt.getRootView();
            SharedPreferences.Editor editor2=newsp.edit();
            editor2.putInt("buttonpressed",buttonpressed);
            editor2.commit();
            d.dismiss();
        }
        if(v==btnleader){
            buttonpressed=2;
            SharedPreferences.Editor editor=sp2.edit();
            editor.putBoolean("GameFinish",true);
            editor.commit();
            LinearLayout parentLyt = (LinearLayout) this.getParent();
            parentLyt.removeView(this);
            parentLyt.getRootView();
            SharedPreferences.Editor editor2=newsp.edit();
            editor2.putInt("buttonpressed",buttonpressed);
            editor2.commit();
            d.dismiss();
        }
    }

}
