package com.example.cov_19tracker;

public class state_items {
    private String mactive;
    private String mconfirmed;
    private String mrecovered;
    private String mdeceased;
    private String mnewconfirmed;
    private String mnewrecovered;
    private String mnewdeceased;
    private String mstate;
    private String mdate;

    public state_items(String active , String confirmed , String recovered , String deceased , String newconfirmed , String newrecovered , String newdeceased , String state , String date){
        mactive = active;
        mconfirmed = confirmed;
        mrecovered = recovered;
        mdeceased = deceased;
        mnewconfirmed = newconfirmed;
        mdate = date;


        mnewrecovered = newrecovered;
        mnewdeceased = newdeceased;
        mstate = state;

    }
    /*private static String formatSign(int number) {
        return (number > 0 ? "+" : "" ) + number;
    }*/

    public String getMactive() {
        return mactive;
    }

    public String getMdate() {
        return mdate;
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

    public String getMstate() {
        return mstate;
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
