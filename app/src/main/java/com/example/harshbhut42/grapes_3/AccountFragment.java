package com.example.harshbhut42.grapes_3;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private View mView;
    //Layout

    private CircleImageView mProfilePic;
    private TextView mUserName;
    private Button mChangeProfile;

    private String uid = null;
    //Firebase
    private DatabaseReference database;
    private FirebaseUser mCurrentUser;
    private StorageReference mImageStorage;

    private static final int GALLARY_PICK = 1;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_account, container, false);


        mProfilePic = (CircleImageView) mView.findViewById(R.id.account_profilePic);
        mUserName = (TextView) mView.findViewById(R.id.account_userName);
        mChangeProfile = (Button) mView.findViewById(R.id.account_changeImage_btn);


        mImageStorage = FirebaseStorage.getInstance().getReference();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mCurrentUser.getUid();

        database = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String userName = dataSnapshot.child("name").getValue().toString();
                String profilePic = dataSnapshot.child("image").getValue().toString();

                mUserName.setText(userName);
                Picasso.get().load(profilePic).into(mProfilePic);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent gallaryIntent = new Intent();
                gallaryIntent.setType("image/*");
                gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallaryIntent,"SELECT IMAGE"),GALLARY_PICK);
              /*  CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start((Activity) getContext());*/

            }
        });



        return mView;
    }

    // for crop image and store in firebase
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLARY_PICK && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            Intent intent = CropImage.activity(imageUri).setAspectRatio(1,1)
                    .getIntent(getContext());
            startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);

        }

        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {    // called after image is croped

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {

                final Uri resultUri = result.getUri();

                StorageReference imageFile = mImageStorage.child("profile_image").child(uid + ".jpg");

                //set uri of image
                imageFile.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful())
                        {
                            String ImageUrl = task.getResult().getDownloadUrl().toString();  // Download url of image

                            DatabaseReference mImage = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("image");
                            mImage.setValue(ImageUrl);

                            Picasso.get().load(ImageUrl).into(mProfilePic);
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
            }
        }
    }

}
