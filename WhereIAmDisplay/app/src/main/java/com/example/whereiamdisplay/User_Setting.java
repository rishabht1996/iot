package com.example.whereiamdisplay;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Rishabh on 20/07/2017.
 */

public class User_Setting extends AppCompatActivity {

    private EditText nameSettings,passSettings;
    private Button updateSettingsBtn,profilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting);
        getSupportActionBar().setTitle("Settings");
        init();
        methodListeners();


    }

    private void init() {
        nameSettings= (EditText) findViewById(R.id.nameSettings);
        passSettings= (EditText) findViewById(R.id.passSettings);
        updateSettingsBtn= (Button) findViewById(R.id.updateSettingsBtn);
        profilePic= (Button) findViewById(R.id.profilePic);


    }

    private void methodListeners() {

        SharedPreferences preferences = getSharedPreferences("kampuskonnectlo", MODE_PRIVATE);
        String name = preferences.getString("name", "UserName");
        String pass = preferences.getString("pass", "Password");
        nameSettings.setText(name);
        passSettings.setText(pass);

        updateSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    private void update() {

        String enteredName=nameSettings.getText().toString();
        String enteredPass=passSettings.getText().toString();

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference userRef =database.getReference("user");
        UserPojo pojo =new UserPojo();
        pojo.setName(enteredName);
        pojo.setPass(enteredPass);
        SharedPreferences preferences = getSharedPreferences("kampuskonnectlo", MODE_PRIVATE);
        String email=preferences.getString("email","Email");
        String mobile_no=preferences.getString("mobile","Mobile");
        String User_Id=preferences.getString("user_id","User_Id");
        pojo.setEmail(email);
        pojo.setMobile(mobile_no);
        pojo.setUserId(User_Id);
        userRef.child(User_Id).setValue(pojo);
    }
}