package com.romes.quotesapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * from user_table where user_table.username LIKE :username")
    User getUser(String username);

    @Query("SELECT * from user_table LIMIT 1")
    User[] getAnyUser();



    @Query("DELETE from user_table")
    void deleteAll();

    @Query("SELECT * FROM user_table LIMIT 1")
    LiveData<User> getCurrentUser();


    @Update
    void update(User user);





    @Delete
    void deleteUser(User user);
}

