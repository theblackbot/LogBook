package com.ashutosh.mishra.logbook.models;

/**
 * Created by Ashutosh on 17-08-2016.
 */
public class JobDate {
    // status 0 normal, 1 pending, 2 done

    String date, status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
