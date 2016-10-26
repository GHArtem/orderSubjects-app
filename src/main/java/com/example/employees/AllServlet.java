package com.example.employees;

import com.example.excelrw.ReaderExcelFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.io.InputStream;
import com.example.excelrw.ReaderExcelFile;
import com.example.excelrw.WriterExcelFile;

/**
 * Created by User on 016 16.10.16.
 */
@WebServlet(
        name = "AllServlet",
        urlPatterns = {"/allservlet"}
)
@MultipartConfig(fileSizeThreshold=1024*1024,
        maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)

public class AllServlet extends HttpServlet {
    SubjectService subjectService = new SubjectService();
    TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");


        String action = req.getParameter("searchAction");
        System.out.println("doGet : "+action);
        if (action!=null){
            switch (action) {
                case "searchSubjectById":
                    searchSubjectById(req, resp);
                    break;
                case "searchTeacherById":
                    searchTeacherById(req, resp);
                    break;
            }
        }else{
            List<Subject> result = subjectService.getAllSubjects();
            List<Teacher> result1 = teacherService.getAllTeachers();
            forwardListAll(req, resp, result,result1);
        }
    }

    private void getExcelSubjects(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Part getFileSubjects = req.getPart("getFileSubjects");
        //String getFileSubjectsName = Paths.get(getSubmittedFileName(getFileSubjects)).getFileName().toString();
        InputStream inputStream = getFileSubjects.getInputStream();
        ReaderExcelFile readerExcelFile = new ReaderExcelFile(inputStream,ReaderExcelFile.READ_AS_SUBJECT);
        readerExcelFile.read();
        List<Subject> result = subjectService.getAllSubjects();
        List<Teacher> result1 = teacherService.getAllTeachers();
        forwardListAll(req, resp, result,result1);

    }

    private void getExcelTeachers(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Part getFileSubjects = req.getPart("getExcelTeachers");
        //String getFileSubjectsName = Paths.get(getSubmittedFileName(getFileSubjects)).getFileName().toString();
        InputStream inputStream = getFileSubjects.getInputStream();
        ReaderExcelFile readerExcelFile = new ReaderExcelFile(inputStream,ReaderExcelFile.READ_AS_TEACHER);
        readerExcelFile.read();
        List<Subject> result = subjectService.getAllSubjects();
        List<Teacher> result1 = teacherService.getAllTeachers();
        forwardListAll(req, resp, result,result1);

    }

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    private void searchSubjectById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long idSubject = Integer.valueOf(req.getParameter("idSubject"));
        Subject subject = null;
        try {
            subject = subjectService.getSubject(idSubject);
        } catch (Exception ex) {
            Logger.getLogger(AllServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Teacher> result1 = teacherService.getAllTeachers();
        req.setAttribute("subject", subject);
        req.setAttribute("teacher", result1);
        req.setAttribute("action", "editSubject");
        String nextJSP = "/jsp/new-subject.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }

    private void searchTeacherById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long idTeacher = Integer.valueOf(req.getParameter("idTeacher"));
        Teacher teacher = null;
        try {
            teacher = teacherService.getTeacher(idTeacher);
        } catch (Exception ex) {
            Logger.getLogger(AllServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.setAttribute("teacher", teacher);
        req.setAttribute("action", "editTeacher");
        String nextJSP = "/jsp/new-teacher.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }

    private void forwardListAll(HttpServletRequest req, HttpServletResponse resp, List subjectList,List teacherList)
            throws ServletException, IOException {
        String nextJSP = "/jsp/list-subject.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("teacherList", teacherList);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String action1 = req.getParameter("action1");
        System.out.println("doPost: "+action+" "+action1);

        switch (action1) {
            case "removeTeacher":
                try {
                    removeTeacherByName(req, resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        try {
            switch (action) {
                case "getExcelSubjects":
                    getExcelSubjects(req, resp);
                    break;
                case "getExcelTeachers":
                    getExcelTeachers(req, resp);
                    break;
                case "printAll":
                    printAll(req, resp);
                    break;
                case "preAddSubject":
                    preAddSubject(req, resp);
                    break;
                case "addSubject":
                    addSubjectAction(req, resp);
                    break;
                case "addTeacher":
                    addTeacherAction(req, resp);
                    break;
                case "edit":
                    editEmployeeAction(req, resp);
                    break;
                case "editTeacher":
                    editTeacherAction(req, resp);
                    break;
                case "editSubject":
                    editSubjectAction(req, resp);
                    break;
                case "removeSubject":
                    removeSubjectByName(req, resp);
                    break;
            }
        }
            catch (Exception e) {
                e.printStackTrace();
            }


    }

    private void printAll(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        WriterExcelFile.write();
        List<Subject> result = subjectService.getAllSubjects();
        List<Teacher> result1 = teacherService.getAllTeachers();
        forwardListAll(req, resp, result,result1);
    }

    private void preAddSubject(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        String nextJSP = "/jsp/new-subject.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        List<Teacher> result1 = teacherService.getAllTeachers();
        req.setAttribute("teacher", result1);
        dispatcher.forward(req, resp);
    }

    private void addEmployeeAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String lastName = req.getParameter("lastName");
        String birthday = req.getParameter("birthDate");
        String role = req.getParameter("role");
        String department = req.getParameter("department");
        String email = req.getParameter("email");
        Employee employee = new Employee(name, lastName, birthday, role, department, email);
     //   long idEmployee = employeeService.addEmployee(employee);
     //   List<Employee> employeeList = employeeService.getAllEmployees();
     //   req.setAttribute("idEmployee", idEmployee);
        String message = "The new employee has been successfully created.";
        req.setAttribute("message", message);
     //   forwardListEmployees(req, resp, employeeList);
    }

    private void addSubjectAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String subjectName = req.getParameter("subjectName");
        int hours = Integer.parseInt(req.getParameter("hours"));
        long teacherId = Integer.valueOf(req.getParameter("teacherId"));
        Subject subject = null;
        try {
            subject = new Subject(subjectName,hours,teacherService.getTeacher(teacherId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        long idSubject = subjectService.addSubject(subject);
        List<Subject> result = subjectService.getAllSubjects();
        List<Teacher> result1 = teacherService.getAllTeachers();
        req.setAttribute("idSubject", idSubject);
        String message = "The new subject has been successfully created.";
        req.setAttribute("message", message);
        forwardListAll(req, resp, result,result1);
    }

    private void addTeacherAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        double rate = Integer.parseInt(req.getParameter("rate"));
        Teacher teacher = null;
        try {
            teacher = new Teacher(fullName,rate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long idTeacher = teacherService.addTeacher(teacher);
        List<Subject> result = subjectService.getAllSubjects();
        List<Teacher> result1 = teacherService.getAllTeachers();
        req.setAttribute("idTeacher", idTeacher);
        String message = "The new subject has been successfully created.";
        req.setAttribute("message1", message);
        forwardListAll(req, resp, result,result1);
    }


    private void editEmployeeAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String lastName = req.getParameter("lastName");
        String birthday = req.getParameter("birthDate");
        String role = req.getParameter("role");
        String department = req.getParameter("department");
        String email = req.getParameter("email");
        long idEmployee = Integer.valueOf(req.getParameter("idEmployee"));
        Employee employee = new Employee(name, lastName, birthday, role, department, email, idEmployee);
        employee.setId(idEmployee);
      //  boolean success = employeeService.updateEmployee(employee);
        String message = null;
       // if (success) {
            message = "The employee has been successfully updated.";
      //  }
      //  List<Employee> employeeList = employeeService.getAllEmployees();
     //   req.setAttribute("idEmployee", idEmployee);
     //   req.setAttribute("message", message);
      //  forwardListEmployees(req, resp, employeeList);
    }

    private void editSubjectAction(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        String subjectName = req.getParameter("subjectName");
        int hours = Integer.parseInt(req.getParameter("hours"));
        long teacherId = Integer.valueOf(req.getParameter("teacherId"));
        long idSubject = Integer.valueOf(req.getParameter("idSubject"));
        Subject subject = subjectService.getSubject(idSubject);
        subject.getTeacher().addLevel(-subject.getHours());
        subject = new Subject(subjectName,hours,teacherService.getTeacher(teacherId));
        subject.setId(idSubject);
        boolean success = subjectService.updateSubject(subject);
        String message = null;
         if (success) {
        message = "The subject has been successfully updated.";
          }
        List<Subject> result = subjectService.getAllSubjects();
        List<Teacher> result1 = teacherService.getAllTeachers();
        req.setAttribute("idSubject", idSubject);
        req.setAttribute("message", message);
        forwardListAll(req, resp, result,result1);
    }

    private void editTeacherAction(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        String fullName = req.getParameter("fullName");
        double rate = Integer.parseInt(req.getParameter("rate"));
        long idTeacher = Integer.valueOf(req.getParameter("idTeacher"));
        Teacher teacher = new Teacher(fullName,rate);
        teacher.setId(idTeacher);
        boolean success = teacherService.updateTeacher(teacher);
        String message = null;
        if (success) {
            message = "The teacher has been successfully updated.";
        }
        List<Subject> result = subjectService.getAllSubjects();
        List<Teacher> result1 = teacherService.getAllTeachers();
        req.setAttribute("idTeacher", idTeacher);
        req.setAttribute("message1", message);
        forwardListAll(req, resp, result,result1);
    }



    private void removeSubjectByName(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        long idSubject = Integer.valueOf(req.getParameter("idSubject"));
        Subject subject = subjectService.getSubject(idSubject);
        subject.getTeacher().addLevel(-subject.getHours());
        boolean confirm = subjectService.deleteSubject(idSubject);
        if (confirm){
            String message = "The subject has been successfully removed.";
            req.setAttribute("message", message);
        }
        List<Subject> result = subjectService.getAllSubjects();
        List<Teacher> result1 = teacherService.getAllTeachers();
        forwardListAll(req, resp, result,result1);
    }

    private void removeTeacherByName(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        long idTeacher = Integer.valueOf(req.getParameter("idTeacher"));
        boolean confirm = teacherService.deleteTeacher(idTeacher);
        if (confirm){
            String message = "The teacher has been successfully removed.";
            req.setAttribute("message1", message);
        }
        List<Subject> result = subjectService.getAllSubjects();
        List<Teacher> result1 = teacherService.getAllTeachers();
        forwardListAll(req, resp, result, result1);
    }



}

