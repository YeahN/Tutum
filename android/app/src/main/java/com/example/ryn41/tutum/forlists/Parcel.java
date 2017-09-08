package com.example.ryn41.tutum.forlists;

/**
 * Created by stg32 on 2017-09-06.
 */

public class Parcel {

//    String itemName;
    String companyCode;
    String companyName;
    String invoiceNo;

    public Parcel(String companyCode, String companyName, String invoiceNo) {
//        this.itemName = itemName;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.invoiceNo = invoiceNo;
    }

//    public String getItemName() {
//        return itemName;
//    }

//    public void setItemName(String itemName) {
//        this.itemName = itemName;
//    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
}
