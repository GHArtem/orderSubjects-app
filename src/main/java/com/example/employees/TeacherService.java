package com.example.employees;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by User on 016 16.10.16.
 */
public class TeacherService {
    List<Teacher> teacherList = TeacherList.getInstance();
    SubjectService subjectService = new SubjectService();

    public List<Teacher> getAllTeachers() {
        return teacherList;
    }


    public Teacher getTeacher(long id) throws Exception {
        Optional<Teacher> match
                = teacherList.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new Exception("The Teacher id " + id + " not found");
        }
    }

    public long addTeacher(Teacher teacher) {
        teacherList.add(teacher);
        return teacher.getId();
    }

    public boolean updateTeacher(Teacher customer) {
        int matchIdx = 0;
        Optional<Teacher> match = teacherList.stream()
                .filter(c -> c.getId() == customer.getId())
                .findFirst();
        if (match.isPresent()) {
            Teacher teacher = match.get();
            teacher.setFullName(customer.getFullName());
            teacher.setRate(customer.getRate());
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteTeacher(long id) throws Exception {
        Teacher teacher = getTeacher(id);
        Predicate<Teacher> Teacher = e -> e.getId() == id;
        if (teacherList.removeIf(Teacher)) {
            subjectService.renameTeacherToNotSelected(teacher,getTeacher(0));
            return true;
        } else {
            return false;
        }
    }

    public Teacher findTeacherByName(String teacherFullName) throws Exception {
        Optional<Teacher> teacherOptinal = teacherList.stream().filter(e -> e.getFullName().equals(teacherFullName)).findFirst();
        if (teacherOptinal.isPresent()) {
            Teacher teacher = teacherOptinal.get();
            return teacher;
        } else {
            return getTeacher(0);
        }
    }
}