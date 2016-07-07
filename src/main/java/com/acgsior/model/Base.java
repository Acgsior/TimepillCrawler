package com.acgsior.model;

/**
 * Created by Yove on 16/07/03.
 */
public abstract class Base {

    private String id;

    private String parent;

    private String location;

    protected Base() {

    }

    protected Base(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(final String parent) {
        this.parent = parent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
