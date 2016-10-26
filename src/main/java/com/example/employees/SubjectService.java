package com.example.employees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by User on 016 16.10.16.
 */
public class SubjectService {
    List<Subject> subjectList = SubjectList.getInstance();


    public List<Subject> getAllSubjects() {
        return subjectList;
    }


    public Subject getSubject(long id) throws Exception {
        Optional<Subject> match
                = subjectList.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new Exception("The Subject id " + id + " not found");
        }
    }

    public long addSubject(Subject subject) {
        subjectList.add(subject);
        return subject.getId();
    }

    public boolean updateSubject(Subject customer) {
        int matchIdx = 0;
        Optional<Subject> match = subjectList.stream()
                .filter(c -> c.getId() == customer.getId())
                .findFirst();
        if (match.isPresent()) {
            matchIdx = subjectList.indexOf(match.get());
            subjectList.set(matchIdx, customer);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteSubject(long id) {
        Predicate<Subject> subject = e -> e.getId() == id;
        if (subjectList.removeIf(subject)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean renameTeacherToNotSelected(Teacher teacher,Teacher teacherNull)
    {
        List <Subject> subjectListWithThisTeacher = subjectList.stream()
                .filter(e -> e.getTeacher().getId() == teacher.getId())
                .collect(Collectors.toList());
        if(!subjectListWithThisTeacher.isEmpty()) {
            subjectListWithThisTeacher.stream().forEach(e -> e.setTeacher(teacherNull));
            return true;
        } else {
            return false;
        }
    }
}

