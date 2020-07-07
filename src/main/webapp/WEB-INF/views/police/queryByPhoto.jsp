<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>警银协同共享资源管理系统</title>
    <%@ include file="../js.jsp"%>
</head>
<body class="page-content">
<div class="my-col-md">
    <div class="portlet light">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <div class="input-icon right" id="homeserch">
                <i class="fa fa-search" onclick="$('#frm').submit();"></i>
                <form id="frm" action="${ctx}/police/queryByPhoto">
                    <input name="identity" type="text" class="form-control input-circle" placeholder="手机号" value="${identity}">
                </form>
            </div>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
<%--<div class="portlet-body">
    <div class="form-group">
        <div class="col-md-4">
            <label class="control-label col-md-3 align-label">
                <span class="ellipsis">姓名:</span>
            </label>
            <div class="col-md-9">
                <input type="text" data-required="1" class="form-control" value="${map.ITEMS.ITEM.XM}" readonly>
            </div>
        </div>
        <div class="col-md-4">
            <label class="control-label col-md-3 align-label">
                <span class="ellipsis">身份证号码:</span>
            </label>
            <div class="col-md-9">
                <input type="text" data-required="1" class="form-control" value="${map.ITEMS.ITEM.SFZHM}" readonly>
            </div>
        </div>
        <div class="col-md-4">
            <label class="control-label col-md-3 align-label">
                <span class="ellipsis">性别:</span>
            </label>
            <div class="col-md-9">
                <input type="text" data-required="1" class="form-control" value="${map.ITEMS.ITEM.XB}" readonly>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-4">
            <label class="control-label col-md-3 align-label">
                <span class="ellipsis">民族:</span>
            </label>
            <div class="col-md-9">
                <input type="text" data-required="1" class="form-control" value="${map.ITEMS.ITEM.MZ}" readonly>
            </div>
        </div>
        <div class="col-md-4">
            <label class="control-label col-md-3 align-label">
                <span class="ellipsis">出生日期:</span>
            </label>
            <div class="col-md-9">
                <input type="text" data-required="1" class="form-control" value="${map.ITEMS.ITEM.CSRQ}" readonly>
            </div>
        </div>
        <div class="col-md-4">
            <label class="control-label col-md-3 align-label">
                <span class="ellipsis">户籍地址:</span>
            </label>
            <div class="col-md-9">
                <input type="text" data-required="1" class="form-control" value="${map.ITEMS.ITEM.HJDZ}" readonly>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-4">
            <label class="control-label col-md-3 align-label">
                <span class="ellipsis">发证日期:</span>
            </label>
            <div class="col-md-9">
                <input type="text" data-required="1" class="form-control" value="${map.ITEMS.ITEM.FZRQ}" readonly>
            </div>
        </div>
        <div class="col-md-4">
            <label class="control-label col-md-3 align-label">
                <span class="ellipsis">有效期止:</span>
            </label>
            <div class="col-md-9">
                <input type="text" data-required="1" class="form-control" value="${map.ITEMS.ITEM.YXRQ}" readonly>
            </div>
        </div>
    </div>
</div>
</body>--%>
<script type="application/javascript">
    $("#homeserch input").on("keypress",serach);
    function serach(e) {
        if(e.keyCode!=13)return;
        $("#frm").submit();
    }
</script>
</html>