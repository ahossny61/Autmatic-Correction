package com.example.bubblesheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class spinnerArrayAdapter extends ArrayAdapter<Exam> {
    public spinnerArrayAdapter(Context context, ArrayList<Exam>exams){
        super(context,0,exams);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,parent,false);
        }
        TextView txtExamName=convertView.findViewById(R.id.spinner_itemNAme);
        //TextView txtQuestionNumber=convertView.findViewById(R.id.spinner_itemQN);

        if(convertView!=null) {
            Exam currenExam = getItem(position);
            txtExamName.setText(currenExam.getName());
            //txtQuestionNumber.setText(currenExam.getQuestionNumber() + "");
        }
        return convertView;
    }
}
