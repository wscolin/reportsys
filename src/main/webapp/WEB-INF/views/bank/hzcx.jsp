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
                        <input name="CXYWLB" type="hidden" value="B2P003"/>

                        <%--<input type="hidden" name="" value="${requestScope.yhjgdm}"/>--%>
                        <div class="row" style="margin-top: 5px">
                            <div class="col-md-1 " style="width:70px"><label>证件类型</label></div>
                            <div class="col-md-2">
                                <select name="ZJLX">
                                    <c:forEach items="${zjlx}" var="temp">
                                        <option value="${temp.ITEM_CODE}">${temp.ITEM_NAME}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-1" style="width:70px"><label>证件号码</label></div><div class="col-md-2"><input name="ZJHM" class="form-control" style="width:100%" type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>姓名</label></div><div class="col-md-2"><input name="XM" class="form-control " type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>办理业务类别</label></div><div class="col-md-2"><input name="BLYWLB" class="form-control " type="text"></div>

                        </div>
                        <div class="row" style="margin-top: 5px">
                            <div class="col-md-1" style="width:70px"><label>银行机构代码</label></div><div class="col-md-2"><input name="YHJGDM" id="YHJGDM" class="form-control " type="text" value="${requestScope.yhjgdm}"style="width:100%"></div>
                            <div class="col-md-1" style="width:70px"><label>银行名称</label></div><div class="col-md-2"><input name="YHMC" class="form-control " type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>银行所在地</label></div><div class="col-md-2"><input name="YHSZD" class="form-control " type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>银行联系电话</label></div><div class="col-md-2"><input name="YHLXDH" class="form-control " type="text"></div>
                        </div>
                        <div class="row" style="margin-top: 5px">
                            <div class="col-md-1" style="width:70px"><label>客户联系电话</label></div><div class="col-md-2"><input name="KHLXDH" class="form-control " type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>操作人证件号码</label></div><div class="col-md-2"><input name="CZRZJHM" class="form-control" style="width:100%" type="text"></div>
                            <div class="col-md-1" style="width:70px"><label>操作人姓名</label></div><div class="col-md-2"><input name="CZRXM" class="form-control " type="text" ></div>
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
        { label: '身份证号', name: 'SFZHM',align:"center",sortable:false},
        { label: '姓名', name: 'XM',align:"center",sortable:false},
        { label: '性别', name: 'XB',align:"center",sortable:false},
        { label:'证件类型', name: 'ZJLX',align:"center",sortable:false},
        { label:"证件号码", name: 'ZJHM',align:"center",sortable:false},
        { label:'前往国或地区', name: 'QWGHDQ',align:"center",sortable:false},
        { label:'证件有效期', name: 'ZJYXQ',align:"center",sortable:false},
        { label:'办理时间', name: 'BLSJ',align:"center",sortable:false}
    ]
    /*$("#homeserch input").on("keypress",serach);
     function serach(e) {
     if(e.keyCode!=13)return;
     $("#frm").submit();
     }*/
    $(function(){
        initGrid();
    });
    function encode64(input) {
        var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"
                + "wxyz0123456789+/" + "=";
        var output = "";
        var chr1, chr2, chr3 = "";
        var enc1, enc2, enc3, enc4 = "";
        var i = 0;
        do {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
                    + keyStr.charAt(enc3) + keyStr.charAt(enc4);
            chr1 = chr2 = chr3 = "";
            enc1 = enc2 = enc3 = enc4 = "";
        } while (i < input.length);

        return output;
    }
    Date.prototype.Format = function(fmt)
    { //author: meizz
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }
    function  btn_search() {
        if(valitation_cx()){//查询验证
            var query_xml="<QUERY_XML>";
            var item ="";
            //获取所有input的name和value
            $("input").each(function () {
                item +=$(this).attr("name")+'="'+$(this).val()+'" ';
            });
            //获取select标签的name和value值
            var zjlx = $("select[name='ZJLX']").attr("name")+'="'+$("select[name='ZJLX']").val()+'"';
            item +=  zjlx;
            var query_xml ="<QUERY_XML><ITEM "+item+"/></QUERY_XML>";
            var cxywlb=$("input[name='CXYWLB']").val();
            var date = new Date().Format("yyyyMMdd");
            var yhjgdm=$("#YHJGDM").val();
            var token = encode64(yhjgdm+date);
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
      if(null==$("input[name='KHLXDH']").val()||""==$("input[name='KHLXDH']").val()){
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
        }else if(null==$("input[name='XM']").val()||""==$("input[name='XM']").val()){
          alert("姓名不能为空");
          return false;
       }
        return true;
    }
</script>
</html>