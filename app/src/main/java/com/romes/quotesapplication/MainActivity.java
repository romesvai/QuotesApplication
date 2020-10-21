package com.romes.quotesapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Quote> mQuoteData;
    private QuoteAdapter mAdapter;
    private static int REQUEST_CODE = 1;
    private static int upREQUEST_CODE = 2;
    private static int userREQUEST_CODE = 3;
    private static int logInREQUEST_CODE = 4;
    private TextView mQuoteTextHere;
    private QuoteVIewModel mQuoteViewModel;
    private Button editButton;
    private Quote updateQuote;
    private int selectedQuoteID;
    public static final String popQuote = "com.romes.example.Quote";
    public static final String popAuthor = "com.romes.example.Author";
    public static final String popDescription = "com.romes.example.Description";
    public static final String id = "com.romes.example.id";
    public static final String loginUsername = "com.romes.example.USERNAME";
    private UserViewModel mUserViewModel;
    private MenuItem prevMenuItem;
    private ViewPager viewPager;
    //    public String logInUsername;
//    public String logInPassword;
    SharedPreferences pref;
    BottomNavigationView mMainBottomNavView;
    private Runnable mOnActivityResultTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        mRecyclerView = findViewById(R.id.recyclerView);
//        mQuoteViewModel = ViewModelProviders.of(this).get(QuoteVIewModel.class);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mQuoteData = new ArrayList<>();
//        mAdapter = new QuoteAdapter(this, mQuoteData);
//        mRecyclerView.setAdapter(mAdapter);
//        mQuoteViewModel = ViewModelProviders.of(this).get(QuoteVIewModel.class);
//        mQuoteViewModel.getAllQuotes().observe(this, new Observer<List<Quote>>() {
//            //  @Override
//            public void onChanged(List<Quote> quotes) {
//                mAdapter.setQuotes(quotes);
//            }
//        });
//        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
//        mUserViewModel.getCurrentUser().observe(this, new Observer<User>() {
//            @Override
//            public void onChanged(User user) {
//                //  mUserViewModel.update(user);
//            }
//        });
        mMainBottomNavView = findViewById(R.id.bottom_nav);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        pref = getSharedPreferences("UserLogin", MODE_PRIVATE);
        viewPager = findViewById(R.id.viewPager);
        mMainBottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.Profile) {
                    viewPager.setCurrentItem(1);
                    return true;

                }
                if (id == R.id.QuoteNAV) {
                    viewPager.setCurrentItem(0);
                    return true;
                }

                return false;

            }

        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    mMainBottomNavView.getMenu().getItem(0).setChecked(false);

                mMainBottomNavView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = mMainBottomNavView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUpViewPager(viewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            Toast.makeText(this, "Deleting all data", Toast.LENGTH_SHORT).show();
            mQuoteViewModel.deleteAll();
            mUserViewModel.deleteAll();
            return true;
        }
        if (id == R.id.sign_up) {
            Intent signUpIntent = new Intent(MainActivity.this, UserSignUpActivity.class);
            startActivityForResult(signUpIntent, userREQUEST_CODE);

        }
        if (id == R.id.login) {
            if (item.getTitle().equals(pref.getString("username", "error"))) {
                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                profileIntent.putExtra(loginUsername, pref.getString("username", "error"));
                startActivity(profileIntent);

            } else {
                Intent logInIntent = new Intent(MainActivity.this, UserLoginActivity.class);
                startActivity(logInIntent);
            }


        }
        if (id == R.id.logout) {
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            Intent restartIntent = getIntent();
            finish();
            startActivity(restartIntent);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(@Nullable View view, @NonNull Menu menu) {
        if (pref.contains("username")) {
            MenuItem Menu = menu.findItem(R.id.login);
            Menu.setTitle(pref.getString("username", "error"));
            MenuItem logOut = menu.findItem(R.id.logout);
            logOut.setEnabled(true);

        } else {
            MenuItem Menu = menu.findItem(R.id.login);
            Menu.setTitle("LOG IN");
            MenuItem logOut = menu.findItem(R.id.logout);
            logOut.setEnabled(false);
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    private void setUpViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        QuoteFragment quoteFragment = new QuoteFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        adapter.addFragment(quoteFragment);
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);

    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {

        //   Random r = new Random();
        // final int i1 = r.nextInt(7);
        super.onActivityResult(requestCode, resultCode, data);


//        mOnActivityResultTask = new Runnable() {
//            @Override
//            public void run() {
//                if (requestCode == upREQUEST_CODE) {
//                    if (resultCode == RESULT_OK) {
//                        String quote = data.getStringExtra(UpdateQuote.upQuote);
//                        String author = data.getStringExtra(UpdateQuote.upAuthor);
//                        String description = data.getStringExtra(UpdateQuote.upDescription);
//                        int selectedQuoteID = data.getIntExtra(UpdateQuote.upID, 0);
//                        Quote updateQuote = new Quote(selectedQuoteID, quote, author, description);
////                if(updateQuote!=null){
//                        mQuoteViewModel.updateQuote(updateQuote);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
//                if (requestCode == REQUEST_CODE) {
//                    if (resultCode == RESULT_OK) {
//                        String quote = data.getStringExtra(AddQuoteActivity.Quote);
//                        String author = data.getStringExtra(AddQuoteActivity.Author);
//                        String description = data.getStringExtra(AddQuoteActivity.Description);
//                        Quote newQuote = new Quote(quote, author, description);
//                        mQuoteViewModel.insert(newQuote);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
//                if (requestCode == userREQUEST_CODE) {
//                    if (resultCode == RESULT_OK) {
//                        String username = data.getStringExtra(UserSignUpActivity.userUsername);
//                        String email = data.getStringExtra(UserSignUpActivity.userEmail);
//                        String password = data.getStringExtra(UserSignUpActivity.userPassword);
//                        String gender = data.getStringExtra(UserSignUpActivity.userGender);
//                        User user = new User(username,password,email,gender);
//                        mUserViewModel.insert(user);
//
//
//                    }
//                }
//
//            }
//        };
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                String quote = data.getStringExtra(AddQuoteActivity.Quote);
//                String author = data.getStringExtra(AddQuoteActivity.Author);
//                String description = data.getStringExtra(AddQuoteActivity.Description);
//                Quote newQuote = new Quote(quote, author, description);
//                mQuoteViewModel.insert(newQuote);
//                mAdapter.notifyDataSetChanged();
//            }
//        }
//        if (requestCode == upREQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                String quote = data.getStringExtra(UpdateQuote.upQuote);
//                String author = data.getStringExtra(UpdateQuote.upAuthor);
//                String description = data.getStringExtra(UpdateQuote.upDescription);
//                int selectedQuoteID = data.getIntExtra(UpdateQuote.upID, 0);
//                Quote updateQuote = new Quote(selectedQuoteID, quote, author, description);
////                if(updateQuote!=null){
//                mQuoteViewModel.updateQuote(updateQuote);
//                mAdapter.notifyDataSetChanged();
//            }
//        }

        if (requestCode == userREQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String username = data.getStringExtra(UserSignUpActivity.userUsername);
                String email = data.getStringExtra(UserSignUpActivity.userEmail);
                String password = data.getStringExtra(UserSignUpActivity.userPassword);
                String gender = data.getStringExtra(UserSignUpActivity.userGender);
                User user = new User(username, password, email, gender);
                mUserViewModel.insert(user);


            }
        }
    }


//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//            if (mOnActivityResultTask != null) {
//                mOnActivityResultTask.run();
//                mOnActivityResultTask = null;
//            }
//
//        }
//    }
}





