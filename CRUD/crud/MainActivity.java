package com.example.exp10;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    EditText etRoll, etName, etDept, etMobile, etEmail, etCollege, etYear;
    Button btnAdd, btnUpdate, btnDelete, btnView, btnClear;
    TextView tvResult;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        etRoll = findViewById(R.id.etRoll);
        etName = findViewById(R.id.etName);
        etDept = findViewById(R.id.etDept);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etCollege = findViewById(R.id.etCollege);
        etYear = findViewById(R.id.etYear);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);
        btnClear = findViewById(R.id.btnClear);

        tvResult = findViewById(R.id.tvResult);

        btnAdd.setOnClickListener(v ->
                Toast.makeText(this,
                        db.insertStudent(
                                etRoll.getText().toString(),
                                etName.getText().toString(),
                                etDept.getText().toString(),
                                etMobile.getText().toString(),
                                etEmail.getText().toString(),
                                etCollege.getText().toString(),
                                etYear.getText().toString()
                        ) ? "Inserted" : "Failed",
                        Toast.LENGTH_SHORT).show()
        );

        btnView.setOnClickListener(v ->
                tvResult.setText(db.getAllStudents())
        );

        btnUpdate.setOnClickListener(v ->
                Toast.makeText(this,
                        db.updateStudent(
                                etRoll.getText().toString(),
                                etName.getText().toString(),
                                etDept.getText().toString(),
                                etMobile.getText().toString(),
                                etEmail.getText().toString(),
                                etCollege.getText().toString(),
                                etYear.getText().toString()
                        ) ? "Updated" : "Failed",
                        Toast.LENGTH_SHORT).show()
        );

        btnDelete.setOnClickListener(v ->
                Toast.makeText(this,
                        db.deleteStudent(etRoll.getText().toString())
                                ? "Deleted" : "Failed",
                        Toast.LENGTH_SHORT).show()
        );

        btnClear.setOnClickListener(v -> {
            etRoll.setText("");
            etName.setText("");
            etDept.setText("");
            etMobile.setText("");
            etEmail.setText("");
            etCollege.setText("");
            etYear.setText("");
            tvResult.setText("");
        });
    }
}
