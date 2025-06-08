package com.example.androidcouresproject;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddSubjectActivity extends AppCompatActivity {

    EditText etSubjectName, etSubjectCode;
    Spinner spGradeLevel, spSemester;
    Button btnAddSubject;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        etSubjectName = findViewById(R.id.etSubjectName);
        etSubjectCode = findViewById(R.id.etSubjectCode);
        spGradeLevel = findViewById(R.id.spGradeLevel);
        spSemester = findViewById(R.id.spSemester);
        btnAddSubject = findViewById(R.id.btnAddSubject);

        queue = Volley.newRequestQueue(this);


        String[] grades = {"10 grade", "11 grade","12 grade"};
        String[] semesters = {"first", "second"};

        ArrayAdapter<String> gradesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, grades);
        spGradeLevel.setAdapter(gradesAdapter);

        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, semesters);
        spSemester.setAdapter(semesterAdapter);

        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etSubjectName.getText().toString();
                String code = etSubjectCode.getText().toString();
                String gradeLevel = spGradeLevel.getSelectedItem().toString();
                String semester = spSemester.getSelectedItem().toString();

                if (name.isEmpty() || code.isEmpty()) {
                    Toast.makeText(AddSubjectActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                sendSubjectToServer(name, code, gradeLevel, semester);

                etSubjectName.setText("");
                etSubjectCode.setText("");
                spGradeLevel.setSelection(0);
                spSemester.setSelection(0);
            }
        });
    }

    private void sendSubjectToServer(String name, String code, String gradeLevel, String semester) {
        String url = "http://10.0.2.2/myproject/add_subject.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(AddSubjectActivity.this, response, Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(AddSubjectActivity.this, "error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("code", code);
                params.put("gradeLevel", gradeLevel);
                params.put("semester", semester);
                return params;
            }
        };

        queue.add(request);
    }
}
