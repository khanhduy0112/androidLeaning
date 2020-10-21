package com.nkd.app.tasklistsqlite;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nkd.app.tasklistsqlite.adapter.TaskAdapter;
import com.nkd.app.tasklistsqlite.database.DatabaseHelper;
import com.nkd.app.tasklistsqlite.model.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private RecyclerView tasksRecyclerView;
    private List<Task> tasks;
    private DatabaseHelper db;
    private FloatingActionButton fab;
    private EditText etTask;
    private Button btnSaveTask, btnViewAllTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasks = new ArrayList<>();
        db = new DatabaseHelper(this);

        updateRecyclerView();

        //save item
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String content = etTask.getText().toString();
                    db = new DatabaseHelper(MainActivity.this);
                    boolean success = db.insertOne(content, 1);
                    updateRecyclerView();
                    Toast.makeText(MainActivity.this, "Added : " + success, LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "ERRORRRRRRRRRRRR", Toast.LENGTH_LONG).show();
                }
            }
        });

        //view all item
        btnViewAllTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db = new DatabaseHelper(MainActivity.this);
                    List<Task> tasks = db.findAllTask();
                    Toast.makeText(MainActivity.this, tasks.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "ERRORRRRRRRRRRRR", Toast.LENGTH_LONG).show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewTaskActivity.class);
                startActivity(intent);
            }
        });

        //touch item in  recycler view
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(this.tasksRecyclerView);

    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        private Task itemDeleted;

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {
                final int id = tasks.get(viewHolder.getAdapterPosition()).getId();
                itemDeleted = tasks.get(viewHolder.getAdapterPosition());
                db = new DatabaseHelper(MainActivity.this);
                db.deleteOne(id);
                updateRecyclerView();
                Snackbar.make(tasksRecyclerView, "Are you sure to delete this item", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                db.insertOne(itemDeleted.getContent(), itemDeleted.getStatus());
                                updateRecyclerView();
                            }
                        }).show();
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.appColorDanger))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    public void updateRecyclerView() {
        tasks = db.findAllTask();
        TaskAdapter taskAdapter = new TaskAdapter(tasks, MainActivity.this, db);
        tasksRecyclerView.setAdapter(taskAdapter);
    }

    public void initView() {
        tasksRecyclerView = findViewById(R.id.rcw_tasksView);
        etTask = findViewById(R.id.et_task);
        btnSaveTask = findViewById(R.id.btn_saveTask);
        btnViewAllTask = findViewById(R.id.btn_viewAllTask);
        fab = findViewById(R.id.fab);
    }


}