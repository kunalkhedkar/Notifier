package com.example.kaushal.notifier;

/**
 * Created by kaushal on 20/10/2017.
 */

public class Teacher{
    public String t_ID;
    public String t_Name;
    public String t_Username;
    public String t_Password;
    public String t_Role;
    public String t_Mobile;
    public String t_Address;


    Teacher()
    {

    }


    public Teacher(String t_ID, String t_Name, String t_Username, String t_Password, String t_Role, String t_Mobile, String t_Address) {
        this.t_ID = t_ID;
        this.t_Name = t_Name;
        this.t_Username = t_Username;
        this.t_Password = t_Password;
        this.t_Role = t_Role;
        this.t_Mobile = t_Mobile;
        this.t_Address = t_Address;
    }


    public String getT_ID() {
        return t_ID;
    }

    public String getT_Name() {
        return t_Name;
    }

    public String getT_Username() {
        return t_Username;
    }

    public String getT_Password() {
        return t_Password;
    }

    public String getT_Role() {
        return t_Role;
    }

    public String getT_Mobile() {
        return t_Mobile;
    }

    public String getT_Address() {
        return t_Address;
    }

    public void setT_ID(String t_ID) {
        this.t_ID = t_ID;
    }

    public void setT_Name(String t_Name) {
        this.t_Name = t_Name;
    }

    public void setT_Username(String t_Username) {
        this.t_Username = t_Username;
    }

    public void setT_Password(String t_Password) {
        this.t_Password = t_Password;
    }

    public void setT_Role(String t_Role) {
        this.t_Role = t_Role;
    }

    public void setT_Mobile(String t_Mobile) {
        this.t_Mobile = t_Mobile;
    }

    public void setT_Address(String t_Address) {
        this.t_Address = t_Address;
    }
}
