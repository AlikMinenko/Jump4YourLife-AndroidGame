package com.example.jump4yourlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class GameActivity extends AppCompatActivity {

    View decorview; // the view of the game activity
    JumpGame jumpGame; // the game
    LinearLayout linearLayout; // the layout of the game activity
    SharedPreferences sp; // sharedpreference that has "BTNS" and in game activity (here) changes to 0 (clears the previous number)
    Handler handler; // a handler that runs repeatedly and "updates"
    Runnable r2; // a variable type runnable that cooperates with the handler
    int buttonpressed=0; // a variable type int that gets the number in the sharedpreference


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();


        decorview = getWindow().getDecorView();
        decorview.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if(i==0){
                    decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                }
            }
        });

        jumpGame = new JumpGame(this);
        linearLayout =findViewById(R.id.Linearlay);
        linearLayout.addView(jumpGame);

        sp = getSharedPreferences("BTNS",0);

        handler = new Handler();
        r2= new Runnable()
        {
            @Override
            public void run() {
                handler.postDelayed(r2,1);
                buttonpressed = sp.getInt("buttonpressed",0);
                if(linearLayout.getChildCount() ==0 && buttonpressed ==1){
                    Intent intent =getIntent();
                    setResult(0,intent);
                    finish();
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putInt("buttonpressed",0);
                    editor.commit();
                }
                if(linearLayout.getChildCount() ==0 && buttonpressed == 2)
                {
                    Intent intent =getIntent();
                    setResult(1,intent);
                    finish();
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putInt("buttonpressed",0);
                    editor.commit();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(r2,1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(r2);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus){
            decorview.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

}