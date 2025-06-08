package com.example.androidcouresproject;

public class Teacher {
    private String firstName, lastName, email, phone, specialization, hireDate;

    public Teacher(String firstName, String lastName, String email, String phone, String specialization, String hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
        this.hireDate = hireDate;
    }

    // Getter methods (add setters if needed)
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getSpecialization() { return specialization; }
    public String getHireDate() { return hireDate; }
}
