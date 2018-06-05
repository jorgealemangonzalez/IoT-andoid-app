package com.jorge.arnau.iot;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TagDataAdapter extends BaseAdapter {

    private Context recordContext;
    private List<TagData> recordList;
    private static class RecordViewHolder {
        //public TextView idView;
        public TextView rfidView;
        public TextView startDateView;
        public TextView endDateView;
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
            holder.startDateView = (TextView) view.findViewById(R.id.record_startDate);
            holder.endDateView = (TextView) view.findViewById(R.id.record_endDate);
            view.setTag(holder);

        }else {
            holder = (RecordViewHolder) view.getTag();
        }

        TagData record = (TagData) getItem(i);
        //holder.idView.setText(record.id);
        holder.rfidView.setText(record.RFID);
        holder.startDateView.setText(record.startDate);
        holder.endDateView.setText(record.endDate);
        return view;
    }
}


