<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${ctx}/plugins/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/plugins/css/error.min.css" rel="stylesheet" type="text/css" />
<title>401</title>
</head>
    <body class=" page-404-full-page">
        <div class="row">
            <div class="col-md-12 page-404">
                <div class=" number font-red"> 401 </div>
                <div class=" details">
                    <h3>你没权限操作此功能，请联系管理员！</h3>
                </div>
            </div>
        </div> 
	</body>
</html>