package com.jorge.arnau.iot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

public class CoursesStatus {
    private static List<Course> endedCourses;
    private static List<Course> notEndedCourses;

    public static HashMap<String, Float> averageEatingTime;         //RFID -> AVERAGE TIME (seconds)
    public static HashMap<String, Float> remainingTime;             //RFID -> average time until finish  //TODO JUST ONE OF EACH RFID ?

    private static void setAverageEatingTime() {
        HashMap<String, Integer> numberOfCourses = new HashMap<>();
        averageEatingTime = new HashMap<>();
        for (Course c : endedCourses) {
            Float intervaltime = (float) c.startDate.until(c.endDate, ChronoUnit.SECONDS);
            if (averageEatingTime.get(c.RFID) == null) {
                averageEatingTime.put(c.RFID, intervaltime);
                numberOfCourses.put(c.RFID, 1);
            } else {
                averageEatingTime.put(c.RFID, averageEatingTime.get(c.RFID) + intervaltime);
                try{
                    numberOfCourses.put(c.RFID, numberOfCourses.get(c.RFID) + 1);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }

        for (String c : numberOfCourses.keySet()) {
            averageEatingTime.put(c, averageEatingTime.get(c) / (float) numberOfCourses.get(c));
        }

    }

    private static void setRemainingTime(){
        remainingTime = new HashMap<>();

        LocalDateTime now = LocalDateTime.now();
        for(Course c : notEndedCourses){
            try {
                LocalDateTime estimatedFinish = c.startDate.plusSeconds(averageEatingTime.get(c.RFID).intValue());
                Float remaining = 0.f;
                if (estimatedFinish.isAfter(now)) {
                    remaining = (float) now.until(estimatedFinish, ChronoUnit.SECONDS);
                } else {
                    remaining = -(float) estimatedFinish.until(now, ChronoUnit.SECONDS);         //This course is spending more time than the average
                }
                remainingTime.put(c.RFID, remaining);
            } catch (Exception e){
                continue;
            }
        }
    }

    public static void setEndedCourses(List<Course> endedCourses){
        CoursesStatus.endedCourses = endedCourses;
        CoursesStatus.setAverageEatingTime();
    }

    public static void setNotEndedCourses(List<Course> notEndedCourses) {
        CoursesStatus.notEndedCourses = notEndedCourses;
        CoursesStatus.setRemainingTime();
    }



}
