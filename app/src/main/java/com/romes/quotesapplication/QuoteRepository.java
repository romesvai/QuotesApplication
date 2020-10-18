package com.romes.quotesapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class QuoteRepository {
    private QuoteDao mQuoteDao;
    private LiveData<List<Quote>> mAllQuotes;

    QuoteRepository(Application application){
        QuoteRoomDatabase db =QuoteRoomDatabase.getDataBase(application);
        mQuoteDao = db.quoteDao();
        mAllQuotes = mQuoteDao.getAllQuotes();
    }

    LiveData<List<Quote>> getAllQuotes(){
        return mAllQuotes;
    }
    public void insert(Quote quote){
        new insertAsyncTask(mQuoteDao).execute(quote);
    }

    private  class insertAsyncTask extends AsyncTask<Quote,Void,Void> {
        private QuoteDao mAsyncTaskDao;
        public insertAsyncTask(QuoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final  Quote ... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllQuotesAsyncTask extends AsyncTask<Quote,Void,Void>{
        private QuoteDao mAsyncTaskDao;
        deleteAllQuotesAsyncTask(QuoteDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Quote... quotes) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    public void deleteAll(){
        new deleteAllQuotesAsyncTask(mQuoteDao).execute();
    }

    private static class deleteQuoteAsyncTask extends  AsyncTask<Quote,Void,Void>{
        private QuoteDao mAsyncTaskDao;
        deleteQuoteAsyncTask(QuoteDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Quote... quotes) {
            mAsyncTaskDao.deleteQuote(quotes[0]);
            return null;
        }
    }
    public void deleteQuote(Quote quote){
        new deleteQuoteAsyncTask(mQuoteDao).execute(quote);
    }

    public void updateQuote(Quote quote){
        new updateQuoteAsyncTask(mQuoteDao).execute(quote);
        }
        private static class updateQuoteAsyncTask extends AsyncTask<Quote,Void,Void>{
        QuoteDao AsyncTaskDao;
        updateQuoteAsyncTask(QuoteDao dao){
            AsyncTaskDao = dao;
        }

            @Override
            protected Void doInBackground(Quote... quotes) {
                AsyncTaskDao.updateQuote(quotes[0]);
                return null;
            }
        }





}


