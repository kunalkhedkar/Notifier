package com.example.kaushal.notifier;

/**
 * Created by kaushal on 21/10/2017.
 */

public class Users {
    public String u_ID;
    public String u_Username;
    public String u_Password;
    public String u_Role;
    public String u_Token="token";


    Users(){

    }


    public Users(String u_ID,String u_Username, String u_Password, String u_Role, String u_Token) {
        this.u_ID=u_ID;
        this.u_Username = u_Username;
        this.u_Password = u_Password;
        this.u_Role = u_Role;
        this.u_Token = u_Token;
    }

    public String getU_ID() {
        return u_ID;
    }

    public void setU_ID(String u_ID) {
        this.u_ID = u_ID;
    }

    public String getU_Username() {
        return u_Username;
    }

    public String getU_Password() {
        return u_Password;
    }

    public String getU_Role() {
        return u_Role;
    }

    public String getU_Token() {
        return u_Token;
    }


    public void setU_Username(String u_Username) {
        this.u_Username = u_Username;
    }

    public void setU_Password(String u_Password) {
        this.u_Password = u_Password;
    }

    public void setU_Role(String u_Role) {
        this.u_Role = u_Role;
    }

    public void setU_Token(String u_Token) {
        this.u_Token = u_Token;
    }
}

