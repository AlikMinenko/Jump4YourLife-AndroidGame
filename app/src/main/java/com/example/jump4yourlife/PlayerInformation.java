package com.example.jump4yourlife;

import android.graphics.Bitmap;

public class PlayerInformation {

    private long Id; // the id of the player
    private int place; // the place that the player got after playing the game
    private String NickName; // the nickname of the player
    private int height; // the height that the player got after playing the game
    private Bitmap bitmap; // the picture that the player took

    public PlayerInformation(long Id, int place, String nickName, int height, Bitmap bitmap) {
        this.Id =Id;
        this.place = place;
        this.NickName = nickName;
        this.height = height;
        this.bitmap = bitmap;
    }

    public PlayerInformation( int place, String nickName, int height, Bitmap bitmap) {
        this.Id =0;
        this.place = place;
        this.NickName = nickName;
        this.height = height;
        this.bitmap = bitmap;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int score) {
        this.height = height;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
