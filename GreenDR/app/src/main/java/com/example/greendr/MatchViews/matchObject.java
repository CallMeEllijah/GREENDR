package com.example.greendr.MatchViews;

public class matchObject {

    private String userID;
    private String name;
    private String url;
    private String userSex;
    private String matchSex;

    public matchObject(String userID, String name, String url, String userSex, String matchSex){

        this.userID = userID;
        this.name = name;
        this.url = url;
        this.userSex = userSex;
        this.matchSex = matchSex;

    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setMatchSex(String matchSex) {
        this.matchSex = matchSex;
    }

    public String getName(){
        return this.name;
    }
    public String getUserID(){
        return this.userID;
    }
    public String getUrl() {
        return url;
    }
    public String getUserSex() {
        return userSex;
    }
    public String getMatchSex() {
        return matchSex;
    }

}
