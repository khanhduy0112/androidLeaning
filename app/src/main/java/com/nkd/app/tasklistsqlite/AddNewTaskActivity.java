package com.nkd.app.tasklistsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import static android.widget.Toast.LENGTH_SHORT;

public class AddNewTaskActivity extends AppCompatActivity {

    private EditText etTask;
    private Button btnSaveTask, btnViewAllTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        etTask = (EditText) findViewById(R.id.et_task);
        btnSaveTask = (Button) findViewById(R.id.btn_saveTask);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = etTask.getText().toString();
                Intent intent = new Intent(AddNewTaskActivity.this, MainActivity.class);
                intent.putExtra("taskContent", task);
                startActivity(intent);
            }
        });

    }
}