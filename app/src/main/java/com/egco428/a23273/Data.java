package com.egco428.a23273;


import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Benz on 11/18/2016.
 */
@IgnoreExtraProperties
public class Data {
    private String name;
    private String password;
    private String latitude;
    private String longitude;

    public Data(){

    }

    public Data(String name,String password,String latitude,String longitude) {
        this.name = name;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {return name;}
    public void setName(String name){this.name = name;}
    public String getPassword() {return password;}
    public void setPassword(String password){this.password = password;}
    public String getLatitude() {return latitude;}
    public void setLatitude(String latitude){this.latitude = latitude;}
    public void setLongitude(String longitude){this.longitude = longitude;}
    public String getLongitude() {return longitude;}


}
