package com.example.androidcouresproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.androidcouresproject.R;
import com.example.androidcouresproject.Student;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    public StudentAdapter(Context context, List<Student> students) {
        super(context, 0, students);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.student_item, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvId = convertView.findViewById(R.id.tvId);
        TextView tvGrade = convertView.findViewById(R.id.tvGrade);

        tvName.setText("Name: " + student.getName());
        tvId.setText("ID: " + student.getId());
        tvGrade.setText("Grade: " + student.getClassnum());

        return convertView;
    }
}
