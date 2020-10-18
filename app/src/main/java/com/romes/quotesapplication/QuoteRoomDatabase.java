package com.romes.quotesapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Quote.class,User.class},version = 6,exportSchema = false)
public abstract class QuoteRoomDatabase extends RoomDatabase {
    public abstract QuoteDao quoteDao();
    public abstract UserDao userDao();

    private static QuoteRoomDatabase INSTANCE;

    public static QuoteRoomDatabase getDataBase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuoteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), QuoteRoomDatabase.class, "quote_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }


        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final QuoteDao mDao;
        private final UserDao mUserDao;
        Quote quote = new Quote("The way I see it if you love the rainbow, you gotta put up with the rain", "Dolly Parton", "Life is journey meant to be embraced to the fullest");
        User user = new User(1,"guest","1234567","guest@gmail.com","Male");

        PopulateDbAsync(QuoteRoomDatabase db) {
            mDao = db.quoteDao();
            mUserDao = db.userDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            if (mDao.getAnyWord().length < 1) {
                mDao.deleteAll();
                mDao.insert(quote);
            }
            if(mUserDao.getAnyUser().length < 1){
                mUserDao.deleteAll();
                mUserDao.insert(user);

            }

            return null;
        }

    }
}
