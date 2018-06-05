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
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    public TagDataAdapter tagDataAdapter;
    public CoursesStatus coursesStatus = new CoursesStatus();
    static DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);
        //mydb.resetDB();
        ArrayList array_list = mydb.getAllCotacts();
        ArrayList cities = mydb.getCititesData();
        Log.i("CITIES:", "all cities--" +cities);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            case R.id.InitService:
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}