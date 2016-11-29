package com.egco428.a23273;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<Data> customArrayAdapter ;
    private DatabaseReference mDatabase;
    List<Data> arrayData = new ArrayList<>();
    public static final String Latitude = "latitude";
    public static final String Longtitude = "longitude";
    public static final String Title = "name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                 Data object = (Data) arrayData.get(position);

                 Intent intent = new Intent(MainActivity.this,MapActivity.class);
                 intent.putExtra(Latitude, object.getLatitude());
                 intent.putExtra(Longtitude, object.getLongitude());
                 intent.putExtra(Title, object.getName());
                 startActivity(intent);


            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                retriveData((Map<String,Object>) snapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                // ...
            }
        };
        mDatabase.addValueEventListener(postListener);
    }


    private void retriveData(Map<String, Object> value) {

        for (Map.Entry<String, Object> entry : value.entrySet()){
            Map singleUser = (Map) entry.getValue();
            String nameUser = (String)singleUser.get("name");
            String password = (String)singleUser.get("password");
            String lat = (String)singleUser.get("latitude");
            String longitude = (String)singleUser.get("longitude");

            arrayData.add(new Data(nameUser,password,lat,longitude));
        }
        if(arrayData.size()>0){
            customArrayAdapter = new CustomArrayAdapter(this, 0, arrayData);
            listView.setAdapter(customArrayAdapter);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Do you want to LOG OUT");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return true;
    }

}
