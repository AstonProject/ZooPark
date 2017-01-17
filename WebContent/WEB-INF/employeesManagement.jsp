<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/employeesManagement.css" />
<title>EmployeesManagement</title>
</head>
<body>
<div id="employees_container" class="ec1">
<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/healer.png" alt="logo" /></span>
<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/cleaner.png" alt="logo" /></span>
<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/security.png" alt="logo" /></span>
</div>
<div id="employees_container" class="ec2"><p>Assigned</p><span class="assigned_healer"></span><span class="assigned_cleaner"></span><span class="assigned_security"></span></div>
<div id="employees_container" class="ec2"><p>Unassigned</p><span class="unassigned_healer"></span><span class="unassigned_cleaner"></span><span class="unassigned_security"></span></div>
<div id="employees_container" class="ec2"><p>Total</p><span class="total_healer"></span><span class="total_cleaner"></span><span class="total_security"></span></div>

<script src="${pageContext.request.contextPath}/assets/vendors/jquery-3.1.1.js"></script>
<script src="${pageContext.request.contextPath}/assets/scripts/zoo.js"></script>
<script src="${pageContext.request.contextPath}/assets/scripts/employeesManagement.js"></script>
</body>

</html>