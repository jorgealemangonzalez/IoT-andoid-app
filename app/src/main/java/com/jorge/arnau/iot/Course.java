package com.jorge.arnau.iot;

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

    public Course(){

    }
}
