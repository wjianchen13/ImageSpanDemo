package com.example.imagespandemo.bean;

public class PkBean {

    /**
     * 名称
     */
    public String name;

    /**
     * 段位
     */
    public int level;

    /**
     * 等级
     */
    public int grade;

    /**
     *  段位
     */
    public int segment;

    public PkBean() {

    }

    public PkBean(String name, int segment) {
        this.name = name;
        this.segment = segment;
    }

    public PkBean(String name, int level, int grade) {
        this.name = name;
        this.level = level;
        this.grade = grade;
    }
}
