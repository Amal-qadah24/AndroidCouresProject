package com.example.androidcouresproject;

public class Student {

    private String name;
    private String id;
    private String classnum;

    public Student(String name, String id, String num) {
        this.name = name;
        this.id = id;
        this.classnum=num;
    }

    // Getters
    public String getName() { return name; }
    public String getId() { return id; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setId(String id) { this.id = id; }

    public String getClassnum() {
        return classnum;
    }

    public void setClassnum(String classnum) {
        this.classnum = classnum;
    }
}
