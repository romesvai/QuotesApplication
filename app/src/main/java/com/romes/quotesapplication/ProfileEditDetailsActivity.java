package com.romes.quotesapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class ProfileEditDetailsActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mEmail;
    private EditText mOldPassword;
    private EditText mNewPassword;
    private CheckBox mShowPasswordOld;
    private CheckBox mShowPasswordNew;
    private TextView mInvalidUsername;
    private TextView mInvalidPassword;
    private TextView mInvalidEmail;
    private String gender;
    private String username;
    private String email;
    private String oldPassword;
    private String newPassword;
    private UserViewModel mUserViewModelEdit;
    private Button EditButton;
    private TextView getmInvalidPasswordTwo;
    private Button EditImageButton;
    String currentUsername;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String editedUsername="com.romes.android.EditedUSERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_details);
        mUsername = findViewById(R.id.username_editTextFieldProfileEdit);
        mEmail = findViewById(R.id.editTextTextEmailAddressProfileEdit);
        mOldPassword = findViewById(R.id.password_editTextProfileEdit);
        mNewPassword = findViewById(R.id.newPasswordEditText);
        getmInvalidPasswordTwo =findViewById(R.id.oldPasswordInvalid);
        mInvalidPassword =findViewById(R.id.invalidPasswordProfileEdit);
        mInvalidEmail = findViewById(R.id.invalidEmailTextProfileEdit);
        mInvalidUsername = findViewById(R.id.invalidUsernameTextProfileEdit);
        mShowPasswordOld = findViewById(R.id.showPasswordCheckBoxProfileEdit);
        mShowPasswordNew = findViewById(R.id.showNewPasswordCheckBox);
        mInvalidPassword.setVisibility(View.INVISIBLE);
        EditButton = findViewById(R.id.signUpButtonEditProfile);
        Intent intent = getIntent();
        currentUsername=intent.getStringExtra(ProfileActivity.profileEditUsername);

        mUserViewModelEdit = ViewModelProviders.of(ProfileEditDetailsActivity.this).get(UserViewModel.class);



                                        EditButton.setOnClickListener(new View.OnClickListener(){
                                                   public void onClick (View view){
                                                   User currentUser = mUserViewModelEdit.UserForValidation(currentUsername, "***");
                                                   int selectedUserID = currentUser.getUserId();
                                                   if (mEmail.getText().toString().isEmpty()) {
                                                       Toast.makeText(ProfileEditDetailsActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                                                       mInvalidEmail.setVisibility(View.VISIBLE);
                                                   } else {
                                                       mInvalidEmail.setVisibility(View.INVISIBLE);
                                                   }
                                                   if (mUsername.getText().toString().isEmpty()) {
                                                       Toast.makeText(ProfileEditDetailsActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                                                       mInvalidUsername.setVisibility(View.VISIBLE);
                                                   } else {
                                                       mInvalidUsername.setVisibility(View.INVISIBLE);
                                                   }
                                                   if (mNewPassword.getText().toString().isEmpty()) {
                                                       Toast.makeText(ProfileEditDetailsActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                                                       mInvalidPassword.setVisibility(View.VISIBLE);
                                                   } else {
                                                       mInvalidPassword.setVisibility(View.INVISIBLE);
                                                   }
                                                   if (mOldPassword.getText().toString().isEmpty()) {
                                                       getmInvalidPasswordTwo.setVisibility(View.VISIBLE);
                                                   } else {
                                                       getmInvalidPasswordTwo.setVisibility(View.INVISIBLE);
                                                   }
                                                   if ((mOldPassword.getText().toString()).equals(currentUser.getPassword())) {
                                                       mInvalidPassword.setVisibility(View.INVISIBLE);
                                                   } else {
                                                       mInvalidPassword.setVisibility(View.VISIBLE);
                                                       if(mOldPassword.getText().toString().isEmpty()){
                                                           mInvalidPassword.setVisibility(View.INVISIBLE);
                                                       }
                                                   }
                                                   if (mEmail.getText().toString().trim().matches(emailPattern)) {
                                                       mInvalidEmail.setVisibility(View.INVISIBLE);
                                                   } else {
                                                       mInvalidEmail.setVisibility(View.VISIBLE);
                                                   }
                                                   if (mInvalidUsername.getVisibility() == View.INVISIBLE && mInvalidPassword.getVisibility() == View.INVISIBLE && mInvalidEmail.getVisibility() == View.INVISIBLE && getmInvalidPasswordTwo.getVisibility() == View.INVISIBLE) {
                                                       mUserViewModelEdit = ViewModelProviders.of(ProfileEditDetailsActivity.this).get(UserViewModel.class);
                                                       username = mUsername.getText().toString();
                                                       email = mEmail.getText().toString();
                                                       oldPassword = mOldPassword.getText().toString();
                                                       newPassword = mNewPassword.getText().toString();
                                                       User user = new User(selectedUserID, username, newPassword, email, gender);
                                                       mUserViewModelEdit.update(user);
                                                       Intent goBackIntent = new Intent();
                                                       goBackIntent.putExtra(editedUsername, username);
                                                       setResult(RESULT_OK, goBackIntent);
                                                       finish();

                                                   }


                                               }
                                               });



        mShowPasswordOld.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else{
                    mOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }


            }
        });
        mShowPasswordNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mShowPasswordNew =findViewById(R.id.showNewPasswordCheckBox);

    }



    public void onRadioButtonClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){

            case R.id.radio_maleEdit:
                if(checked){
                    gender = "Male";
                    break;
                }
            case R.id.radio_femaleEdit:
                if(checked){
                    gender ="Female";
                    break;
                }
            case R.id.radio_othersEdit:
                if(checked){
                    gender="others";
                    break;
                }
            default:
                break;
        }
    }
}