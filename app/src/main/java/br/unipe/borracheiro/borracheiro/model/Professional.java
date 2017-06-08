package br.unipe.borracheiro.borracheiro.model;

import com.orm.SugarRecord;

/**
 * Created by Andrey on 07/06/2017.
 */

public class Professional extends SugarRecord {
//    private int id;
    private String name;
    private String phone;
    private String latitude;
    private String longitude;

    public Professional(){}

    public Professional(String name, String phone) {
        this.name = name;
        this.phone = phone;
//        this.name = latitude;
//        this.name = longitude;
    }

//    public int getId() {
//        return id;
//    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}