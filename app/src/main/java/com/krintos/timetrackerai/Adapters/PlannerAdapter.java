package com.krintos.timetrackerai.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.krintos.timetrackerai.R;

import java.util.ArrayList;

/**
 * Created by root on 7/10/18.
 */

public class PlannerAdapter extends ArrayAdapter {
    private String[] task,stime,ftime,icon,ntime;
    private Activity context;
    public PlannerAdapter(Activity context, ArrayList<String> task, ArrayList<String> stime,
                          ArrayList<String> ftime,ArrayList<String> icon,ArrayList<String> ntime){
        super(context, R.layout.planner_adapter, task);
        this.context = context;
        this.task = task.toArray(new String[0]);
        this.stime = stime.toArray(new String[0]);
        this.ftime = ftime.toArray(new String[0]);
        this.icon = icon.toArray(new String[0]);
        this.ntime = ntime.toArray(new String[0]);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        PlannerAdapter.ViewHolder viewHolder = null;
        if (r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.planner_adapter, null,true);
            viewHolder = new PlannerAdapter.ViewHolder(r);
            r.setTag(viewHolder);
        }else {
            viewHolder = (PlannerAdapter.ViewHolder) r.getTag();
        }

        viewHolder.tstime.setText(stime[position]);
        viewHolder.tftime.setText(ftime[position]);
        viewHolder.ttask.setText(task[position]);
        viewHolder.tnotification.setText(ntime[position]);
        int id = context.getResources().getIdentifier(icon[position],"drawable",context.getPackageName());
        viewHolder.ticon.setImageResource(id);
        return r;

    }
    class ViewHolder{
        TextView ttask,tstime,tftime,tnotification;
        ImageView ticon;
        ViewHolder(View v){
            ttask = v.findViewById(R.id.taskname);
            tstime = v.findViewById(R.id.stime);
            tftime = v.findViewById(R.id.ftime);
            ticon = v.findViewById(R.id.icon);
            tnotification = v.findViewById(R.id.notification);
        }
    }
}
