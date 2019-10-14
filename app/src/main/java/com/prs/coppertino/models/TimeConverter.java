package com.prs.coppertino.models;

import android.util.Log;

public class TimeConverter {

    public static String ConvertToSeconds(String millisecond){
        if(millisecond != null) {
            try {
                Long time = Long.valueOf(millisecond);
                long seconds = time/1000;
                long minutes = seconds/60;
                seconds = seconds % 60;

                if(seconds<10) {
                    return String.valueOf(minutes) + ":0" + String.valueOf(seconds);
                } else {
                    return String.valueOf(minutes) + ":" + String.valueOf(seconds);
                }
            } catch(NumberFormatException e) {
                return "-";
            }
        } else {
            return "-";
        }
    }
}
