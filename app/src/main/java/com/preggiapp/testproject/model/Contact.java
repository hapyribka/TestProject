package com.preggiapp.testproject.model;

public class Contact {

    private String name;
    private String ava;
    private String email;

    public Contact() {
    }

    public Contact(String name, String ava, String email) {
        this.name = name;
        this.ava = ava;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}