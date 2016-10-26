<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
</head>

<body>

<div class="container">
    <div class="col-xs-6">
        <h2>Subjects</h2>
        <!--Subject List-->
        <c:if test="${not empty message}">
            <div class="alert alert-success">
                    ${message}
            </div>
        </c:if>
        <form action="/allservlet" method="post" id="subjectForm" role="form" >
            <input type="hidden" id="idSubject" name="idSubject">
            <input type="hidden" id="action" name="action">
            <input type="hidden" id="action1" name="action1">
            <c:choose>
                <c:when test="${not empty subjectList}">
                    <div class="table-responsive">
                        <table  class="table table-striped">
                            <thead>
                            <tr>
                                <td>#</td>
                                <td>Название дисциплины</td>
                                <td>Количество часов</td>
                                <td>Закрепленный преподаватель</td>
                                <td></td>
                            </tr>
                            </thead>
                            <c:forEach var="subject" items="${subjectList}">
                                <c:set var="classSucess" value=""/>
                                <c:if test ="${idSubject == subject.id}">
                                    <c:set var="classSucess" value="info"/>
                                </c:if>
                                <tr class="${classSucess}">
                                    <td>
                                        <a href="/allservlet?idSubject=${subject.id}&searchAction=searchSubjectById">${subject.id-100}</a>
                                    </td>
                                    <td>${subject.subjectName}</td>
                                    <td>${subject.hours}</td>
                                    <td>${subject.teacher.fullName}[${subject.teacher.rate}]</td>
                                    <td><a href="#removeSubject" id="removeSubject"
                                           onclick="document.getElementById('action').value = 'removeSubject';document.getElementById('idSubject').value = '${subject.id}';

                                                   document.getElementById('subjectForm').submit();">
                                        <span class="glyphicon glyphicon-trash"/>
                                    </a>

                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <br>
                    <div class="alert alert-info">
                        No subjects found
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
        <form action ="/allservlet?action=preAddSubject&action1=" method="post">
            <div class="col-xs-6">
                <br>
                <button type="submit" class="btn btn-primary  btn-md">New subject</button>
            </div>
        </form>

        <form action="/allservlet?action=getExcelSubjects&action1=action1" method="post" enctype="multipart/form-data">
                <br>
                <%--
                    <input type="hidden" name="action" value="getExcelSubjects">
                    <input type="hidden" name="action1" value="action1">
                --%>
                <label class="btn btn-link">
                    Choose File<input type="file" name="getFileSubjects" id="getFileSubjects" style="display: none;">
                </label>
                <button type="submit" class="btn btn-primary  btn-md">Get Subjects</button>
        </form>

        <form action="/allservlet?action=printAll&action1=action1" method="post">
            <button type="submit" class="btn btn-success  btn-md">Print All</button>
        </form>

    </div>
    <div class="col-xs-6">
        <h2>Teachers</h2>
        <c:if test="${not empty message1}">
            <div class="alert alert-success">
                    ${message1}
            </div>
        </c:if>
        <form action="/allservlet" method="post" id="teacherForm" role="form" >
            <input type="hidden" id="idTeacher" name="idTeacher">
            <input type="hidden" id="action1" name="action1">
            <input type="hidden" id="action" name="action">
            <c:choose>
            <c:when test="${not empty teacherList}">
        <div class="table-responsive">
            <table  class="table table-striped">
                <thead>
                <tr>
                    <td>#</td>
                    <td>FullName</td>
                    <td>Rate</td>
                    <td>Hours</td>
                    <td></td>
                </tr>
                </thead>
                <c:forEach var="teacher" items="${teacherList}">
                    <c:set var="classSucess" value=""/>
                    <c:if test ="${idTeacher == teacher.id}">
                        <c:set var="classSucess" value="info"/>
                    </c:if>
                    <c:choose>
                        <c:when test ="${teacher.id == 0}">
                            <tr class="${classSucess}">
                                <td></td>
                                <td>${teacher.fullName}</td>
                                <td></td>
                                <td>${teacher.level}/${teacher.threshold}</td>
                                <td></td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr class="${classSucess}">
                                <td>
                                    <a href="/allservlet?idTeacher=${teacher.id}&searchAction=searchTeacherById">${teacher.id}</a>
                                </td>
                                <td>${teacher.fullName}</td>
                                <td>${teacher.rate}</td>
                                <td>${teacher.level}/${teacher.threshold}</td>
                                <td><a href="#removeTeacher" id="removeTeacher"
                                       onclick="document.getElementById('action1').value = 'removeTeacher';document.getElementById('idTeacher').value = '${teacher.id}';

                                               document.getElementById('teacherForm').submit();">
                                    <span class="glyphicon glyphicon-trash"/>
                                </a>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </table>
        </div>
            </c:when>
                <c:otherwise>
                    <br>
                    <div class="alert alert-info">
                        No teachers found
                    </div>
                </c:otherwise>
            </c:choose>
        </form>

        <form action ="jsp/new-teacher.jsp">
            <div class="col-xs-6">
                <br>
                <button type="submit" class="btn btn-primary  btn-md">New teacher</button>
            </div>
        </form>

        <form action="/allservlet?action=getExcelTeachers&action1=action1" method="post" enctype="multipart/form-data">
            <div class="col-xs-6">
                <br>
                <%--
                    <input type="hidden" name="action" value="getExcelSubjects">
                    <input type="hidden" name="action1" value="action1">
                --%>
                <label class="btn btn-link">
                    Choose *.xsl File<input type="file" name="getExcelTeachers" id="getExcelTeachers" style="display: none;">
                </label>
                <button type="submit" class="btn btn-primary  btn-md">Get *.xsl File</button>
            </div>
        </form>

        <form action="/allservlet?action=printExcelTeachers" method="post" enctype="multipart/form-data">
            <label class="btn btn-link">
                Print  <input type="file" name="printExcelTeachers" id="printExcelTeachers" webkitdirectory directory multiple style="display:none;"/>
            </label>
        </form>

    </div>

</div>
</body>
</html>