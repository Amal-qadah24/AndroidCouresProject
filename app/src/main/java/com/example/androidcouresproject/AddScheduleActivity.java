package com.example.androidcouresproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidcouresproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddScheduleActivity extends AppCompatActivity {

    Spinner teacherSpinner, subjectSpinner, classSpinner, daySpinner;
    EditText startTimeEditText, endTimeEditText;
    Button submitButton;

    ArrayList<Teacher> teacherList = new ArrayList<>();
    ArrayList<Subject> subjectList = new ArrayList<>();
    ArrayList<SchoolClass> classList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_schedule);

        initViews();
        setupDaySpinner();
        loadSubjects();
        loadClasses();

        submitButton.setOnClickListener(view -> submitSchedule());
    }

    private void initViews() {
        teacherSpinner = findViewById(R.id.spinner_teacher);
        subjectSpinner = findViewById(R.id.spinner_subject);
        classSpinner = findViewById(R.id.spinner_class);
        daySpinner = findViewById(R.id.spinner_day);
        startTimeEditText = findViewById(R.id.edit_start_time);
        endTimeEditText = findViewById(R.id.edit_end_time);
        submitButton = findViewById(R.id.button_submit);
    }

    private void setupDaySpinner() {
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, days);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        daySpinner.setAdapter(adapter);
    }

    private void loadSubjects() {
        String url = "http://10.0.2.2/myproject/get_subjects.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        subjectList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            Subject subject = new Subject(
                                    obj.getInt("subject_id"),
                                    obj.getString("subject_name"));
                            subjectList.add(subject);
                        }
                        ArrayAdapter<Subject> adapter = new ArrayAdapter<>(this,
                                R.layout.custom_spinner_item, subjectList);
                        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
                        subjectSpinner.setAdapter(adapter);

                        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Subject selectedSubject = subjectList.get(position);
                                loadTeachers(selectedSubject.id);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                teacherList.clear();
                                teacherSpinner.setAdapter(null);
                            }
                        });

                    } catch (JSONException e) {
                        Toast.makeText(this, "Error getting subject", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error loading subject", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(request);
    }

    private void loadTeachers(int subjectId) {
        if (subjectId <= 0) return;
        String url = "http://10.0.2.2/myproject/get_teachers_by_subject.php" +
                "?subject_id=" + subjectId;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        teacherList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            Teacher teacher = new Teacher(
                                    obj.getInt("teacher_id"),
                                    obj.getString("first_name") + " " + obj.getString("last_name"));
                            teacherList.add(teacher);
                        }
                        ArrayAdapter<Teacher> adapter = new ArrayAdapter<>(this,
                                R.layout.custom_spinner_item, teacherList);
                        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
                        teacherSpinner.setAdapter(adapter);
                    } catch (JSONException e) {
                        Toast.makeText(this, "Error in bringing teachers", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Loading teachers failed", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(request);
    }

    private void loadClasses() {
        String url = "http://10.0.2.2/myproject/get_classes.php";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        classList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            SchoolClass schoolClass = new SchoolClass(
                                    obj.getInt("class_id"),
                                    obj.getString("class_name"));
                            classList.add(schoolClass);
                        }
                        ArrayAdapter<SchoolClass> adapter = new ArrayAdapter<>(this,
                                R.layout.custom_spinner_item, classList);
                        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
                        classSpinner.setAdapter(adapter);
                    } catch (JSONException e) {
                        Toast.makeText(this, "Error fetching classes", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "classes loading failed", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(request);
    }

    private void submitSchedule() {
        Teacher teacher = (Teacher) teacherSpinner.getSelectedItem();
        Subject subject = (Subject) subjectSpinner.getSelectedItem();
        SchoolClass schoolClass = (SchoolClass) classSpinner.getSelectedItem();
        String day = daySpinner.getSelectedItem().toString();
        String start = startTimeEditText.getText().toString().trim();
        String end = endTimeEditText.getText().toString().trim();

        if (teacher == null || subject == null || schoolClass == null || start.isEmpty() || end.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2/myproject/add_schedule.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    String cleanResponse = response.trim().replace("\"", "").toLowerCase();
                    switch (cleanResponse) {
                        case "conflict":
                            Toast.makeText(this, "scheduling conflict!", Toast.LENGTH_LONG).show();
                            break;
                        case "success":
                            Toast.makeText(this, "Added successfully!", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(this, "Unknown response: " + cleanResponse, Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(this, "Error connecting to the server", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("teacher_id", String.valueOf(teacher.id));
                params.put("subject_id", String.valueOf(subject.id));
                params.put("class_id", String.valueOf(schoolClass.id));
                params.put("day_of_week", day);
                params.put("start_time", start);
                params.put("end_time", end);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(postRequest);
    }

    class Teacher {
        int id;
        String name;

        Teacher(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    class Subject {
        int id;
        String name;

        Subject(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    class SchoolClass {
        int id;
        String name;

        SchoolClass(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
