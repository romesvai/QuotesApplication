package com.romes.quotesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private UserViewModel mUserViewModelProfile;
    private TextView mUsername;
    private TextView mEmail;
    private TextView mGender;
    String username;
    private Activity context;
    public static final String profileEditUsername="com.romes.android.PROFILE_USERNAME";
    private final int editREQUESTCODE=10;
    SharedPreferences pref;
    private ImageView ProfilePic;
    private Button EditImageButton;
    private Button EditDetailsButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        Intent intent = getActivity().getIntent();
        String password = "notNeeded";
        mUserViewModelProfile = ViewModelProviders.of(this).get(UserViewModel.class);
        mUsername = v.findViewById(R.id.name_text);
        mEmail = v.findViewById(R.id.email_text);
        mGender = v.findViewById(R.id.gender_text);
        pref = getActivity().getSharedPreferences("UserLogin", Context.MODE_PRIVATE);
        username = pref.getString("username","DEFAULT");
        ProfilePic =v.findViewById(R.id.profile_image);
        EditImageButton=v.findViewById(R.id.edit_image_button);
        EditDetailsButton = v.findViewById(R.id.edit_profile_button);

        EditImageButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] options = {"Take Photo", "Choose from gallery", "Cancel"};
                final AlertDialog.Builder alertDialogForImage = new AlertDialog.Builder(context);
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
        });
        EditDetailsButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfileIntent = new Intent(context,ProfileEditDetailsActivity.class);
                editProfileIntent.putExtra(profileEditUsername,username);
                startActivityForResult(editProfileIntent,editREQUESTCODE);

            }
        });

        if(pref.contains("username")){
        User currentUser = mUserViewModelProfile.UserForValidation(username,password);
        mUsername.setText(currentUser.getUsername());
        mEmail.setText(currentUser.getEmail());
        mGender.setText(currentUser.getGender());}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


//    public void editProfileDetailsFunction(View view){
//        Intent editProfileIntent = new Intent(context,ProfileEditDetailsActivity.class);
//        editProfileIntent.putExtra(profileEditUsername,username);
//        startActivityForResult(editProfileIntent,editREQUESTCODE);
//
//    }

//    public void editImageFunction(View view){
//        final CharSequence[] options = {"Take Photo", "Choose from gallery", "Cancel"};
//        final AlertDialog.Builder alertDialogForImage = new AlertDialog.Builder(context);
//        alertDialogForImage.setTitle("Edit Image");
//        //   alertDialogForImage.setMessage("Do you want to capture image from phone or select from gallery");
//        alertDialogForImage.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (options[i].equals("Take Photo")) {
//                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, 11);
//                } else if (options[i].equals("Choose from gallery")) {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto, 12);
//                } else if (options[i].equals("Cancel")) {
//                    dialogInterface.dismiss();
//                }
//
//
//            }
//
//        });
//        alertDialogForImage.show();


//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==editREQUESTCODE){
            if(resultCode==Activity.RESULT_OK){
                username = data.getStringExtra(ProfileEditDetailsActivity.editedUsername);
                pref = context.getSharedPreferences("UserLogin",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.putString("username",username);
                editor.commit();
                Intent restartIntent = context.getIntent();
                context.finish();
                startActivity(restartIntent);
            }
        }
        if(requestCode==11){
            if(resultCode==Activity.RESULT_OK){
                Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                ProfilePic.setImageBitmap(selectedImage);

            }
        }
        if(requestCode==12){
            if(resultCode== Activity.RESULT_OK){
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if(selectedImage!=null){
                    Cursor cursor = context.getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        ProfilePic.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        cursor.close();}

                }
            }
        }
    }
    }
