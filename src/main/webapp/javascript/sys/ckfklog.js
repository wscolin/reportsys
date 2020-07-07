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
		bSort : true,
		searching : false,
		pagingType : "full_numbers",
		lengthChange : true,
		deferRender: true,
		bDestroy:true,
		lengthMenu : [ 10, 25, 50 ],
		order: [[4,"desc"]],
		ajax : {
			url:ctx+"/log/getfks",
			type : "POST",
			data: {
				FKS:FKS
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
				data : "FK_FILE_PATH",
			}, {
				data : "FK_STATE",
			}, {
				data : "FK_FLAG"
			}, {
				data : "FK_CREATE_TIME",
			}, {
				data : "FK_UPDATE_TIME",
			}
		],
		aoColumnDefs : [
			{
				targets : 2,
				render : function(data, type, full) {
					if (data == "1") {
						return "<span class='label label-sm label-primary'>已提取</span>";
					} else {
						return "<span class='label label-sm label-warning'>未提取</span>";
					}
				}
			}, {
				targets : 3,
				render : function(data, type, full) {
					if (data == "0") {
						return "<span class='label label-sm label-primary'>正常文件</span>";
					} else {
						return "<span class='label label-sm label-danger'>过期文件</span>";
					}
				}
			}, {
				targets : 4,
				render : function(data, type, full) {
					return formatDateTime(data);
				}
			}, {
				targets : 5,
				render : function(data, type, full) {
					if(full.STATE==0){
						return "";
					}else{
						return formatDateTime(data);
					}
				}
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