package com.jorge.arnau.iot;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.LocalDateTime;

public class Course {
    public String id;
    public String RFID;
    public LocalDateTime startDate;
    public LocalDateTime endDate;

    public Course(String id, String RFID, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.RFID = RFID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Course(String id, String RFID, String startDate, String endDate) {
        this.id = id;
        this.RFID = RFID;
        this.startDate = LocalDateTime.parse(startDate);
        this.endDate = (endDate == null ? null : LocalDateTime.parse(endDate));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TagData courseToTagData() {
        String _endDate = " ";
        if(this.endDate != null){
            _endDate = this.endDate.toString();
        }
        //return new TagData(this.id.toString(), this.RFID.toString(), this.startDate.toString(), _endDate);
        return new TagData(this.id.toString(), this.RFID.toString(), String.valueOf(this.startDate.getSecond()), "END");
    }

    public Course(){

    }
}
