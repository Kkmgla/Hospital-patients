package com.example.hospital_patients;

import java.io.Serializable;

public class User implements Serializable {
    String id, email, firstname, lastname, sex, anamnesis, anamnesisFull;
    int age;

    public User(){

    }
    public User(String id, String email, String firstname, String lastname, int age, String anamnesis, String anamnesisFull, String sex) {
        this.id = id;
        this.email = email.replace(".", "(dot)");
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.anamnesis = anamnesis;
        this.anamnesisFull = anamnesisFull;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age='" + age + '\'' +
                ", anamnesis='" + anamnesis + '\'' +
                ", anamnesisFull='" + anamnesisFull + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getEmail() {
        return email.replace("(dot)", ".");
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAnamnesis() {
        return anamnesis;
    }

    public void getAnamnesis(String anamnesis) {
        this.anamnesis = anamnesis;
    }

    public String getAnamnesisFull() {
        return anamnesisFull;
    }

    public void setAnamnesisFull(String anamnesisFull) {
        this.anamnesisFull = anamnesisFull;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}