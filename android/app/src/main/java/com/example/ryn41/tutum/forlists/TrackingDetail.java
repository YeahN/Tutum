package com.example.ryn41.tutum.forlists;

/**
 * Created by stg32 on 2017-09-06.
 */

public class TrackingDetail {

    String transTime;
    String transWhere;
    String transKind;

    public TrackingDetail(String transTime, String transWhere, String transKind) {
        this.transTime = transTime;
        this.transWhere = transWhere;
        this.transKind = transKind;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getTransWhere() {
        return transWhere;
    }

    public void setTransWhere(String transWhere) {
        this.transWhere = transWhere;
    }

    public String getTransKind() {
        return transKind;
    }

    public void setTransKind(String transKind) {
        this.transKind = transKind;
    }
}
