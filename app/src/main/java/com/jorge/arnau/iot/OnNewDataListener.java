package com.jorge.arnau.iot;

import java.util.List;

public interface OnNewDataListener {
    void onNewData(List<Course> notEndedCourses);
}