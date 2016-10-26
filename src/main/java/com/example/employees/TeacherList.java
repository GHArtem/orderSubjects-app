package com.example.employees;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 016 16.10.16.
 */
public class TeacherList {
    private static final List<Teacher> teacherList = new ArrayList<Teacher>();

    private  TeacherList() {
    }

    static{
        teacherList.add(new Teacher("не выбрано",0.0));
        teacherList.add(new Teacher("Лавринович Л.И.",0.5));
        teacherList.add(new Teacher("Дмитрук Н.М.",0.5));
    }

    public static List<Teacher> getInstance() {
        return teacherList;
    }
}
