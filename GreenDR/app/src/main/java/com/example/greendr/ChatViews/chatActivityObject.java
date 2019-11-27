package com.example.greendr.ChatViews;

public class chatActivityObject {

    private String mMessage;
    private Boolean currentUser;

    public chatActivityObject(String mMessage, Boolean currentUser){
        this.mMessage = mMessage;
        this.currentUser = currentUser;
    }

    public String getmMessage() {
        return mMessage;
    }
    public Boolean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Boolean currentUser) {
        this.currentUser = currentUser;
    }
    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
