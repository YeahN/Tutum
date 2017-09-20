package com.example.ryn41.tutum.forlists;

/**
 * Created by stg32 on 2017-09-06.
 */

public class Payment {

    String amount;
    String detail;
    String time;

    public Payment(String amount, String detail, String time) {
        this.amount = amount;
        this.detail = detail;
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
