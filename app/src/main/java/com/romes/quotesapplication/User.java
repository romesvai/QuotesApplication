package com.romes.quotesapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;
    @ColumnInfo(name="username")
    private String username;
    @ColumnInfo(name="password")
    private String password;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name ="gender")
    private String gender;
//    @ColumnInfo(name="quoteId")
//    private int quoteID;
    @Ignore
    public User(String username,String password,String email,String gender){
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;

    }
    public User(int userId,String username,String password,String email,String gender){
        this.userId=userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
//        this.quoteID = quoteID;
    }

    String getUsername(){ return username;}
    String getPassword(){return password;}
    String getEmail(){return email;}
    String getGender(){return gender;}
    public int getUserId(){return userId;}
    public void setUserId(int UserId){
        this.userId=UserId;
    }

}
