package com.jorge.arnau.iot;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public TagDataAdapter tagDataAdapter;
    static DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_devices);
        mydb = new DBHelper(this);
        //mydb.resetDB();

        Bundle extras = getIntent().getExtras();

        //DATAADAPTER
        tagDataAdapter= new TagDataAdapter(this, new ArrayList<TagData>());
        setContentView(R.layout.activity_display_devices);
        final ListView recordsView = (ListView) findViewById(R.id.records_view);
        recordsView.setAdapter(tagDataAdapter);

        //as before
        RFIDcontroller devicesData = new RFIDcontroller();
        devicesData.execute();
        devicesData.setOnNewData(new OnNewDataListener() {
            @Override
            public void onNewData(final List<Course> notEndedCourses) {
                //Code to work when music stops
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Log.i( "LE", String.valueOf(notEndedCourses.size()));
                            //tagDataAdapter.addAll(notEndedCourses);
                            tagDataAdapter.clear();
                            recordsView.setSelection(0);
                            for(Course c: notEndedCourses) {
                                TagData record = c.courseToTagData();
                                tagDataAdapter.add(record);
                                recordsView.setSelection(tagDataAdapter.getCount() - 1);
                            }
                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
    }
}