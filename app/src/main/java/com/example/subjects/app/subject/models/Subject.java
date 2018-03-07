package com.example.subjects.app.subject.models;

/**
 * Created by vivek on 07/03/18.
 */

public class Subject {
    private static final String TAG = Subject.class.getSimpleName();

    private long id;
    private String title;
    private String desc;
    private String imgName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}