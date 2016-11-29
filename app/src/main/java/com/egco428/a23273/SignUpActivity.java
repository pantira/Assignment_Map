package com.egco428.a23273;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity implements SensorEventListener {
    Button Clicking;
    Button addUser;
    EditText latitude;
    EditText longitude;
    EditText username;
    EditText password;
    String saveName;
    String savePass;
    String saveLat;
    String saveLong;
    ArrayList<String> values = new ArrayList<>();

    private SensorManager sensorManager;
    private long lastUpdate;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;

    Random aa = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        Clicking = (Button) findViewById(R.id.clickBtn);
        addUser = (Button) findViewById(R.id.addBtn);
        latitude = (EditText) findViewById(R.id.LatInput);
        longitude = (EditText) findViewById(R.id.LongInput);
        username = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passwordInput);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                retriveData((Map<String, Object>) snapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.addValueEventListener(postListener);


        Clicking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double ranLat = aa.nextDouble() * 170 - 85;
                double ranLong = aa.nextDouble() * 359.999978 - 179.999989;

                latitude.setText(Double.toString(ranLat));
                longitude.setText(Double.toString(ranLong));
            }
        });


        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = database.getReference("User");
                DatabaseReference postsRef = ref.getParent();
                DatabaseReference newPostRef = postsRef.push();

                saveLat = latitude.getText().toString();
                saveLong = longitude.getText().toString();
                saveName = username.getText().toString();
                savePass = password.getText().toString();
                Data data = new Data(saveName, savePass, saveLat, saveLong);

                if (saveLat.equals("") || saveLong.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please input latitude or longitude", Toast.LENGTH_SHORT).show();
                } else if (saveName.equals("") || savePass.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please input username or password", Toast.LENGTH_SHORT).show();
                } else if (saveName != null) {
                    Boolean check=false;
                    for(int i=0;i<values.size();i++){
                        String value = values.get(i);
                        if(saveName.equals(values.get(i))){
                            Toast.makeText(SignUpActivity.this, "Already use this username", Toast.LENGTH_SHORT).show();
                            check=true;
                            break;
                        }
                    }
                    if(check==false){
                        newPostRef.setValue(data);
                        finish();}

                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        double ranLat = aa.nextDouble() * 170 - 85;
        double ranLong = aa.nextDouble() * 359.999978 - 179.999989;


        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH); //distance between 2 point
        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot >= 4) //
        {
            if (actualTime - lastUpdate < 500) {
                return;
            }
            lastUpdate = actualTime;
            latitude.setText(Double.toString(ranLat));
            longitude.setText(Double.toString(ranLong));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void retriveData(Map<String, Object> value) {

        for (Map.Entry<String, Object> entry : value.entrySet()) {
            Map singleUser = (Map) entry.getValue();
            String nameUser = (String) singleUser.get("name");
            values.add(nameUser);
        }
    }
}
