package com.example.deneme.MessageIDConstans;

public class AmtbSubFunctions {


    public static int A2KMisfireReason(String name) {

        switch(name.toUpperCase()) {

            case AMTBMessageState.AMTB_MISFIRE_REASON_UNKNOWN: return 0;
            case AMTBMessageState.AMTB_MISFIRE_REASON_EARLY: return 1;
            case AMTBMessageState.AMTB_MISFIRE_REASON_LATE: return 2;
            case AMTBMessageState.AMTB_MISFIRE_REASON_INV_SCAN: return 3;

            default: return -1;
        }
    }



}
