package com.romes.quotesapplication;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {
    private UserDao mUserDao;
    private LiveData<User> currentUser;
//    boolean d;
//    boolean isValid;
//    int progressComplete = 0;
    UserRepository(Application application){
        QuoteRoomDatabase db =QuoteRoomDatabase.getDataBase(application);
        mUserDao = db.userDao();
        currentUser = mUserDao.getCurrentUser();
    }
    LiveData<User> getCurrentUser(){
        return currentUser;
    }


    public User isValidAccount(String username, String password) {
        try {
          User toCheckUser = new isValidAccountAsyncTask(mUserDao).execute(username,password).get();
          return toCheckUser;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }


         }




    private class isValidAccountAsyncTask extends AsyncTask<String,Void,User> {
        private UserDao mAsyncTaskDao;

        isValidAccountAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected User doInBackground(String... strings) {
            try{
                User user = mAsyncTaskDao.getUser(strings[0]);
                return user;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
        }
    }



    void insert(User user){
        new insertUserAsyncTask(mUserDao).execute(user);

    }
    private class insertUserAsyncTask extends AsyncTask<User,Void,Void>{
        private UserDao mAsyncTaskDao;
        insertUserAsyncTask(UserDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.insert(users[0]);
            return null;
        }
    }

    void delete(User user){
        new deleteUserAsyncTask(mUserDao).execute(user);

    }

    private class deleteUserAsyncTask extends AsyncTask<User,Void,Void>{
        private UserDao mAsyncTaskDao;
        deleteUserAsyncTask(UserDao dao){
            mAsyncTaskDao =dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.deleteUser(users[0]);
            return null;
        }
    }

    void deleteAll(){
        new deleteAllAsyncTask(mUserDao).execute();
    }
    private class deleteAllAsyncTask extends AsyncTask<User,Void,Void>{
        private UserDao mAsyncTaskDao;
        deleteAllAsyncTask(UserDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    void update(User user){
        new updateAsyncTask(mUserDao).execute(user);
    }
    private class updateAsyncTask extends  AsyncTask<User,Void,Void>{
        private UserDao mAsyncTaskDao;
        updateAsyncTask(UserDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.update(users[0]);
            return null;
        }
    }

}
