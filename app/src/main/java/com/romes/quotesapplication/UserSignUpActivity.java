package com.romes.quotesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class UserSignUpActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    private CheckBox mShowPassword;
    private Button mSignUpButton;
    private TextView mInvalidUsername;
    private TextView mInvalidEmail;
    private TextView mInvalidPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String userUsername = "com.romes.android.USERNAME";
    public static final String userEmail = "com.romes.android.EMAIL";
    public static final String userPassword ="com.romes.android.PASSWORD";
    public static final String userGender="com.romes.android.GENDER";
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        mUsername = (EditText)findViewById(R.id.username_editTextField);
        mEmail =(EditText)findViewById(R.id.editTextTextEmailAddress);
        mPassword=(EditText)findViewById(R.id.password_editText);
        mShowPassword=(CheckBox)findViewById(R.id.showPasswordCheckBox);
        mSignUpButton=(Button)findViewById(R.id.signUpButton);
        mInvalidUsername=(TextView)findViewById(R.id.invalidUsernameText);
        mInvalidEmail=(TextView)findViewById(R.id.invalidEmailText);
        mInvalidPassword=(TextView)findViewById(R.id.invalidPasswordtext);
        mShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){

                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else{
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEmail.getText().toString().isEmpty()){
                    Toast.makeText(UserSignUpActivity.this,"Enter email",Toast.LENGTH_SHORT).show();
                    mInvalidEmail.setVisibility(View.VISIBLE);
                }
                else{
                    mInvalidEmail.setVisibility(View.INVISIBLE);
                }
                if(mUsername.getText().toString().isEmpty()){
                    Toast.makeText(UserSignUpActivity.this,"Enter username",Toast.LENGTH_SHORT).show();
                    mInvalidUsername.setVisibility(View.VISIBLE);
                }
                else{
                    mInvalidUsername.setVisibility(View.INVISIBLE);
                }
                if(mPassword.getText().toString().isEmpty()){
                    Toast.makeText(UserSignUpActivity.this,"Enter email",Toast.LENGTH_SHORT).show();
                    mInvalidPassword.setVisibility(View.VISIBLE);
                }
                else{
                    mInvalidPassword.setVisibility(View.INVISIBLE);
                }
                if(mEmail.getText().toString().trim().matches(emailPattern)){
                    mInvalidEmail.setVisibility(View.INVISIBLE);
                }
                else{
                    Toast.makeText(UserSignUpActivity.this,"Invalid email address",Toast.LENGTH_SHORT).show();
                    mInvalidEmail.setVisibility(View.VISIBLE);
                }
                if(mInvalidUsername.getVisibility()==View.INVISIBLE && mInvalidPassword.getVisibility()==View.INVISIBLE && mInvalidEmail.getVisibility()==View.INVISIBLE ){
                    Intent userReplyIntent = new Intent();
                    String username = mUsername.getText().toString();
                    String email = mEmail.getText().toString();
                    String password = mPassword.getText().toString();
                    userReplyIntent.putExtra(userGender,gender);
                    userReplyIntent.putExtra(userUsername,username);
                    userReplyIntent.putExtra(userEmail,email);
                    userReplyIntent.putExtra(userPassword,password);
                    setResult(RESULT_OK,userReplyIntent);
                    finish();

                }


            }
        });

    }

    public void onRadioButtonClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){
            case R.id.radio_male:
                if(checked){
                gender ="Male";
                break;}
            case R.id.radio_female:
                if(checked){
                    gender="Female";
                    break;
                }
            case R.id.radio_others:
                if(checked){
                    gender="Others";
                    break;
                }
            default:
                //
                break;
        }
    }
}