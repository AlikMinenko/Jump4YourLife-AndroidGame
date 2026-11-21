package com.example.jump4yourlife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class InformationOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASENAME="PlayerInformation.db"; // the name of the data base
    public static final String TABLE_PLAYERINFORMATION="tblplayerinformation"; // the name of the table
    public static final int DATABASEVERSION=1; // the data base version

    public static final String COLUMN_ID="playerId"; // the first column - id
    public static final String COLUMN_PLACE="playerPlace"; // the second column - place
    public static final String COLUMN_PICTURE="playerBitamp"; // the third column - bitmap
    public static final String COLUMN_NAME="playerName"; // the fourth column - name
    public static final String COLUMN_HEIGHT="playerHeight"; // the fifth column - height

    private static final String CREATE_TABLE_PLAYERINFORMATION="CREATE TABLE IF NOT EXISTS " +
            TABLE_PLAYERINFORMATION + "(" + COLUMN_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PLACE + " INTEGER," + COLUMN_PICTURE + " BLOB,"
            + COLUMN_NAME +" VARCHAR," + COLUMN_HEIGHT +   " INTEGER "  +   ");"; // the creation of the table


    String []allColumns={InformationOpenHelper.COLUMN_ID, InformationOpenHelper.COLUMN_PLACE,InformationOpenHelper.COLUMN_PICTURE,
            InformationOpenHelper.COLUMN_NAME,InformationOpenHelper.COLUMN_HEIGHT}; // a string[] of all the columns

    SQLiteDatabase database; // the sql data base

    public InformationOpenHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_PLAYERINFORMATION);
        Log.i("onlydatapls", "Table customer created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERINFORMATION);
        onCreate(sqLiteDatabase);
    }

    public void Open(){
        database = this.getWritableDatabase();
        Log.i("onlydatapls", "Database connection open");
    }

    public byte [] ConvertToByte1(Bitmap bitmap)
    {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] Image = stream.toByteArray();
            return Image;
        }
        return null;
    }

    public PlayerInformation createPlayerInformation(PlayerInformation PI)
    {
        ContentValues values=new ContentValues();
        values.put(InformationOpenHelper.COLUMN_PLACE, PI.getPlace());
        byte[] b = ConvertToByte1(PI.getBitmap());
        values.put(InformationOpenHelper.COLUMN_PICTURE, b);
        values.put(InformationOpenHelper.COLUMN_NAME, PI.getNickName());
        values.put(InformationOpenHelper.COLUMN_HEIGHT, PI.getHeight());
        long insertId=database.insert(InformationOpenHelper.TABLE_PLAYERINFORMATION, null, values);
        Log.i("onlydatapls", "Customer " + insertId + "insert to database");
        PI.setId(insertId);
        return PI;
    }

    public ArrayList<PlayerInformation> getAllPlayerInformations() {

        ArrayList<PlayerInformation> list = new ArrayList<PlayerInformation>();
        Cursor cursor=database.query(InformationOpenHelper.TABLE_PLAYERINFORMATION, allColumns, null, null, null, null, COLUMN_HEIGHT + " DESC");
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                long id=cursor.getLong(cursor.getColumnIndex(InformationOpenHelper.COLUMN_ID));
                int place=cursor.getInt(cursor.getColumnIndex(InformationOpenHelper.COLUMN_PLACE));
                byte[] b =cursor.getBlob(cursor.getColumnIndex(InformationOpenHelper.COLUMN_PICTURE));
                Bitmap bitmap =null;
                if(b != null && b.length >0){
                    bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
                }
                String name=cursor.getString(cursor.getColumnIndex(InformationOpenHelper.COLUMN_NAME));
                int height=cursor.getInt(cursor.getColumnIndex(InformationOpenHelper.COLUMN_HEIGHT));
                PlayerInformation PI=new PlayerInformation(id,place,name,height,bitmap);
                list.add(PI);
            }
        }
        return list;
    }

    public PlayerInformation getPlayerInformationById(long rowId)
    {
        Cursor cursor=database.query(InformationOpenHelper.TABLE_PLAYERINFORMATION, allColumns, InformationOpenHelper.COLUMN_ID + "=" +rowId, null, null, null, COLUMN_HEIGHT + " DESC");
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            long id=cursor.getLong(cursor.getColumnIndex(InformationOpenHelper.COLUMN_ID));
            int place=cursor.getInt(cursor.getColumnIndex(InformationOpenHelper.COLUMN_PLACE));
            byte[] b =cursor.getBlob(cursor.getColumnIndex(InformationOpenHelper.COLUMN_PICTURE));
            Bitmap bitmap =null;
            if(b != null && b.length >0){
                bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
            }            String name=cursor.getString(cursor.getColumnIndex(InformationOpenHelper.COLUMN_NAME));
            int height=cursor.getInt(cursor.getColumnIndex(InformationOpenHelper.COLUMN_HEIGHT));
            PlayerInformation PI=new PlayerInformation(id,place,name,height,bitmap);
            return PI;
        }
        return null;
    }

    public long deleteAll() {
        return database.delete(InformationOpenHelper.TABLE_PLAYERINFORMATION, null, null);
    }

    public long deletePlayerInformationByRow(long rowId) {
        return database.delete(InformationOpenHelper.TABLE_PLAYERINFORMATION, InformationOpenHelper.COLUMN_ID + "=" + rowId, null);
    }

    public long updateByRow(PlayerInformation PI)
    {
        ContentValues values=new ContentValues();
        values.put(InformationOpenHelper.COLUMN_ID, PI.getId());
        values.put(InformationOpenHelper.COLUMN_PLACE, PI.getPlace());
        byte[] b = ConvertToByte1(PI.getBitmap());
        values.put(InformationOpenHelper.COLUMN_PICTURE,b);
        values.put(InformationOpenHelper.COLUMN_NAME, PI.getNickName());
        values.put(InformationOpenHelper.COLUMN_HEIGHT, PI.getHeight());
        return database.update(InformationOpenHelper.TABLE_PLAYERINFORMATION, values, InformationOpenHelper.COLUMN_ID +"=" + PI.getId(), null);
    }

//    public Cursor getAllID()
//    {
//        String[] Idcolumn = {COLUMN_ID};
//        return database.query(TABLE_PLAYERINFORMATION, Idcolumn, null, null, null, null, COLUMN_HEIGHT + " DESC");
//    }
}
