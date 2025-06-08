package com.example.androidcouresproject;

import android.os.Bundle;
import android.widget.*;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.util.HashMap;
import java.util.Map;

public class AddTeacherActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPhone, etSpecialization, etHireDate;
    Button btnAddTeacher;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etSpecialization = findViewById(R.id.etSpecialization);
        etHireDate = findViewById(R.id.etHireDate);
        btnAddTeacher = findViewById(R.id.btnAddTeacher);

        queue = Volley.newRequestQueue(this);

        btnAddTeacher.setOnClickListener(v -> {
            String first = etFirstName.getText().toString();
            String last = etLastName.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            String spec = etSpecialization.getText().toString();
            String date = etHireDate.getText().toString();

            if (first.isEmpty() || last.isEmpty()) {
                Toast.makeText(this, "First and Last name required", Toast.LENGTH_SHORT).show();
                return;
            }

            sendToServer(first, last, email, phone, spec, date);
        });
    }

    private void sendToServer(String first, String last, String email, String phone, String spec, String date) {
        String url = "http://10.0.2.2/myproject/add_teacher.php"; // Adjust path

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(this, "Teacher added", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("first_name", first);
                map.put("last_name", last);
                map.put("email", email);
                map.put("phone", phone);
                map.put("specialization", spec);
                map.put("hire_date", date);
                return map;
            }
        };

        queue.add(request);
    }
}
