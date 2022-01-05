package me.dongqinglin.flina.rest.helper;


import java.util.Date;
import java.sql.Timestamp;

public class TimeHelper {
    public static Timestamp toTimeStamp(Date date){
        return Timestamp.from(date.toInstant());
    }

    public static Date getNow(){
        return new Date();
    }
}
