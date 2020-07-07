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
		order: [[4,"desc"]],
		ajax : {
			url:ctx+"/log/cklist",
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
				data : "QQDH",
			}, {
				data : "STATE",
			}, {
				data : "FLAG"
			}, {
				data : "CREATE_TIME",
			}, {
				data : "UPDATE_TIME",
			}, {
				data : "FK",
			}
		],
		aoColumnDefs : [
			{
				targets : 2,
				render : function(data, type, full) {
					if (data == "1") {
						var temp ="<span class='label label-sm label-primary'>已提取</span>   ";
						if(!full.FK && DateUtil.comptime(formatDateTime(full.UPDATE_TIME),danger_date)){
							temp+="<span class='label label-sm label-primary' style='cursor: pointer' onclick='cfqqdh(\""+full.QQDH+"\")'>重发</span>";
						}
						return temp;
					} else {
						if(DateUtil.comptime(formatDateTime(full.CREATE_TIME),danger_date)){
							return "<span class='label label-sm label-danger'>超时未提取</span>";
						}else{
							return "<span class='label label-sm label-warning'>未提取</span>";
						}
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
			}, {
				targets : 6,
				render : function(data, type, full) {
					if(data){
						return "<div class='look'><span class='label label-sm label-primary' style='cursor: pointer'>反馈详情</span></div>";
					}else{
						if(full.STATE=="1"){
							if(DateUtil.comptime(formatDateTime(full.UPDATE_TIME),danger_date)){
								return "<span class='label label-sm label-danger'>超时未反馈</span>";
							}else{
								return "<span class='label label-sm label-warning'>未反馈</span>";
							}
						}else{
							return "";
						}
					}
				}
			}
		],
		language : {
			url:ctx+"/plugins/lang-zh_CN.json"
		},initComplete:function () {
			tableHeight(60+hideheight);
		}
	});
	//查看通告
	table.on('click', '.look', function (e) {
		var full = $("#itemTable").DataTable().row($(this).parents("tr")).data();
		var item = {};
		item.frameHeight = $(document).height();
		item.id = "FK_"+full.QQDH;
		item.url = ctx + "/log/fkpage?FKS="+full.FK;
		item.title = "请求单"+full.QQDH+"：反馈详情";
		item.close =true;
		parent._gas_analysis.addTabs(item);//添加tab（默认添加的一行放不下会放两行）
	})
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
function cfqqdh(qqdh) {
	$.ajax({
		url:ctx+"/log/ckcfqq",
		cache : false,
		type : "POST",
		data : {
			qqdh:qqdh
		},
		success : function(result) {
			$("#itemTable").dataTable().fnDraw(false);
		}
	});
}