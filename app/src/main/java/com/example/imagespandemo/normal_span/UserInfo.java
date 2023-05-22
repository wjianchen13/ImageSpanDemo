package com.example.imagespandemo.normal_span;

public class UserInfo {

    private int type;
    private String name;
    private int sex;

    public UserInfo(int type, String name, int sex) {
        this.type = type;
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
