package com.register.afmelden.mohamed.afmelden;

import android.content.Context;
import android.os.Message;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Created by mohamed on 10-2-2015.
 */
public class Student {
    public String getStudentId() {
        return studentId;
    }

    public String getAbsence() {
        return absence;
    }

    public String getDate_time() {
        return date_time;
    }

    String studentId;
    String absence;
    String date_time;
    Context context;

    public Student(String id, String _absence, String _date){
        this.studentId = id;
        this.absence = _absence;
        this.date_time = _date;


        System.out.println("{\"Student_id : " + getStudentId() + ":\" reason for absence is : \"" + getAbsence() + ":\" datum is : " + getDate_time() + "}");



//        new absence_service(context,1).execute(getStudentId(),getAbsence(),getDate_time());

    }



}
