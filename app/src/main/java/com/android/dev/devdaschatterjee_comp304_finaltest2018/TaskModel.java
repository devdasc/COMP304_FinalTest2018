package com.android.dev.devdaschatterjee_comp304_finaltest2018;

public class TaskModel {
     //private fields
    private int taskId;
    private String taskName, taskDescription;

    public TaskModel(int taskId, String taskName, String taskDescription) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }
    public TaskModel() {

    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    public String toString() {
        return getTaskName()+ " "+ getTaskDescription();
    }
}
