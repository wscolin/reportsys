<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../js.jsp"%>
<html>
<head>
    <title>警银协同共享资源管理系统</title>

    <link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/css/ui.jqgrid-bootstrap.css" />
    <style>
        .table-scrollable{
            border:none;
        }
        /*#tablediv th{
            text-align: center;
        }*/
        .qxlaTitle .text-center p{
            margin:0;
        }
        .portlet.light,.portlet{
            padding:0 15px!important;
            margin-bottom:0!important;
        }

        #dataList tbody tr:nth-child(even){
            background-color: #E9EDEF;
        }
        .table>tbody>tr.success>td, .table>tbody>tr.success>th, .table>tbody>tr>td.success, .table>tbody>tr>th.success, .table>tfoot>tr.success>td, .table>tfoot>tr.success>th, .table>tfoot>tr>td.success, .table>tfoot>tr>th.success, .table>thead>tr.success>td, .table>thead>tr.success>th, .table>thead>tr>td.success, .table>thead>tr>th.success {
            background-color: #acb5c3;
        }
        .table>tbody>tr.active>td,.table>tbody>tr.active>th,.table>tbody>tr>th.active,.table>tfoot>tr.active>td,.table>tfoot>tr.active>th,.table>tfoot>tr>td.active,.table>tfoot>tr>th.active,.table>thead>tr.active>td,.table>thead>tr.active>th,.table>thead>tr>td.active,.table>thead>tr>th.active{
            background-color:#acb5c3;
        }

    </style>
</head>
<body class="page-content">
<div class="portlet light">
    <div class="panel-group accordion" id="accordion3">
    </div>
    <div id="tablediv">
        <table id="dataList" ></table>
    </div>
</div>
</body>
<script type="text/ecmascript" src="<%=basePath%>/js/jquery.jqGrid.min.js"></script>
<script type="text/ecmascript" src="<%=basePath%>/js/grid.locale-cn.js"></script>
<script type="application/javascript">
    $(function(){
        initGrid();
    });
    function initGrid() {
        $("#tablediv").empty();
        jQuery("#dataList").jqGrid(
                {
                    url : cxt+'/log/list',//组件创建完成之后请求数据的url
                    datatype : "json",//请求数据返回的类型。可选json,xml,txt
                    colNames : [ 'Inv No', 'Date', 'Client', 'Amount', 'Tax','Total', 'Notes' ],//jqGrid的列显示名字
                    colModel : [ //jqGrid每一列的配置信息。包括名字，索引，宽度,对齐方式.....
                        {name : 'id',index : 'id',width : 55},
                        {name : 'invdate',index : 'invdate',width : 90},
                        {name : 'name',index : 'name asc, invdate',width : 100},
                        {name : 'amount',index : 'amount',width : 80,align : "right"},
                        {name : 'tax',index : 'tax',width : 80,align : "right"},
                        {name : 'total',index : 'total',width : 80,align : "right"},
                        {name : 'note',index : 'note',width : 150,sortable : false}
                    ],
                    rowNum : 10,//一页显示多少条
                    rowList : [ 10, 20, 30 ],//可供用户选择一页显示多少条
                    pager : '#pager2',//表格页脚的占位符(一般是div)的id
                    sortname : 'id',//初始化的时候排序的字段
                    sortorder : "desc",//排序方式,可选desc,asc
                    mtype : "post",//向后台请求数据的ajax的类型。可选post,get
                    viewrecords : true,
                    caption : "JSON Example"//表格的标题名字
                });
    }








</script>
</html>