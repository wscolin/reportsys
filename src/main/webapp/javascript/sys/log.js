$(document).ready(function() {
	// 初始化日期选择控件
	if (jQuery().datepicker) {
		$('.date-picker').datepicker({
			rtl: App.isRTL(),
			orientation: "left",
			autoclose: true
		});
	}
	initTable();
});
function cleansearch() {
	$('#searchForm')[0].reset();
	initTable();
}

//表格初始化
function initTable() {
	var table = $('#itemTable');
	var oTable = table.dataTable({
		dom:"<'row'<'search text-left'f>r>t<'row pageRow'<'col-sm-3 col-md-3 col-lg-3 data_len'l><'col-sm-3 col-md-3 col-lg-3'i><'col-sm-6 col-md-6 col-lg-6'p>>",
		scrollY:true,
		processing : true,
		serverSide : true,
		bSort : true,
		searching : false,
		pagingType : "full_numbers",
		lengthChange : true,
		deferRender: true,
		bDestroy:true,
		lengthMenu : [ 10, 25, 50 ],
		order: [[7,"desc"]],
		ajax : {
			url:ctx+"/log/list",
			type : "POST",
			data: {
				userid:$("#searchForm input[name=userid]").val(),
				APP:$("#searchForm select[name=APP]").val(),
				search_begin: $("#searchForm input[name=search_begin]").val(),
				search_end: $("#searchForm input[name=search_end]").val(),
			},
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
				data : "USERID",
			}, {
				data : "USERNAME",
			}, {
				data : "DEPTNAME"
			}, {
				data : "CLASS_NAME",
			}, {
				data : "REMARKS",
			}, {
				data : "IP_ADDRESS",
			}, {
				data : "CREATETIME",
				sWidth:'150px',
			}
		],
		aoColumnDefs : [
			{
				targets : 7,
				render : function(data, type, full) {
					return formatDateTime(data);
				}
			}
		],
		language : {
			url:ctx+"/plugins/lang-zh_CN.json"
		},initComplete:function () {
			tableHeight(60+hideheight);
		}
	});

}
var hideheight=0;
function logtableHeight() {
	hideheight=hideheight==0?80:0;
	tableHeight(60+hideheight);
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