$(document).ready(function() {
	initTable();
});

//表格初始化
function initTable() {
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
			url:ctx+"/params/list",
			type : "POST",
			error: AjaxError
		},
		select : {
			style : 'single'
		},
		columns : [
			{
				data:null,
				sWidth:'30px',
				sClass:"text-center",
				fnCreatedCell:function (nTd,sData,oData,iRow,iCol) {
					var startnum=this.api().page()*(this.api().page.info().length);
					$(nTd).html(iRow+1+startnum);
				}
			},{
				data : "PARM_KEY"
			}, {
				data : "PARM_VALUE"
			}, {
				data : "REMARKS"
			}
		],
		aoColumnDefs : [
		{		

		}
		],		
		language : {
			url:ctx+"/plugins/lang-zh_CN.json"
		},initComplete:function () {
			tableHeight();
		}
	});
  
}

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


//新增
function btn_add_click() {
	$("#itemForm input[name=PARM_KEY]").removeAttr("readonly","readonly");
	$("#itemForm input[name=ID]").val("");
	$("h4").text("新增");
	$('#itemModal').modal('show');
    $('#itemModal').on('shown.bs.modal', function() {
    	$('input[name=PARM_KEY]').focus();
    });	  
}

//修改
function btn_edit_click() {
	$("#itemForm input[name=PARM_KEY]").attr("readonly","readonly");
	var row = $('#itemTable').DataTable().rows('.selected').data();
	if (row.length == 0) {
		window.parent.toastr[MES_WARN]("请选中需要修改的记录！");
		return false;
	}
	$("#itemForm input[name=ID]").val("edit");
	$("#itemForm input[name=PARM_KEY]").val(row[0].PARM_KEY);
	$("#itemForm input[name=PARM_VALUE]").val(row[0].PARM_VALUE);
	$("#itemForm textarea[name=REMARKS]").val(row[0].REMARKS);

	$("h4").text("修改");
	$('#itemModal').modal('show');
    $('#itemModal').on('shown.bs.modal', function() {
        $('input[name=PARM_KEY]').focus();
    });
}
// 保存按钮单击事件
function btn_save_click() {
	function sava() {
		$.ajax({
			url:ctx+"/"+ url,
			type : "POST",
			data : form.serialize(),
			success : function(result) {
				if (result == "success") {
					$("#itemModal").modal("hide");
					window.parent.toastr[MES_SUCCESS]("保存成功");
					$("#itemTable").dataTable().fnDraw(false);
				} else {
					window.parent.toastr[MES_ERROR]("保存失败");
				}
			},
			error : function() {
				window.parent.toastr[MES_ERROR]("数据保存失败！");
			}
		});
	}
	var url = "params/add";
	var form = $("#itemForm");
	if (form.valid()) {
		if($("#itemForm input[name=ID]").val()!= ""){
			url = "params/edit";
			sava();
		}else{
			$.ajax({
				url:ctx+"/params/isone",
				type : "POST",
				data : "key="+$("#itemForm input[name=PARM_KEY]").val(),
				success : function(result) {
					if (result == "success") {
						sava();
					}else{
						$("#itemForm input[name=PARM_KEY]").val(null);
						window.parent.toastr[MES_WARN]("参数编码已存在！");
					}
				}
			});
		}
	}
}
//验证
$("#itemForm").validate({
	rules : {
		PARM_KEY : {
			required : true,
			maxlength : 50
		}/*,
		PARM_VALUE : {
			required : true,
			maxlength : 100
		},
		REMARKS:{
			maxlength : 100
		}*/
	},
	messages : {
		PARM_KEY : {
			required : "请输入参数编码.",
			maxlength : "内容过长"
		}/*,
		PARM_VALUE : {
			required : "请输入参数值.",
			maxlength : "内容过长"
		},
		REMARKS:{
			maxlength : "内容过长"
		}*/
	},
	highlight : function(element) {
		$(element).closest('div').addClass('has-error');
	},
	success : function(label) {
		$(label).closest('div').removeClass('has-error');
		$(label).remove();
	}
});