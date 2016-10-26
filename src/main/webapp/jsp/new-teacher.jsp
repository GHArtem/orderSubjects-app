<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form action="/allservlet" method="post"  role="form" data-toggle="validator" >
        <c:if test ="${empty action}">
            <c:set var="action" value="addTeacher"/>
        </c:if>
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="action1" name="action1" value="action1">
        <input type="hidden" id="idTeacher" name="idTeacher" value="${teacher.id}">
        <h2>Teacher</h2>
        <div class="form-group col-xs-4">

            <label for="fullName" class="control-label col-xs-4">Teacher FullName:</label>
            <input type="text" name="fullName" id="fullName" class="form-control" value="${teacher.fullName}" required="true"/>

            <label for="rate" class="control-label col-xs-4">Rate:</label>
            <input type="text" name="rate" id="rate" class="form-control" value="${teacher.rate}" required="true"/>

            <br></br>
            <button type="submit" class="btn btn-primary  btn-md">Accept</button>
        </div>
    </form>
</div>
</body>
</html>