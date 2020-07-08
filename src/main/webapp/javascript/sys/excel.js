$(function () {
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
    var today = new Date();
    var year = today.getFullYear();
    var moth = today.getMonth()+1;
    $("#kssjDiv").datepicker('setDate',year+"-"+moth);
    $("#kssjDiv_export").datepicker('setDate',year+"-"+moth);
});
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
    $.ajax({
        url:ctx+ "/excel/excelExportByTemplate?date="+date,
        type : "POST",
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
        }
    });
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








