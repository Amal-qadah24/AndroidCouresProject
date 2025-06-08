package com.example.androidcouresproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrarHomeActivity extends AppCompatActivity {

    Button btnAddStudent, btnAddSubject, btnBuildSchedule, addte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_home);

        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnAddSubject = findViewById(R.id.btnAddSubject);
        btnBuildSchedule = findViewById(R.id.btnBuildSchedule);
        addte = findViewById(R.id.btnAddTeacher);

        btnAddStudent.setOnClickListener(v ->
                startActivity(new Intent(this, AddStudentActivity.class)));

        btnAddSubject.setOnClickListener(v ->
                startActivity(new Intent(this, AddSubjectActivity.class)));

        btnBuildSchedule.setOnClickListener(v ->
                startActivity(new Intent(this, AddScheduleActivity.class)));

        addte.setOnClickListener(v ->
                startActivity(new Intent(this, AddTeacherActivity.class)));
    }
}
