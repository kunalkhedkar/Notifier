package com.example.kaushal.notifier;

/**
 * Created by kaushal on 18/10/2017.
 */

public class Student {
    public String s_ID;
    public String s_Name;
    public String s_Username;
    public String s_Password;
    public String s_RollNumber;
    public String s_Mobile;
    public String s_Address;
    public String s_Role;
    public String s_Class;

    public Student() {

    }

    public Student(String s_ID, String s_Name, String s_Username, String s_Password, String s_RollNumber, String s_Mobile, String s_Address, String s_Role, String s_Class) {
        this.s_ID = s_ID;
        this.s_Name = s_Name;
        this.s_Username = s_Username;
        this.s_Password = s_Password;
        this.s_RollNumber = s_RollNumber;
        this.s_Mobile = s_Mobile;
        this.s_Address = s_Address;
        this.s_Role = s_Role;
        this.s_Class = s_Class;
    }

    public String getS_ID() {
        return s_ID;
    }

    public String getS_Name() {
        return s_Name;
    }

    public String gets_Username() {
        return s_Username;
    }

    public String getS_Password() {
        return s_Password;
    }

    public String gets_RollNumber() {
        return s_RollNumber;
    }

    public String getS_Mobile() {
        return s_Mobile;
    }

    public String getS_Address() {
        return s_Address;
    }

    public String getS_Role() {
        return s_Role;
    }

    public String getS_Class() {
        return s_Class;
    }

    // setter


    public void setS_ID(String s_ID) {
        this.s_ID = s_ID;
    }

    public void setS_Name(String s_Name) {
        this.s_Name = s_Name;
    }

    public void sets_Username(String s_Username) {
        this.s_Username = s_Username;
    }

    public void setS_Password(String s_Password) {
        this.s_Password = s_Password;
    }

    public void sets_RollNumber(String s_RollNumber) {
        this.s_RollNumber = s_RollNumber;
    }

    public void setS_Mobile(String s_Mobile) {
        this.s_Mobile = s_Mobile;
    }

    public void setS_Address(String s_Address) {
        this.s_Address = s_Address;
    }

    public void setS_Role(String s_Role) {
        this.s_Role = s_Role;
    }

    public void setS_Class(String s_Class) {
        this.s_Class = s_Class;
    }


}
