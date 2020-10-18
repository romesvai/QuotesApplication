package com.romes.quotesapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository mRepository;
    private LiveData<User> currentUser;
    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = new UserRepository(application);
        currentUser = mRepository.getCurrentUser();

    }
    LiveData<User> getCurrentUser(){
        return currentUser;
    }
    public void insert(User user){
        mRepository.insert(user);
    }
    public void deleteAll(){
        mRepository.deleteAll();

    }
    public void deleteQuote(User user){
        mRepository.delete(user);
    }

    public void update(User user){
        mRepository.update(user);
    }
    User UserForValidation(String username, String password)
    {
        return mRepository.isValidAccount(username, password);
    }

    }



