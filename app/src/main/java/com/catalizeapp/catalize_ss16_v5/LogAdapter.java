package com.catalizeapp.catalize_ss16_v5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.catalize.backend.model.introductionApi.model.Introduction;

import java.util.List;

public class LogAdapter extends ArrayAdapter<Introduction> {
    private List<Introduction> introList;
    static class ViewHolder {
        protected TextView date;
        protected TextView intro;
        protected TextView complete;

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    // Provide a suitable constructor (depends on the kind of dataset)


    public LogAdapter(Context context, int resource, List<Introduction> objects) {
        super(context, resource, objects);
        introList = objects;

    }

    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view =  LayoutInflater.from(getContext()).inflate(R.layout.log_item, null);
            holder.date = (TextView) view.findViewById(R.id.log_date);
            holder.intro = (TextView) view.findViewById(R.id.log_intro_text);
            holder.complete = (TextView) view.findViewById(R.id.log_complete);

            view.setTag(holder);

        } else {

            holder = (ViewHolder) view.getTag();
            holder.complete.setVisibility(View.GONE);

        }
        Introduction intro = introList.get(position);

        holder.date.setText( intro.getDate().substring(5,10));
        if(intro.getAReplied() && intro.getBReplied()){
            holder.complete.setVisibility(View.VISIBLE);

        }



        holder.intro.setText("Introduced " + intro.getAName() + " and " +intro.getBName());



        return view;
    }
    public  void addIntro(Introduction introduction){
        introList.add(introduction);
        notifyDataSetChanged();

    }
    public  Introduction getIntro(int position){

        return introList.get(position);
    }


}


