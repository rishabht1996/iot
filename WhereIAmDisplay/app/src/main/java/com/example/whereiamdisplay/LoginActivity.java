package com.example.whereiamdisplay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailEditText, loginPassEditText;
    private Button loginBtn;
    private TextView registerBtn;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().setTitle("Login");

        SharedPreferences preferences = getSharedPreferences("kampuskonnectlo", MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("isLogin", false);
        if (isLogin)
        {

        }

        init();
        methodListeners();


    }

    private void init() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        loginEmailEditText = (EditText) findViewById(R.id.input_email);
        loginPassEditText = (EditText) findViewById(R.id.input_password);
        loginBtn = (Button) findViewById(R.id.btn_login);
        registerBtn = (TextView) findViewById(R.id.link_signup);
        awesomeValidation.addValidation(this, R.id.input_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.input_password, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
    }

    private void methodListeners() {

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate() ) {

                    check();
                }

            }
        });
    }

    private void check() {


        final String enteredemail=loginEmailEditText.getText().toString();
        final String enteredpass =loginPassEditText.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("user");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                {
                    DataPojo pojo = childDataSnapshot.getValue(DataPojo.class);


                    if (enteredemail.equals(pojo.getEmail()) && enteredpass.equals(pojo.getPass()))
                    {

                        SharedPreferences sharedPreferences =
                                getSharedPreferences("whereiam", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLogin", true);
                        editor.putString("user_id", pojo.getUserId());
                        editor.putString("name",pojo.getName());
                        editor.putString("email",pojo.getEmail());
                        editor.putString("pass",pojo.getPass());
                        editor.putString("mobile",pojo.getMobile());
                        editor.commit();
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    }

                }


                Toast.makeText(LoginActivity.this, "UserId/Pass Invalid", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
