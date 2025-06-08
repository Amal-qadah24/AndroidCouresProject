package com.example.androidcouresproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;




public class AddStudentActivity extends AppCompatActivity {


    EditText etName, etID;
    Spinner spgrade;
    Button btnAddStudent, btnShowStudents;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etName = findViewById(R.id.etName);
        etID = findViewById(R.id.etID);
        spgrade= findViewById(R.id.spMajor);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnShowStudents = findViewById(R.id.btnShowStudents);

        queue = Volley.newRequestQueue(this);


        String[] classnum = {"10", "11", "12", "Business"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, classnum);
        spgrade.setAdapter(adapter);

        btnAddStudent.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String id = etID.getText().toString();
            String grade =  spgrade.getSelectedItem().toString();

            if (name.isEmpty() || id.isEmpty()) {
                Toast.makeText(AddStudentActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            sendStudentToServer(name, id, grade);
            etName.setText("");
            etID.setText("");
            spgrade.setSelection(0);
        });

        btnShowStudents.setOnClickListener(v ->
                startActivity(new Intent(AddStudentActivity.this, StudentListActivity.class))
        );
    }

    private void sendStudentToServer(String name, String id, String grade) {
        String url = "http://10.0.2.2/myproject/add_student.php";

        Log.d("NETWORK", "Sending request...");
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d("NETWORK", "Response: " + response);
                    Toast.makeText(AddStudentActivity.this, response, Toast.LENGTH_LONG).show();
                },
                error -> {
                    Log.e("NETWORK", "Volley error: " + error.toString());
                    Toast.makeText(AddStudentActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("id", id);
                params.put("grade", grade);
                return params;
            }
        };

        queue.add(request);
    }
}
