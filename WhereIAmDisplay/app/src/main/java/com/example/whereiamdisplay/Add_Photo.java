package com.example.whereiamdisplay;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;





public class Add_Photo extends AppCompatActivity {

    private EditText postTextEditText;
    private ImageView postImageView;
    private Uri selectedImageUriFromGallary = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_photo);
        getSupportActionBar().setTitle("Add Photo");
        init();
    }

    private void init() {
        postTextEditText = (EditText) findViewById(R.id.postTextEditText);
        postImageView = (ImageView) findViewById(R.id.postImageView);
    }

    public void addPost(View view) {

        String enteredPostText = postTextEditText.getText().toString();

        boolean flag = false;
        if (enteredPostText.equals(""))
            flag = true;
        if (selectedImageUriFromGallary == null)
            flag  = true;

        if (flag)
        {
            Toast.makeText(this, "select atleas one", Toast.LENGTH_SHORT).show();
        }
        else {
            final PostPojo postPojo = new PostPojo();
            postPojo.setPostText(enteredPostText);
            if (selectedImageUriFromGallary != null)
            {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference("image");
                UploadTask uploadTask = storageReference.putFile(selectedImageUriFromGallary);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_Photo.this, "image upload failed", Toast.LENGTH_SHORT).show();
                        postPojo.setPostImage("");
                    }
                });
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String imageUploadUrl = taskSnapshot.getStorage().getDownloadUrl().toString();
                        postPojo.setPostImage(imageUploadUrl);

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference postRef = firebaseDatabase.getReference("post");
                        SharedPreferences preferences = getSharedPreferences("kampuskonnectlo", MODE_PRIVATE);
                        String name=preferences.getString("name","Name");
                        String User_Id=preferences.getString("user_id","User_Id");

                        postPojo.setUserId(User_Id);
                        postPojo.setPostUserText(name);


                        String post_id = postRef.push().getKey();
                        postPojo.setPostId(post_id);
                        postRef.child(post_id).setValue(postPojo);

                        startActivity(new Intent(Add_Photo.this,DashboardActivity.class));

                        finish();
                    }
                });
            }
        }
    }

    public void selectImage(View view) {
        if (checkGallaryPermission()) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            startActivityForResult(i, 0);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 12);
        }
    }

    private boolean checkGallaryPermission() {
        boolean flag =
                ContextCompat.checkSelfPermission
                        (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return flag;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //for gallary
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                selectedImageUriFromGallary = data.getData();
//              use glide to load in every phone any size
                Glide.with(this)
                        .load(selectedImageUriFromGallary)
                        .crossFade()
                        .into(postImageView);
            }
        }

    }
}
