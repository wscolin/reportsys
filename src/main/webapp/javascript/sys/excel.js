$(function () {
    var today = new Date();
    var year = today.getFullYear();
    var moth = today.getMonth()+1;
    $("li[role=\"presentation\"]").on("click",function () {
        var index = $(this).find("a").attr("index");
        var date  = $("#querydate").val();
        initTable(date,index);
    });
    $(".dateDiv").datepicker({
        format: 'yyyy-mm',
        weekStart: 1,
        autoclose: true,
        startView: 1,
        maxViewMode: 1,
        minViewMode:1,
        forceParse: false,
        language: 'zh-CN'
    });
    $("#querydateDiv").datepicker('setDate',year+"-"+moth);
    $("#kssjDiv").datepicker('setDate',year+"-"+moth);
    $("#kssjDiv_export").datepicker('setDate',year+"-"+moth);
    initTable($("#querydate").val());
});

function queryDate(_this) {
    var date = $(_this).val();
    var index =  $(".active a").attr("index");
    initTable(date,index);
    $(".datepicker-dropdown").hide();
}
//表格初始化
function initTable(date,index) {
    var table = $('#itemTable');
    var oTable = table.dataTable({
        dom:"<'row'<'search text-left'f>r>t<'row pageRow'<'col-sm-3 col-md-3 col-lg-3 data_len'l><'col-sm-3 col-md-3 col-lg-3'i><'col-sm-6 col-md-6 col-lg-6'p>>",
        scrollY:true,
        processing : true,
        serverSide : true,
        bSort : false,
        searching : false,
        pagingType : "full_numbers",
        lengthChange : true,
        deferRender: true,
        bDestroy:true,
        lengthMenu : [ 10, 25, 50 ],
        ajax : {
            url:ctx+"/excel/list?date="+date+"&index="+index,
            type : "POST",
            error: AjaxError
        },
        select : {
            style : 'single'
        },
        columns : [
            {
                data:null,
                sClass:"text-center",
                fnCreatedCell:function (nTd,sData,oData,iRow,iCol) {
                    var startnum=this.api().page()*(this.api().page.info().length);
                    $(nTd).html(iRow+1+startnum);
                }
            },{
                data : "kmbm",
                width : "100px",
                "defaultContent": "<i>Not set</i>"
            },{
                data : "kmmc",
                width : "260px",
                "defaultContent": ""
            }, {
                data : "amt",
                width : "260px",
                "defaultContent": ""

            }, {
                data : "sz",
                "defaultContent": ""
            }, {
                data : "gxq",
                width : "40px",
                "defaultContent": ""
            }, {
                data : "lcq",
                width : "40px",
                "defaultContent": ""
            }
        ],
     /*   aoColumnDefs : [
            {
                targets : 4,
                data : "STATE",
                render : function(data, type, full) { //返回自定义内容
                    if (data == "0") {
                        return "<span class='label label-sm label-primary'>正常</span>";
                    } else {
                        return "<span class='label label-sm label-danger'>注销</span>";
                    }
                }
            }
        ],*/
        language : {
            url:ctx+"/plugins/lang-zh_CN.json"
        },initComplete:function () {
            tableHeight();
        }
    });

}
//验证
/*$("#fileForm").validate({
    rules : {
        KSRQ : {
            required:true
        }
    },
    messages : {
        KSRQ : {
            required: "日期不能为空"
        }
    },
    highlight : function(element) {
        $(element).closest('div').addClass('has-error');
    },
    success : function(label) {
        $(label).closest('div').removeClass('has-error');
        $(label).remove();
    }
});*/
//异常错误提示
function AjaxError( xhr, textStatus, error ) {
    if ( textStatus === 'timeout' ) {
        window.parent.toastr[MES_ERROR]("服务器没有响应！");
    }
    else {
        window.parent.toastr[MES_ERROR]("服务器出错,请重试！");
    }
    $("#itemTable").dataTable().fnProcessingIndicator(false );
}
function btn_import_click() {
    $("h4").text("导入-收入-支出表");
	$('#fileModal').modal('show');
}
function btn_export_click() {
    $("h4").text("导出-收入-支出表");
    $('#fileModal_exprot').modal('show');

}
function btn_export_confrim (){
	var date = $("#KSRQ_exprot").val();
    $('#fileModal_exprot').modal('hide');
    window.location.href=ctx+"/excel/excelExportByTemplate?date="+date;
/*    $.ajax({
        url:ctx+ "/excel/excelExportByTemplate?date="+date,
        type : "GET",
        data : null,
        cache: false,
        contentType: false,
        processData: false,
        dataType : "json",
        success: function (data,status){
            if(data.responseText=="success"){
                window.parent.toastr[MES_SUCCESS]("导出成功！！");
            }else if(data.responseText=="error") {
                window.parent.toastr[MES_WARN]("导出成功失败！！");
            }
            $('#fileModal_exprot').modal('hide');
        }
    });*/
}
function btn_dwload_mb() {
	window.location.href=ctx+"/excel/dwloadmb";
}
function btn_savefile_click() {
    var date = $("#KSRQ").val();
    if(date==null||date==""||date=="undefined"){
        window.parent.toastr[MES_ERROR]("日期不能空！！");
        return ;
	}
	if($("#fileUpload").val()){
		var formData=new FormData($("#fileForm")[0]);
		$.ajax({
			url:ctx+ "/excel/importfile?date="+date,
			type : "POST",
			data : formData,
			cache: false,
			contentType: false,
			processData: false,
			dataType : "json",
			success: function (data){
				$(".delfile").click();
				if(data.length>0){
					initerrorTable(data);
					$('#fileModal').modal('hide');
					$("h4").text("导入失败数据(失败原文可能为:用户名已存在)");
					$('#errorModal').modal('show');
				}
			},
			error: function (data){
				$(".delfile").click();
				if(data.responseText!="error"){
					$('#fileModal').modal('hide');
					$("#itemTable").dataTable().fnDraw(false);
				}
				if(data.responseText=="success"){
					window.parent.toastr[MES_SUCCESS]("导入成功！！");
				}else if(data.responseText=="error") {
					window.parent.toastr[MES_WARN]("模板有误请重新选择！！");
				}
			}
		});
	}else{
		window.parent.toastr[MES_ERROR]("请选择文件！！");
		return true;
	}
}








