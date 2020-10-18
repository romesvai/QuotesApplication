package com.romes.quotesapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuoteDao {
    @Insert
    void insert(Quote quote);

    @Query("SELECT * from quote_table LIMIT 1")
    Quote[] getAnyWord();



    @Query("DELETE from quote_table")
    void deleteAll();


    @Query("SELECT * FROM quote_table ORDER BY quote ASC")
    LiveData<List<Quote>> getAllQuotes();

   // @Query("UPDATE quote_table SET quote=:quote ,author=:author,description=:description")
  //  void updateQuoteData(String quote,String author,String description);

    @Update
    void updateQuote(Quote... quote);



    @Delete
    void deleteQuote(Quote quote);
}
