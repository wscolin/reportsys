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
})

var provice_data = {
    btn_import_click:function () {
        $(".btn-primary").attr("disabled",false);
        $("#KSRQ").val("");
        $("h4").text("导入省级数据");
        $('#fileModal').modal('show');
    },
    btn_savefile_click:function (_this) {
        $(_this).attr("disabled","disabled");
        var date = $("#KSRQ").val();
        if(date==null||date==""||date=="undefined"){
            window.parent.toastr[MES_ERROR]("日期不能空！！");
            $(_this).attr("disabled",false);
            return ;
        }
        if($("#fileUpload").val()){
            var formData=new FormData($("#fileForm")[0]);
            $.ajax({
                url:ctx+ "/excel/import_prodataBypoi?date="+date,
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
                        inityearTable();
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
}

