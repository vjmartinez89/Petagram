package com.vjmartinez.petagram.dto;

import java.io.Serializable;

public class ContactPhoto implements Serializable {

    private int likes;
    private int loves;
    private int resourceId;

    public ContactPhoto(){}

    public ContactPhoto(int likes, int loves, int resourceId){
        this.likes = likes;
        this.loves = loves;
        this.resourceId = resourceId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getLoves() {
        return loves;
    }

    public void setLoves(int loves) {
        this.loves = loves;
    }
}
