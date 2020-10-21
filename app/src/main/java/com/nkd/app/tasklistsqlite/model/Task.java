package com.nkd.app.tasklistsqlite.model;

public class Task {

    private int id;
    private String content;
    private int status;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }

    public Task(int id, String content, int status) {
        this.id = id;
        this.content = content;
        this.status = status;
    }

    public Task() {
    }

    public Task(String content, int status) {
        this.content = content;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
