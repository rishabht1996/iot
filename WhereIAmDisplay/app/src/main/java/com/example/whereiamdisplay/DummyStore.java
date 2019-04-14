package com.example.whereiamdisplay;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DummyStore extends AppCompatActivity {

    private Button help, victim;
    private TextView signup;
    private NotificationUtils mNotificationUtils;
    private static final int PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        getSupportActionBar().setTitle("Where I am");

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }

        details();
        init();
        methodListeners();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the service when the permission is granted
            loginToFirebase();
        } else {
            finish();
        }
    }

    private void details() {


    }

    private void init() {
        help = findViewById(R.id.help);
        victim = findViewById(R.id.victim);
        signup = findViewById(R.id.signup);
    }

    private void methodListeners() {
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper();
            }
        });

        victim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                needhelp();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DummyStore.this, SignupActivity.class));
                finish();
            }
        });
    }

    private void needhelp() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("temp");
        DataPojo pojo = new DataPojo();
        pojo.setMessage("I need Help");
        pojo.setNoti("1");
        //String User_Id = ref.push().getKey();
        ref.child("childt").setValue(pojo);
        loginToFirebase();
        startActivity(new Intent(this, MapsActivity.class));
        finish();


    }

    private void helper() {
        mNotificationUtils = new NotificationUtils(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("temp");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                {
                    DataPojo pojo = childDataSnapshot.getValue(DataPojo.class);
                    if(pojo.getNoti().equals("1")) {
                        String title = "Need Help";
                        String author= "Can you please help!!!";

                        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(author)) {
                            Notification.Builder nb = mNotificationUtils.
                                    getAndroidChannelNotification(title, "By " + author);

                            mNotificationUtils.getManager().notify(101, nb.build());


                        }
                    }

                }


                //Toast.makeText(LoginActivity.this, "UserId/Pass Invalid", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }


    private void loginToFirebase() {
        // Functionality coming next step
        String email = getString(R.string.firebase_email);
        String password = getString(R.string.firebase_password);
        FirebaseApp.initializeApp(this);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Log.d(TAG, "firebase auth success");
                    requestLocationUpdates();
                    //Toast.makeText(DummyStore.this, "Im in login", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(mNotificationUtils, "I am in firebase login", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d(TAG, "firebase auth failed");
                }
            }
        });
    }

    private void requestLocationUpdates() {
        // Functionality coming next step

        SharedPreferences preferences = getSharedPreferences("whereiam", MODE_PRIVATE);
        String n = preferences.getString("name", "name");

        String user_loc = preferences.getString("user_id", "user_id");
        Toast.makeText(DummyStore.this, n + user_loc, Toast.LENGTH_SHORT).show();
        //Toast.makeText(mNotificationUtils, " I am after haredpref", Toast.LENGTH_SHORT).show();
        Toast.makeText(DummyStore.this, "I am in location", Toast.LENGTH_SHORT).show();
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        final String path = user_loc + "/" + n;
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            Toast.makeText(DummyStore.this, "Updating", Toast.LENGTH_SHORT).show();
            // received, store the location in Firebase
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        //Log.d(TAG, "location update " + location);
                        ref.setValue(location);
                    }
                }
            }, null);
        }
    }
}