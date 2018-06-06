package com.jorge.arnau.iot;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TagDataAdapter extends BaseAdapter {

    private Context recordContext;
    private List<TagData> recordList;
    private static class RecordViewHolder {
        //public TextView idView;
        public TextView rfidView;
        public ProgressBar progressBarView;
    }

    public TagDataAdapter(Context context, List<TagData> records) {
        recordList = records;
        recordContext = context;
    }

    public void add(TagData record) {
        recordList.add(record);
        notifyDataSetChanged();
    }

    public void clear(){
        recordList.clear();
    }

    public void addAll(List<Course> courses) {
        recordList.clear();
        for(Course c: courses){
            recordList.add(c.courseToTagData());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return recordList.size();
    }
    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecordViewHolder holder;

        if (view ==null){
            LayoutInflater recordInflater = (LayoutInflater)
                    recordContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = recordInflater.inflate(R.layout.record, null);

            holder = new RecordViewHolder();
            //holder.idView = (TextView) view.findViewById(R.id.record_id);
            holder.rfidView = (TextView) view.findViewById(R.id.record_rfid);
            holder.progressBarView = (ProgressBar) view.findViewById(R.id.progress_bar);
            view.setTag(holder);

        }else {
            holder = (RecordViewHolder) view.getTag();
        }

        TagData record = (TagData) getItem(i);
        //holder.idView.setText(record.id);
        holder.rfidView.setText(record.RFID);

        if (record.RFID != null) {
            Float averageEatingTime = CoursesStatus.averageEatingTime.get(record.RFID);
            Float remainingTime = CoursesStatus.remainingTime.get(record.RFID);

            if(averageEatingTime == null || remainingTime == null){
                holder.progressBarView.setProgress(100);
                holder.progressBarView.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

            }else{

                int progress = 100;
                if (remainingTime > 0)
                    progress = (int) (100 * (1 - remainingTime / averageEatingTime));

    //            Log.i("TEST-" + record.RFID, "averageEatingTime" + averageEatingTime);
    //            Log.i("TEST-" + record.RFID, "remainingTime" + remainingTime);
    //            Log.i("TEST-" + record.RFID, "progress" + progress);
                holder.progressBarView.setProgress(progress);
                holder.progressBarView.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            }
        }

        return view;
    }
}


