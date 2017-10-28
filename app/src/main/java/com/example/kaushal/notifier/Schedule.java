package com.example.kaushal.notifier;

import java.security.PublicKey;

/**
 * Created by kaushal on 23/10/2017.
 */

public class Schedule {
    public String schedule_ID;
    public String t_name;
    public String t_username;
    public String sub_name;
    public String marks;
    public String s_date;
    public String s_time;
    public String classType;
    public String subjectType;
    public String description;


    Schedule(){

    }


    public Schedule(String username,String schedule_ID,String t_name, String sub_name, String marks,String description ,String s_date, String s_time, String classType, String subjectType) {
        this.t_username=username;
        this.schedule_ID=schedule_ID;
        this.t_name = t_name;
        this.sub_name = sub_name;
        this.marks = marks;
        this.description=description;
        this.s_date = s_date;
        this.s_time = s_time;
        this.classType = classType;
        this.subjectType = subjectType;

    }

    public String getT_username() {
        return t_username;
    }

    public void setT_username(String t_username) {
        this.t_username = t_username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchedule_ID() {
        return schedule_ID;
    }

    public String getT_name() {
        return t_name;
    }

    public String getSub_name() {
        return sub_name;
    }

    public String getMarks() {
        return marks;
    }

    public String getS_date() {
        return s_date;
    }

    public String getS_time() {
        return s_time;
    }

    public String getClassType() {
        return classType;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSchedule_ID(String schedule_ID) {
        this.schedule_ID = schedule_ID;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public void setS_date(String s_date) {
        this.s_date = s_date;
    }

    public void setS_time(String s_time) {
        this.s_time = s_time;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }
}
