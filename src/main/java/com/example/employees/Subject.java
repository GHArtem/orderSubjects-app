package com.example.employees;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by User on 016 16.10.16.
 */
public class Subject {
    private long id;
    private String subjectName;
    private int hours;
    private Teacher teacher;
    private static final AtomicLong counter = new AtomicLong(100);

    public Subject(String subjectName, int hours, Teacher teacher) {
        teacher.addLevel(hours);
        this.subjectName = subjectName;
        this.hours = hours;
        this.teacher = teacher;
        this.id = counter.incrementAndGet();
    }



    public static AtomicLong getCounter() {
        return counter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
