package com.romes.quotesapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserLoginActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private UserViewModel mUserViewModel;
    private TextView mInvalidUsername;
    private TextView mInvalidPassword;
    private Button logIn;
    private CheckBox mShowPassword;
    public static final String logInUsername="com.romes.android.USERNAME";
    public static final String LogInPassword="com.romes.anrdoid.PASSWORD";
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mPassword = (EditText)findViewById(R.id.password_editTextlogin);
        mUsername=(EditText)findViewById(R.id.username_editTextFieldlogin);
        logIn =(Button)findViewById(R.id.loginButton);
        mInvalidPassword=(TextView)findViewById(R.id.invalidPasswordtextlogin);
        mInvalidUsername =(TextView)findViewById(R.id.invalidUsernameTextlogin);
        mShowPassword = (CheckBox)findViewById(R.id.showPasswordCheckBoxlogin);
        preferences = getSharedPreferences("UserLogin",MODE_PRIVATE);
        mShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


            logIn.setOnClickListener(new View.OnClickListener(){
                private String passwordCheck;
                private String password;
                private String username;
                @Override
                public void onClick(View view) {
                    username = mUsername.getText().toString();
                    password = mPassword.getText().toString();
                   User ToCheckUser = mUserViewModel.UserForValidation(username,password);
                   if(ToCheckUser!=null) {
                       if (ToCheckUser.getPassword().equals(password)) {
                           mInvalidPassword.setVisibility(View.INVISIBLE);
                           mInvalidUsername.setVisibility(View.VISIBLE);
                           Toast.makeText(UserLoginActivity.this, "Congratulations you are logged in as  " + username, Toast.LENGTH_LONG).show();
                           SharedPreferences.Editor editor = preferences.edit();
                           editor.putString("username", username);
                           editor.putString("password", password);
                           editor.commit();
                           Intent loginIntent = new Intent(UserLoginActivity.this, MainActivity.class);
                           startActivity(loginIntent);
                       } else {
                           Toast.makeText(UserLoginActivity.this, "Failed. password doesnt match", Toast.LENGTH_LONG).show();
                           mInvalidPassword.setVisibility(View.VISIBLE);
                           mInvalidUsername.setVisibility(View.INVISIBLE);


                       }
                   }
                   else{
                       Toast.makeText(UserLoginActivity.this,"There is no such user",Toast.LENGTH_LONG).show();
                       mInvalidUsername.setVisibility(View.VISIBLE);
                       mInvalidPassword.setVisibility(View.INVISIBLE);
                       AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserLoginActivity.this);
                       alertDialog.setTitle("Username  doesn't exist");
                       alertDialog.setMessage("Do you want to sign up?");
                       alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               Intent loginToSignup = new Intent(UserLoginActivity.this,UserSignUpActivity.class);
                               startActivity(loginToSignup);
                           }
                       });
                       alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               Toast.makeText(getApplicationContext(),"You canceled",Toast.LENGTH_SHORT).show();
                           }
                       });
                       alertDialog.show();



                   }


                }
            });
        }




    }
