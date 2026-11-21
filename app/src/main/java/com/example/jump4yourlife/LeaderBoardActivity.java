package com.example.jump4yourlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {

    RecyclerView recyclerView; // a variable type recycleview on the leader board activity
    ArrayList<PlayerInformation> playerInformations; // an array list that consits of players information
    InformationOpenHelper ioh; // sql
    PlayerInformationAdapter playerInformationAdapter; // an adapter for recycle view
    SharedPreferences sp1; // sharedpreference that saves player information
    SharedPreferences sp2; // sharedpreference that has "IsFinished" and tells if game is finished or not (true or false)
    int Place=1; // a random starting place
    //Handler handler; // a handler that runs repeatedly and "updates" the leader board
    //Runnable r2; // a variable type runnable that cooperates with the handler


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sp1 = getSharedPreferences("PlayerInformation",0);
        sp2 = getSharedPreferences("IsFinished",0);
        SharedPreferences.Editor editor1=sp1.edit();
        SharedPreferences.Editor editor2=sp2.edit();




        //handler = new Handler();     // handler and runnable only with lines 84-95 and lines 39-40
        //r2= new Runnable() {
        //    @Override
        //    public void run() {
//                handler.postDelayed(r2, 1);
                recyclerView =findViewById(R.id.recycleview);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                ioh= new InformationOpenHelper(getBaseContext());
                playerInformations =new ArrayList<PlayerInformation>();
                ioh.Open();
                playerInformations=ioh.getAllPlayerInformations();
                ioh.close();

                boolean allowed = sp2.getBoolean("GameFinish",false);
                if(allowed==true)
                {
                    editor2.putBoolean("GameFinish",false);
                    editor2.commit();
                    createInformations();
                    editor1.clear();
                    editor1.commit();
                }


//                ioh.Open();
//                Cursor c = ioh.getAllID();
//                int helpme=0;
//                c.moveToFirst();
//                if(playerInformations.size() != 0){
//                    for (int i = playerInformations.size()-1; i > 0; i--) {
//                        playerInformations.get(i).setPlace(c.getCount() - helpme);
//                        helpme++;
//                        c.moveToNext();
//                    }
//                }
//                ioh.close();

                if(playerInformations.size() != 0){
                    int helper = playerInformations.size();
                    for(int i =playerInformations.size()-1;i>0;i--) {
                    playerInformations.get(i).setPlace(helper);
                    helper--;
                    }
                }


                playerInformationAdapter =new PlayerInformationAdapter(getBaseContext(),playerInformations);
                recyclerView.setAdapter(playerInformationAdapter);
        //    }
        //};


//        if (num == 0){
//            Intent intent = getIntent();
//            finish();
//            overridePendingTransition(0,0);
//            startActivity(intent);
//            overridePendingTransition(0,0);
//            editor3.putInt("num",1);
//            editor3.commit();
//            this.recreate();
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id=item.getItemId();
        if(id == R.id.menumain){
            Intent intent =getIntent();
            setResult(0,intent);
            finish();
        }
        if(id == R.id.menuregister){
            Intent intent =getIntent();
            setResult(1,intent);
            finish();
        }

        if(id == R.id.menurules){
            Intent intent =getIntent();
            setResult(2,intent);
            finish();
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        handler.postDelayed(r2,1);
    }

    @Override
    protected void onPause() {
        super.onPause();
  //      handler.removeCallbacks(r2);
    }

    public void createInformations()
    {
        String Name =sp1.getString("NickName",null);
        String encoded = sp1.getString("Picture",null);
        Bitmap Picture;
        if(encoded !=null)
        {
        byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
        Picture = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        }
        else
            Picture =null;
        int Height = sp1.getInt("Height",0);
        ioh.Open();
        PlayerInformation PI=new PlayerInformation(Place,Name,Height,Picture);
        PI=ioh.createPlayerInformation(PI);
        playerInformations.add(PI);
        ioh.close();
    }

}