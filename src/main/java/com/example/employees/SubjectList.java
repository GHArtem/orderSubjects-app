package com.example.employees;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 016 16.10.16.
 */
public class SubjectList {
    private static TeacherService teacherService = new TeacherService();
    private static final List<Subject> subjectList = new ArrayList();
    private TeacherList teacherList;

    private SubjectList(){
    }

    static{
        try {
            subjectList.add(new Subject("Методы оптимизации (лекции, I поток)",264,teacherService.getTeacher(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List <Subject> getInstance(){
        return subjectList;
    }

    public TeacherList getTeacherList() {
        return teacherList;
    }
}
