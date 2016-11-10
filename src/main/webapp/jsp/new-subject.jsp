<%@ page import="com.example.employees.TeacherList" %>
<%@ page import="com.example.employees.Teacher" %>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>

</head>
<body>

<div class="container">
    <form action="/allservlet" method="post"  role="form" data-toggle="validator" >
        <c:if test ="${empty action}">
            <c:set var="action" value="addSubject"/>
        </c:if>
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="action1" name="action1" value="action1">
        <input type="hidden" id="idSubject" name="idSubject" value="${subject.id}">
        <h2></h2>
        <div class="form-group col-xs-4">

            <label for="subjectName" class="control-label col-xs-4">Subject Name:</label>
            <input type="text" name="subjectName" id="subjectName" class="form-control" value="${subject.subjectName}"/>

            <label for="hours" class="control-label col-xs-4">Hours:</label>
            <input type="text" name="hours" id="hours" class="form-control" value="${subject.hours}" required="true"/>

            <!--<label for="teacherFullName" class="control-label col-xs-4">TeacherFullName:</label>
            <input type="text" name="teacherFullName" id="teacherFullName1" class="form-control" value="" required="false"/>
            -->
            <label for="teacherId" class="control-label col-xs-4">TeacherInfo:</label>
            <select class="form-control selectpicker" name="teacherId" id="teacherId" data-style="btn-success">
                <c:forEach var = "teacher" items="${teacher}">
                    <option value="${teacher.id}" ${teacher.id == subject.teacher.id ? 'selected="selected"' : ''}>${teacher.fullName}-${teacher.rate}-${teacher.level}/${teacher.threshold}</option>
                </c:forEach>
            </select>


            <br>
            <button type="submit" class="btn btn-primary  btn-md">Accept</button>
        </div>
    </form>
</div>
</body>
</html>