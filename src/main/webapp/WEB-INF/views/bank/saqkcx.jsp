<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../js.jsp"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/css/ui.jqgrid-bootstrap.css" />
    <style>
        .table-scrollable{
            border:none;
        }
        #tablediv th{
            text-align: center;
        }
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
<body>
<div class="portlet light">
    <div class="panel-group accordion">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion3" href="#collapse_3_1" aria-expanded="true" style="font-family: 微软雅黑">查询条件</a>
                </h4>
            </div>
            <div class="panel-collapse collapse in" aria-expanded="true">
                <div class="panel-body">
                    <form id="searchForm">
                        <input name="CXYWLB" type="hidden" value="B2P002"/>

                        <%--<input type="hidden" name="" value="${requestScope.yhjgdm}"/>--%>
                        <div class="row" style="margin-top: 5px">
                            <div class="col-md-1 " style="width:70px"><label>身份证号码</label></div><div class="col-md-2"><input name="SFZHM" class="form-control " style="width:100%" type="text" value=""></div>
                            <div class="col-md-1" style="width:70px"><label>客户联系电话</label></div><div class="col-md-2"><input name="KHLXDH" class="form-control " type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>办理业务类别</label></div><div class="col-md-2"><input name="BLYWLB" class="form-control " type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>姓名</label></div><div class="col-md-2"><input name="XM"  class="form-control " type="text" style="width:100%"></div>
                        </div>
                        <div class="row" style="margin-top: 5px">
                            <div class="col-md-1" style="width:70px"><label>银行名称</label></div><div class="col-md-2"><input name="YHMC" class="form-control " style="width:100%" type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>银行所在地</label></div><div class="col-md-2"><input name="YHSZD" class="form-control " type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>银行联系电话</label></div><div class="col-md-2"><input name="YHLXDH" class="form-control " type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>操作人姓名</label></div><div class="col-md-2"><input name="CZRXM" class="form-control " type="text" style="width:100%"></div>
                        </div>
                        <div class="row" style="margin-top: 5px">
                            <div class="col-md-1" style="width:70px"><label>操作人证件号码</label></div><div class="col-md-2"><input name="CZRZJHM" class="form-control" style="width:100%" type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>银行机构代码</label></div><div class="col-md-2"><input name="YHJGDM" id="YHJGDM" class="form-control " type="text" value="E002H101110101001"style="width:100%"></div>
                        </div>
                        <div class="row" style="margin-top: 5px">
                            <div class="row" style="margin-top: 5px;text-align: center"><a class="btn btn-sm btn-primary" style="color: #fff;margin-right: 10px;" onclick="btn_search()"><i class="fa fa-search"></i>查询</a><a class="btn btn-sm btn-danger" style="color: #fff;margin-right: 10px;" onclick="btn_empty()"> <i class="fa fa-trash-o"></i>清空</a></div>
                        </div>
                    </form>
                </div>
                <div id="tablediv">
                    <table id="dataList" ></table>
                </div>
            </div>

        </div>

    </div>

</div>
</body>
<script type="text/ecmascript" src="<%=basePath%>/js/jquery.jqGrid.min.js"></script>
<script type="application/javascript">
    //布局
    var fieldlist =[
        { label: '姓名', name: 'XM',align:"center",sortable:false},
        { label: '身份证号码', name: 'SFZHM',align:"center",sortable:false},
        { label: '涉案时间', name: 'SASJ',align:"center",sortable:false},
        { label:'涉案类别', name: 'SALB',align:"center",sortable:false}
    ]
    /*$("#homeserch input").on("keypress",serach);
     function serach(e) {
     if(e.keyCode!=13)return;
     $("#frm").submit();
     }*/
    $(function(){
        initGrid();
    });
    function  btn_search() {
        if(valitation_cx()){//查询验证
            var query_xml="<QUERY_XML>";
            var item ="";
            $("input").each(function () {
                item +=$(this).attr("name")+'="'+$(this).val()+'" ';
            });
            var query_xml ="<QUERY_XML><ITEM "+item+"/></QUERY_XML>";
            var xml = "<QUERY_XML><ITEM "+item+"/></QUERY_XML>";
            var cxywlb=$("input[name='CXYWLB']").val();
            var date = new Date().Format("yyyyMMdd");
            var token = encode64("E002H101110101001"+date);
            var param = {"QUERY_XML":query_xml,"CXYWLB":cxywlb,"TOKEN":token};
            initGrid(param);
        }
    }
    function initGrid(param) {
        $("#tablediv").empty();
        $("#tablediv").append( "<table id=\"dataList\" style='height: auto;width: auto'></table>");
        $("#dataList").jqGrid({
            url:ctx+"bank/jzzcx",
            datatype: 'json',
            mtype: "GET",
            rowNum: 1000,
            styleUI : 'Bootstrap',
            colModel: fieldlist,
            jsonReader:{
                root:"rows",
                repeatitems:false
            },
            postData :param,
            altRows:false,
            width:$(window.parent.window).width()-287,
            height:$(window.parent.window).height()-210,
            shrinkToFit:true,
            rownumbers:false
        });
    }
    function valitation_cx(){
        var sfzhm = $("input[name='SFZHM']").val();
        if(null==sfzhm||""==sfzhm){
            alert("身份证号码不能为空");
            return false;
        }else if(null==$("input[name='KHLXDH']").val()||""==$("input[name='KHLXDH']").val()){
            alert("客户联系电话不能为空");
            return false;
        }else if(null==$("input[name='BLYWLB']").val()||""==$("input[name='BLYWLB']").val()){
            alert("办理业务类别不能为空");
            return false;
        }else if(null==$("input[name='YHJGDM']").val()||""==$("input[name='YHJGDM']").val()){
            alert("银行机构代码不能为空");
            return false;
        }else if(null==$("input[name='YHMC']").val()||""==$("input[name='YHMC']").val()){
            alert("银行名称不能为空");
            return false;
        }else if(null==$("input[name='YHSZD']").val()||""==$("input[name='YHSZD']").val()){
            alert("银行所在地不能为空");
            return false;
        }else if(null==$("input[name='YHLXDH']").val()||""==$("input[name='YHLXDH']").val()){
            alert("银行联系电话不能为空");
            return false;
        }else if(null==$("input[name='CZRXM']").val()||""==$("input[name='CZRXM']").val()){
            alert("操作人姓名不能为空");
            return false;
        }else if(null==$("input[name='CZRZJHM']").val()||""==$("input[name='CZRZJHM']").val()){
            alert("操作人证件号码不能为空");
            return false;
        }
        return true;
    }
</script>
</html>
