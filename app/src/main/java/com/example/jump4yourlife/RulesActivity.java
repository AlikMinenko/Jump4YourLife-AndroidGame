package com.example.jump4yourlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    TextView tvmrules; // text view in rules activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvmrules=findViewById(R.id.tvrules);
        tvmrules.setPaintFlags(tvmrules.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main2,menu);
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
        if(id == R.id.menuleader)
        {
            Intent intent =getIntent();
            setResult(2,intent);
            finish();
        }

        return true;
    }
}