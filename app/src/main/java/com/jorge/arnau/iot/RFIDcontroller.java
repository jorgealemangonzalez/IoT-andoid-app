package com.jorge.arnau.iot;

import android.os.AsyncTask;
import android.util.Log;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RFIDcontroller extends AsyncTask<String, Integer, Boolean> {
    public String adress = "http://10.0.2.2:3161/devices";
    public HashMap<String,LocalDateTime> connected_devices = new HashMap<>();
    public List<Course> endedCourses;
    public List<Course> notEndedCourses;

    protected Boolean doInBackground(String... urls) {
        endedCourses = MainActivity.mydb.getEndedCourses();
        notEndedCourses = MainActivity.mydb.getNotEndedCourses();
        for(Course c : notEndedCourses){
            connected_devices.put(c.RFID, c.startDate);         //restore last state
        }

        long totalSize = 0;
        boolean exitLoop = false;
        while (!exitLoop){
            RFIDManager manager = RFIDManager.getInstance();
            manager.readDevices();
            HashSet<String> listDevices = manager.getConnectedDevices();
            Log.i("DATA", listDevices.toString());

            LocalDateTime now = LocalDateTime.now();

            // devides which disconnected
            Collection<String> toRemove = new HashSet<>();
            toRemove.addAll(connected_devices.keySet());
            toRemove.removeAll(listDevices);
            for(String device: toRemove){
                connected_devices.remove(device);
                MainActivity.mydb.updateDeviceEndTime(device, now);
            }

            // new devices connected
            for(String device : listDevices){
                if(connected_devices.get(device) == null){
                    connected_devices.put(device, now);
                    MainActivity.mydb.insertDevice(device, now);
                }
            }

            endedCourses = MainActivity.mydb.getEndedCourses();         //Upload every 3 seconds because another device could have modified the database (if it is a remote DB)
            notEndedCourses = MainActivity.mydb.getNotEndedCourses();

            CoursesStatus.setEndedCourses(endedCourses);
            CoursesStatus.setNotEndedCourses(notEndedCourses);
            //Don't use all CPU
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                exitLoop = true;
                e.printStackTrace();
            }
        }
        return true;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Boolean result) {
        Log.i("RFIDcontroller: ", "The processing of RFIDs has been stopped");
    }
}
