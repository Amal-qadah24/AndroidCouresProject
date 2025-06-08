package com.example.androidcouresproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {

    ListView lstStudents;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        lstStudents = findViewById(R.id.lstStudents);
        queue = Volley.newRequestQueue(this);

        loadStudents();
    }

    private void loadStudents() {
        String url = "http://10.0.2.2/myproject/get_students.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    ArrayList<Student> students = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            String name = obj.getString("name");
                            String id = obj.getString("id");
                            String grade = obj.getString("grade");
                            students.add(new Student(name, id, grade));
                        } catch (Exception e) {
                            Log.e("JSON", "Error parsing", e);
                        }
                    }

                    StudentAdapter adapter = new StudentAdapter(this, students);
                    lstStudents.setAdapter(adapter);
                },
                error -> {
                    Log.e("VOLLEY", "Error: " + error.getMessage());
                    Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }
}

