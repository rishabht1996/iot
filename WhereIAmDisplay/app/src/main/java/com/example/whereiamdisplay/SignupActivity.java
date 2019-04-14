package com.example.whereiamdisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText registerName,registerEmail,registerPass;
    private Button registerRBtn;
    private TextView linkBtn;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        getSupportActionBar().setTitle("Register");

        init();
        methodListeners();

    }

    private void init() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        registerName= (EditText) findViewById(R.id.input_name);
        registerEmail= (EditText) findViewById(R.id.input_email);

        registerPass= (EditText) findViewById(R.id.input_password);
        registerRBtn= (Button) findViewById(R.id.btn_signup);
        linkBtn= (TextView) findViewById(R.id.link_login);


        awesomeValidation.addValidation(this, R.id.input_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.input_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);


        awesomeValidation.addValidation(this, R.id.input_password, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);


    }

    private void methodListeners() {

        registerRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enteredPass=registerPass.getText().toString();

                    if (awesomeValidation.validate() ) {

                        value();
                    }

            }
        });
        linkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();

            }
        });
    }



    private void value() {

        String enteredName =registerName.getText().toString();
        String enteredEmail =registerEmail.getText().toString();

        String enteredPass =registerPass.getText().toString();
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference userRef =database.getReference("user");
        DataPojo pojo =new DataPojo();
        pojo.setName(enteredName);
        pojo.setEmail(enteredEmail);
        pojo.setPass(enteredPass);
        String User_Id=userRef.push().getKey();
        pojo.setUserId(User_Id);

        userRef.child(User_Id).setValue(pojo);
        startActivity(new Intent(SignupActivity.this,MapsActivity.class));
        finish();
    }
}
