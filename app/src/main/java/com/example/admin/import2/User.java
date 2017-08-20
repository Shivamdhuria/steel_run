package com.example.admin.import2;

import static android.R.attr.name;

/**
 * Created by Admin on 7/2/2017.
 */

public class User {

    public String username;
    public String phone;
    public String tokenID;
    public String userpicture;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String phone,String tokenID,String userpicture) {
        this.username = username;
        this.phone = phone;
        this.tokenID = tokenID;
        this.userpicture = userpicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }


}