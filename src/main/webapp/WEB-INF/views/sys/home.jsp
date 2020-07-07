<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/js.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
</head>
<style>
    .number{
        font-size: 17px !important;
    }
</style>
<body class="page-content">
<%--<script>--%>
    <%--var domainctx="${domainctx}";--%>
<%--</script>--%>
<%--<script src="${domainctx}/javascript/domain.js" type="text/javascript"></script>--%>
<%--<input type="button" value="ext一键搜" onclick='openext("一键搜","http://192.168.110.22:5080/OSearchManage/search/index.jsp");'>--%>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-user"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup">${userVO.USERID}</span>
                </div>
                <div class="desc">用户名</div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="dashboard-stat red">
            <div class="visual">
                <i class="fa fa-building"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup">${userVO.DEPTNAME}</span></div>
                <div class="desc">所属部门</div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="dashboard-stat green">
            <div class="visual">
                <i class="fa fa-male"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup" title="${roles}" >${fn:length(roles)>13?fn:replace(roles,fn:substring(roles,13,fn:length(roles)),'...'):roles}</span>
                </div>
                <div class="desc">最高级别角色</div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="dashboard-stat purple">
            <div class="visual">
                <i class="fa fa-group"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup"></span>${userount}</div>
                <div class="desc">部门人员</div>
            </div>
        </div>
    </div>
</body>
</html>
