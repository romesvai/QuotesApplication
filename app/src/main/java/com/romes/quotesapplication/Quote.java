package com.romes.quotesapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "quote_table")
public class Quote {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name="quote")
    private String quote;
    @ColumnInfo(name="author")
    private String author;
    @ColumnInfo(name="description")
    private String description;
    @Ignore
    public Quote(int id,String quote, String author, String description) {
        this.id = id;
        this.quote = quote;
        this.author = author;
        this.description = description;

    }
    public Quote(String quote, String author, String description) {
        this.quote = quote;
        this.author = author;
        this.description = description;

    }
    String getQuote(){
        return quote;
    }
    String getAuthor(){
        return author;
    }
    String getDescription(){
        return description;
    }
    public int getId(){return id;}
    public void setId(int id){
        this.id=id;
    }
}
