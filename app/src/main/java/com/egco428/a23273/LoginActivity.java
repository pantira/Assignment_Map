package com.egco428.a23273;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    String name;
    String pass;
    EditText username;
    EditText password;
    Button signIn;
    Button signUp;
    Button cancle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        username = (EditText)findViewById(R.id.usernameInput);
        password = (EditText)findViewById(R.id.passwordInput);
        signIn = (Button)findViewById(R.id.button);
        signUp = (Button)findViewById(R.id.button3);
        cancle = (Button)findViewById(R.id.button2);



        signIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                name = username.getText().toString();
                pass = password.getText().toString();

                ValueEventListener postListener = new ValueEventListener()  {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        collectData((Map<String,Object>) snapshot.getValue());
                        }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                mDatabase.addValueEventListener(postListener);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        cancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                username.setText("");
                password.setText("");
            }
        });
    }

    public void collectData(Map<String,Object> users){

        ArrayList<Data> data = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){
            Map singleUser = (Map) entry.getValue();

            String nameUser = (String)singleUser.get("name");
            String passUser = (String)singleUser.get("password");
            String lat =(String)singleUser.get("latitude");
            String longitude = (String)singleUser.get("longitude");


            data.add(new Data(nameUser,passUser,lat,longitude));
            }

       for (int i=0;i<data.size();i++){
           if(name.equals(data.get(i).getName()) && pass.equals(data.get(i).getPassword())){
               Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();
               startActivity(new Intent(LoginActivity.this, MainActivity.class));
               break;
           }
           else{
               if(i==data.size()-1){
               Toast.makeText(getApplicationContext(),"Login fail",Toast.LENGTH_SHORT).show();
               username.setText("");
               password.setText("");}
           }
       }
    }


}



