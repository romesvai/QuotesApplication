package com.romes.quotesapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private UserViewModel mUserViewModelProfile;
    private TextView mUsername;
    private TextView mEmail;
    private TextView mGender;
    String username;
    public static final String profileEditUsername = "com.romes.android.PROFILE_USERNAME";
    private final int editREQUESTCODE = 10;
    SharedPreferences pref;
    private ImageView ProfilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        String password = "notNeeded";
        mUserViewModelProfile = ViewModelProviders.of(ProfileActivity.this).get(UserViewModel.class);
        mUsername = findViewById(R.id.name_text);
        mEmail = findViewById(R.id.email_text);
        mGender = findViewById(R.id.gender_text);
        pref = getSharedPreferences("UserLogin", MODE_PRIVATE);
        username = pref.getString("username", "DEFAULT");
        ProfilePic = findViewById(R.id.profile_image);


        User currentUser = mUserViewModelProfile.UserForValidation(username, password);
        mUsername.setText(currentUser.getUsername());
        mEmail.setText(currentUser.getEmail());
        mGender.setText(currentUser.getGender());


    }

    public void editProfileDetailsFunction(View view) {
        Intent editProfileIntent = new Intent(ProfileActivity.this, ProfileEditDetailsActivity.class);
        editProfileIntent.putExtra(profileEditUsername, username);
        startActivityForResult(editProfileIntent, editREQUESTCODE);

    }

    public void editImageFunction(View view) {
        final CharSequence[] options = {"Take Photo", "Choose from gallery", "Cancel"};
        final AlertDialog.Builder alertDialogForImage = new AlertDialog.Builder(ProfileActivity.this);
        alertDialogForImage.setTitle("Edit Image");
        //   alertDialogForImage.setMessage("Do you want to capture image from phone or select from gallery");
        alertDialogForImage.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (options[i].equals("Take Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 11);
                } else if (options[i].equals("Choose from gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 12);
                } else if (options[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }


            }

        });
        alertDialogForImage.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == editREQUESTCODE) {
            if (resultCode == RESULT_OK) {
                username = data.getStringExtra(ProfileEditDetailsActivity.editedUsername);
                pref = getSharedPreferences("UserLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.putString("username", username);
                editor.commit();
                Intent restartIntent = getIntent();
                finish();
                startActivity(restartIntent);
            }
        }
        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                ProfilePic.setImageBitmap(selectedImage);

            }
        }
        if (requestCode == 12) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (selectedImage != null) {
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        ProfilePic.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        cursor.close();

                    }
                }
            }
        }
    }
}