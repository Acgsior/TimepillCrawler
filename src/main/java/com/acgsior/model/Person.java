package com.acgsior.model;

import java.time.LocalDate;

/**
 * Created by mqin on 7/4/16.
 */
public class Person extends Base {

    private String name;

    private LocalDate registerDate;

    private String description;

    public static Person newInstance(String personId) {
        Person instance = new Person();
        instance.setId(personId);
        return instance;
    }

    public static Person newInstance(String personId) {
        Person instance = new Person();
        instance.setId(personId);
        return instance;
    }

    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
