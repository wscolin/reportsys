<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${ctx}/plugins/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
   	<link href="${ctx}/plugins/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
    <link href="${ctx}/plugins/css/error.min.css" rel="stylesheet" type="text/css" />
<title>500</title>
</head>
    <body class=" page-500-full-page">
        <div class="row">
            <div class="col-md-12 page-500">
                <div class=" number font-red"> 500 </div>
                <div class=" details">
                    <h3>系统内部错误</h3>
                    <p> 
                        <br/> </p>
                    <p>
                        <a href="#" class="btn red btn-outline" onclick="back()"> 返回首页  </a>
                        <br> </p>
                </div>
            </div>
        </div> 
</body>
<script type="text/javascript">

        function back() {
            document.frames['admin'].history.back();
            return false;
        }
</script>
</html>