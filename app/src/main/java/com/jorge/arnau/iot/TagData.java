package com.jorge.arnau.iot;

import java.time.LocalDateTime;

class TagData {
    //public String tag_id;
    //public String time;
    public String id;
    public String RFID;
    public String startDate;
    public String endDate;

    public TagData(String _id, String _RFID, String _timeS, String _timeE) {
        id = id;
        RFID = _RFID;
        startDate = _timeS;
        endDate = _timeE;
    }
}
