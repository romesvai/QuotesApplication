package com.romes.quotesapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuoteVIewModel extends AndroidViewModel {
    private QuoteRepository mRepository;
    private LiveData<List<Quote>>  mAllQuotes;
    public QuoteVIewModel(@NonNull Application application) {
        super(application);
        mRepository = new QuoteRepository(application);
        mAllQuotes = mRepository.getAllQuotes();

    }
    LiveData<List<Quote>> getAllQuotes(){
        return mAllQuotes;
    }
    public void insert(Quote quote){
        mRepository.insert(quote);
    }
    public void deleteAll(){
        mRepository.deleteAll();

    }
    public void deleteQuote(Quote quote){
        mRepository.deleteQuote(quote);
    }

    public void updateQuote(Quote quote){
        mRepository.updateQuote(quote);
    }
}
