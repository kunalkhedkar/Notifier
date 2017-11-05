package com.example.kaushal.notifier;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kaushal on 05/11/2017.
 */
public class ViewQuertReportAdapter extends RecyclerView.Adapter<ViewQuertReportAdapter.ViewHolder> {

    private ArrayList<QueryRepotClass> queryObjectList;
    View view;

    public ViewQuertReportAdapter(ArrayList<QueryRepotClass> queryObjectList) {
        this.queryObjectList = queryObjectList;
    }

    @Override
    public ViewQuertReportAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_item_queryreport, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewQuertReportAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.studentName.setText(queryObjectList.get(i).getStudentName());
        viewHolder.query.setText(queryObjectList.get(i).getQueryDescription());
        viewHolder.classType.setText(queryObjectList.get(i).getStudentClass());

    }


    @Override
    public int getItemCount() {
        return queryObjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView studentName,classType,query;

        public ViewHolder(View view) {
            super(view);

            studentName = (TextView) view.findViewById(R.id.studentID);
            query = (TextView) view.findViewById(R.id.descriptionID);
            classType = (TextView) view.findViewById(R.id.classtypeID);

        }


    }
}
