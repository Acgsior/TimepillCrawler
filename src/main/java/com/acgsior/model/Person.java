package com.acgsior.model;

import java.time.LocalDate;

/**
 * Created by Yove on 7/4/16.
 */
public class Person extends Base {

    private String name;

    private LocalDate registerDate;

    private String description;
    private String avatar;

    public static Person newInstance(String personId) {
        Person instance = new Person();
        instance.setId(personId);
        return instance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Person{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", registerDate=").append(registerDate);
        sb.append(", description='").append(description).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append('}');
        return sb.toString();
    }

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
