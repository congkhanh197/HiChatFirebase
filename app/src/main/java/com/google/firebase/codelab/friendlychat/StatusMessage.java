package com.google.firebase.codelab.friendlychat;

/**
 * Created by Khanh Tran-Cong on 12/17/2016.
 * Email: congkhanh197@gmail.com
 */

public class StatusMessage {
    private String name;
    private String photoUrl;
    private String status;

    public StatusMessage(){

    }

    protected StatusMessage(String name, String photoUrl, String status) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
