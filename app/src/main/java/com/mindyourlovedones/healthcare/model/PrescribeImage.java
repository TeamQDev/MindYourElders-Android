package com.mindyourlovedones.healthcare.model;

import java.io.Serializable;

/**
 * Created by welcome on 9/19/2017.
 */

public class PrescribeImage implements Serializable {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getPreid() {
        return preid;
    }

    public void setPreid(int preid) {
        this.preid = preid;
    }

    int userid;
    int preid;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;
}
