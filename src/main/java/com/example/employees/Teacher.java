package com.example.employees;


import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by User on 016 16.10.16.
 */
public class Teacher {
    private long id;
    private String fullName;
    private double rate;
    private static final int load = Integer.parseInt("700");
    private double threshold;
    private double level;
    private static final AtomicLong counter = new AtomicLong(-1);

    public Teacher(String fullName, double rate) {
        this.fullName = fullName;
        this.rate = rate;
        this.threshold = rate*load;
        this.level=0;
        this.id = counter.incrementAndGet();
    }

    public void addLevel(double level) {
        this.level += level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
        this.threshold = rate*load;
    }

    public static int getLoad() {
        return load;
    }


    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }
}
