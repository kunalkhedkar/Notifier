package com.example.kaushal.notifier;

/**
 * Created by kaushal on 26/10/2017.
 */

public class Head {
    public String headUsername;
    public String fullName;
    public String instituteName;

    Head(){

    }


    public Head(String headUsername, String fullName, String instituteName1) {
        this.headUsername = headUsername;
        this.fullName = fullName;
        instituteName = instituteName1;
    }

    public String getHeadUsername() {
        return headUsername;
    }

    public void setHeadUsername(String headUsername) {
        this.headUsername = headUsername;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
