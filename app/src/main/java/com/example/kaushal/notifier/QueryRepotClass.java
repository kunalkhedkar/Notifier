package com.example.kaushal.notifier;

/**
 * Created by kaushal on 04/11/2017.
 */

public class QueryRepotClass {
    public String qid;
    public String teacherUsername;
    public String studentName;
    public String studentClass;
    public String queryDescription;

    QueryRepotClass() {

    }

    public QueryRepotClass(String qid, String tusername, String studentName, String studentClass, String queryDescription) {
        this.qid = qid;
        this.teacherUsername = tusername;
        this.studentName = studentName;
        this.queryDescription = queryDescription;
        this.studentClass = studentClass;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getQueryDescription() {
        return queryDescription;
    }

    public void setQueryDescription(String queryDescription) {
        this.queryDescription = queryDescription;
    }
}

