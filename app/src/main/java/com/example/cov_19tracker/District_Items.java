package com.example.cov_19tracker;

public class District_Items {
    private String mactive;
    private String mconfirmed;
    private String mrecovered;
    private String mdeceased;
    private String mnewconfirmed;
    private String mnewrecovered;
    private String mnewdeceased;
    private String mdistrict;
    private String mdate;

    public District_Items(String active , String confirmed , String recovered , String deceased , String newconfirmed , String newrecovered , String newdeceased , String district  ){
        mactive = active;
        mconfirmed = confirmed;
        mrecovered = recovered;
        mdeceased = deceased;
        mnewconfirmed = newconfirmed;


        mnewrecovered = newrecovered;
        mnewdeceased = newdeceased;
        mdistrict = district;


    }
    /*private static String formatSign(int number) {
        return (number > 0 ? "+" : "" ) + number;
    }*/

    public String getMactive() {
        return mactive;
    }

    public String getMconfirmed() {
        return mconfirmed;
    }


    public String getMrecovered() {
        return mrecovered;
    }

    public String getMdeceased() {
        return mdeceased;
    }

    public String getMdistrict() {
        return mdistrict;
    }

    public String getMnewconfirmed() {
        return mnewconfirmed;
    }

    public String getMnewrecovered() {
        return mnewrecovered;
    }

    public String getMnewdeceased() {
        return mnewdeceased;
    }
}
