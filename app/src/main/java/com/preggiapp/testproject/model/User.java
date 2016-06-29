package com.preggiapp.testproject.model;

public class User {

    private String id;
    private String login;
    private String locality;
    private Avatar avatar;
    private PregnancySettings pregnancy_settings;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public PregnancySettings getPregnancy_settings() {
        return pregnancy_settings;
    }

    public void setPregnancy_settings(PregnancySettings pregnancy_settings) {
        this.pregnancy_settings = pregnancy_settings;
    }
}