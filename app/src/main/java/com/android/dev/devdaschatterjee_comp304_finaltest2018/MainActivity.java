package com.android.dev.devdaschatterjee_comp304_finaltest2018;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TaskManager taskManager;
    private EditText txtId, txtTaskName, txtTaskDescription;
    private Button btnAdd, btnShow, btnEdit, btnDelete,btnShowAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskManager = new TaskManager(this);
        //
        txtId = (EditText) findViewById(R.id.edTextTaskId);
        txtTaskName = (EditText) findViewById(R.id.edTextTaskName);
        txtTaskDescription = (EditText) findViewById(R.id.edTextTaskDescription);
        //
        btnAdd = findViewById(R.id.btnInsertTask);
        btnShow = findViewById(R.id.btnShowTask);
        btnEdit = findViewById(R.id.btnEdit);
        btnShowAll=findViewById(R.id.btnShowAllTask);
    }

    // create a new task to the database
    public void addTask(View v) {
        int taskId = Integer.parseInt(txtId.getText().toString());
        String tskName = txtTaskName.getText().toString();
        String taskDesc = txtTaskDescription.getText().toString();
        ContentValues values = new ContentValues();
        values.put("taskId", taskId);
        values.put("taskName", tskName);
        values.put("taskDescription", taskDesc);
        try {
            taskManager.addTask(values);
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    // to show all task-----------------
    public void showAllTask(View v){
        Cursor res = taskManager.showAllTask();
        if(res.getCount()==0){
            //Toast.makeText(this,"No Data Available",Toast.LENGTH_LONG).show();
            showTaskMessage("Error","No Data Found");
            return;
        }else{
            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()){
                buffer.append("taskId : "+res.getString(0)+"\n");
                buffer.append("taskName : "+res.getString(1)+"\n");
                buffer.append("taskDescription : "+res.getString(2)+"\n");
            }
            showTaskMessage("Data",buffer.toString());
        }
    }
    public void showTaskMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    //-----------------------

    // fetch a task by ID
    public void showTask(View v) {
        String taskIdStr = txtId.getText().toString();
        if (taskIdStr.isEmpty() || taskIdStr == null) {
            Toast.makeText(this, "Please Insert task ID", Toast.LENGTH_LONG).show();
        } else {
            try {
                int taskId = Integer.parseInt(taskIdStr);
                TaskModel taskmodel = taskManager.getTaskById(taskId);
                txtTaskName.setText(taskmodel.getTaskName());
                txtTaskDescription.setText(taskmodel.getTaskDescription());
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void editTask(View v) {
        int taskId = Integer.parseInt(txtId.getText().toString());
        String tskName = txtTaskName.getText().toString();
        String taskDesc = txtTaskDescription.getText().toString();
        try {
            ContentValues values = new ContentValues();
            values.put("taskId", taskId);
            values.put("taskName", tskName);
            values.put("taskDescription", taskDesc);
            boolean isUpdated=taskManager.editTask(taskId,"taskId",values);
            if(isUpdated==true){
                Toast.makeText(this,"Successfully Updated",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Update Failed",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
         Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void deleteTask(View v){
        String taskIdStr=txtId.getText().toString();
        try{
            boolean isDeletedTask=taskManager.deleteTask(Integer.parseInt(taskIdStr));

           if(isDeletedTask==true){
                Toast.makeText(this,"Task Deleted",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Task Not Deleted",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
