package com.example.jump4yourlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    View decorview; // the view of the main activity
    Button btnmrnplay,btnmleadeboard,btnmsave,btnmplayonly,btnmpic,btnmrules; // buttons for main activity and register dialog
    EditText etmusername; // edit text in register dialog
    ImageView ivmpic; // image view in register dialog
    Dialog d; // register dialog
    BluetootheReciver bluetootheReciver; // a variable type bluetoothreciver for checking the state of the bluetooth
    Bitmap bitmap; // bitmap for the picture of the player

    SharedPreferences sp; // sharedpreference that saves player information
    //SharedPreferences sp2; // sharedpreference that has "BTNS" and in main activity (here) changes to 0 (clears the previous number)

    boolean DidSave=false; // a variable type boolean that chages when the user has saved

    public static final String CHANNEL_ID="exampleServiceChannel"; // a variable type string that has channel id


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        bluetootheReciver = new BluetootheReciver();

        btnmrnplay =findViewById(R.id.btnRNP);
        btnmleadeboard=findViewById(R.id.btnLB);
        btnmrules=findViewById(R.id.btnrules);
        btnmrnplay.setOnClickListener(this);
        btnmleadeboard.setOnClickListener(this);
        btnmrules.setOnClickListener(this);

        sp=getSharedPreferences("PlayerInformation",0);
        //sp2=getSharedPreferences("BTNS",0);


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

        createNotificationChannel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(bluetootheReciver,new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(bluetootheReciver);
    }

    public void Createregisterdialog(){
        d= new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.register_dialog);
        d.setCancelable(true);
        btnmpic=d.findViewById(R.id.btnpic);
        btnmsave=d.findViewById(R.id.btnsave);
        btnmplayonly=d.findViewById(R.id.btnplayonly);
        etmusername=d.findViewById(R.id.etname);
        ivmpic=d.findViewById(R.id.ivpic);
        btnmpic.setOnClickListener(this);
        btnmsave.setOnClickListener(this);
        btnmplayonly.setOnClickListener(this);
        d.show();
    }

    @Override
    public void onClick(View v)
    {
        if(btnmrnplay ==v)
        {
            Createregisterdialog();
        }
        if(btnmplayonly == v)
        {
            if(DidSave == false)
            {
                Toast.makeText(this, "You have to Save", Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(this,GameActivity.class);
                startActivityForResult(intent,1);
                Intent intent2 =new Intent(this,NotificationService.class);
                startService(intent2);
                DidSave=false;
                d.dismiss();
            }
        }
        if(btnmleadeboard == v)
        {
            Intent intent = new Intent(this,LeaderBoardActivity.class);
            startActivityForResult(intent,2);
        }
        if(btnmpic == v)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,3);
        }
        if(btnmsave == v)
        {
            String helper = etmusername.getText().toString();
            if(helper.matches(""))
            {
                Toast.makeText(this, "You have to enter a NickName", Toast.LENGTH_LONG).show();
            }
            else {
                DidSave=true;
                Toast.makeText(this,"Information was saved",Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor=sp.edit();
                if(bitmap != null) {
                    ByteArrayOutputStream ba = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, ba);
                    byte[] b = ba.toByteArray();
                    String encoded = Base64.encodeToString(b, Base64.DEFAULT);
                    editor.putString("Picture",encoded);
                }
                editor.putString("NickName",etmusername.getText().toString());
                editor.commit();
                bitmap=null;
            }
        }
        if(btnmrules == v)
        {
            Intent intent = new Intent(this,RulesActivity.class);
            startActivityForResult(intent,4);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1)
        {
            if(resultCode==0)
            {
                //SharedPreferences.Editor editor=sp2.edit();
                //editor.putInt("buttonpressed",0);
                //editor.commit();
            }
            if(resultCode==1)
            {
                //SharedPreferences.Editor editor=sp2.edit();
                //editor.putInt("buttonpressed",0);
                //editor.commit();
                Intent intent = new Intent(this,LeaderBoardActivity.class);
                startActivityForResult(intent,2);
            }
        }
        if(requestCode == 2)
        {
            if(resultCode == 1)
            {
                Createregisterdialog();
            }
            if(resultCode == 2)
            {
                Intent intent = new Intent(this,RulesActivity.class);
                startActivityForResult(intent,4);
            }
        }
        if(requestCode ==3)
        {
            if(resultCode ==RESULT_OK)
            {
                bitmap=(Bitmap) data.getExtras().get("data");
                ivmpic.setImageBitmap(bitmap);
            }
        }
        if(requestCode == 4)
        {
            if(resultCode == 1)
            {
                Createregisterdialog();
            }
            if(resultCode == 2)
            {
                Intent intent = new Intent(this,LeaderBoardActivity.class);
                startActivityForResult(intent,2);
            }
        }
    }

    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID,"Example Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager =getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
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