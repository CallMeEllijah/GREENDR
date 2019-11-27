package com.example.greendr.CardViews;

public class cardObject {

    private String userID;
    private String name;
    private String age;
    private String dpUrl;

    public cardObject(String userID, String name, String age, String dpUrl){

        this.userID = userID;
        this.name = name;
        this.age = age;
        this.dpUrl = dpUrl;

    }

    //just in case need to change like name
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public void setDpUrl(String dpUrl) {
        this.dpUrl = dpUrl;
    }

    public String getName(){
        return this.name;
    }
    public String getUserID(){
        return this.userID;
    }
    public String getAge(){
        return this.age;
    }
    public String getDpUrl() {
        return dpUrl;
    }

}
