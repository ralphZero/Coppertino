package com.prs.coppertino.models;

public class TrackDataConverter {

    public static String Convert(String number){
        if(number.length()!= 4){
            return String.valueOf(Integer.parseInt(number));
        }else{
            int chiffre = Integer.parseInt(String.valueOf(number.charAt(0))) * 1000;
            return String.valueOf(Integer.parseInt(number) - chiffre);
        }
    }
}
