package com.example.kjaaharapp.activity.Models;

public class UserHistory {
    String name,type,description,userid;

    public UserHistory() {
    }

    public UserHistory(String name, String type, String description, String userid) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.userid = userid;
    }

    public UserHistory(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
