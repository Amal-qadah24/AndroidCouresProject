package com.example.androidcouresproject;

public class Subject {

    private String name;
    private String code;
    private String gradeLevel;
    private String semester;

    // Constructor
    public Subject(String name, String code, String gradeLevel, String semester) {
        this.name = name;
        this.code = code;
        this.gradeLevel = gradeLevel;
        this.semester = semester;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public String getSemester() {
        return semester;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }


}
