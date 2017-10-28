package com.example.kaushal.notifier;

/**
 * Created by kaushal on 23/10/2017.
 */

public class OriginalTeacher {
    public String ot_ID;
    public String ot_Name;
    public String ot_Username;
    public String ot_Password;
    public String ot_Role;
    public String ot_Mobile;
    public String ot_Address;

    OriginalTeacher(){
            }


    public OriginalTeacher(String ot_ID, String ot_Name, String ot_Username, String ot_Password, String ot_Role, String ot_Mobile, String ot_Address) {
        this.ot_ID = ot_ID;
        this.ot_Name = ot_Name;
        this.ot_Username = ot_Username;
        this.ot_Password = ot_Password;
        this.ot_Role = ot_Role;
        this.ot_Mobile = ot_Mobile;
        this.ot_Address = ot_Address;
    }

    public String getOt_ID() {
        return ot_ID;
    }

    public String getOt_Name() {
        return ot_Name;
    }

    public String getOt_Username() {
        return ot_Username;
    }

    public String getOt_Password() {
        return ot_Password;
    }

    public String getOt_Role() {
        return ot_Role;
    }

    public String getOt_Mobile() {
        return ot_Mobile;
    }

    public String getOt_Address() {
        return ot_Address;
    }

    public void setOt_ID(String ot_ID) {
        this.ot_ID = ot_ID;
    }

    public void setOt_Name(String ot_Name) {
        this.ot_Name = ot_Name;
    }

    public void setOt_Username(String ot_Username) {
        this.ot_Username = ot_Username;
    }

    public void setOt_Password(String ot_Password) {
        this.ot_Password = ot_Password;
    }

    public void setOt_Role(String ot_Role) {
        this.ot_Role = ot_Role;
    }

    public void setOt_Mobile(String ot_Mobile) {
        this.ot_Mobile = ot_Mobile;
    }

    public void setOt_Address(String ot_Address) {
        this.ot_Address = ot_Address;
    }
}
