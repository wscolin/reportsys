<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/js.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
</head>
<link href="${ctx}/plugins/assets/global/plugins/ztree/metroStyle/metroStyle.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/plugins/assets/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/theme.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css"/>
<%--<script th:src="@{js/jquery-3.3.1.min.js}"></script>--%>
<%--
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/js/jquery-3.3.1.js" type="text/javascript"></script>
--%>
<%--<link rel="stylesheet" th:href="@{css/bootstrap.min.css}">--%>
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/css/bootstrap.min.css" rel="stylesheet" >

<%--<script th:src="@{js/bootstrap.min.js}"></script>--%>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/js/bootstrap.min.js" type="text/javascript"></script>

<%--<link rel="stylesheet" th:href="@{css/fileinput.css}">--%>
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/css/fileinput.css" rel="stylesheet" >

<%--<script th:src="@{js/fileinput.js}"></script>--%>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>


<%--<script th:src="@{js/theme.js}"></script>--%>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/theme.js" type="text/javascript"></script>

<%--<script th:src="@{js/zh.js}"></script>--%>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/zh.js" type="text/javascript"></script>

<style>
	.ztree * {
		font-size: 14px;
		margin: 0;
		padding: 0;
	}
	.inputtree{
		display:none;
		position: absolute;
		background-color: white;
		z-index: 9999;
		max-height: 300px;
		overflow: auto;
	}
	input.search-input{
		box-sizing: border-box;
		-moz-box-sizing:border-box;
		width: 100%;
		margin-bottom: 5px;
		height: auto;
	}
	.pageRow{
		width: 80%;
	}
	.datess{text-align: center;cursor:pointer}
	.file-preview {
		border-radius: 5px;
		border: 1px solid #ddd;
		padding: 8px;
		width: 100%;
		margin-bottom: 5px;
		height: 300px;
		overflow-y: auto;
	}
</style>
<body class="page-content">
	<div class="my-col-md">
		<div class="portlet light">
			<div class="portlet-title" >
				<%--<form class="form-inline">--%>
					<div class="form-group" style="float:left">
						<div class="col-md-8 col-lg-8 col-sm-8" style="display: none">
							<label class="control-label col-md-1col-lg-1 col-sm-1 " for="querydate">查询日期</label>
							<div id="querydateDiv" class="col-md-2 col-lg-2 col-sm-2 input-group date dateDiv  pull-left paddingRight0">
								<input class="form-control borderRadiusRight0 borderRight0"  type="text" id="querydate" name="querydate" onchange="queryDate(this);">
								<span class="input-group-btn">
									<button class="btn default date-set" type="button">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-md-12 col-lg-12col-sm-12">
							<shiro:hasPermission name="st_user_import">
								<%--<button class="btn btn-sm green-turquoise" id="btn_setresource" data-toggle="modal" onclick="btn_dwload_mb();"><i class="fa fa-download"></i>收入-支出表导入模板下载</button>--%>
								<button class="btn btn-sm btn-default" style="display: none" id="btn_setresource" data-toggle="modal" onclick="btn_import_click();"><i class="fa fa-upload" ></i> 导入</button>

							</shiro:hasPermission>
							<shiro:hasPermission name="excel_import">
								<button class="btn btn-sm btn-default" id="btn_setresource" data-toggle="modal" onclick="btn_import_click_13();" data-target ="modalImport_base"><i class="fa fa-upload"></i> 导入基础数据</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="excel_import">
								<button class="btn btn-sm btn-default" id="btn_setresource" data-toggle="modal" onclick="tx_data.btn_import_click();" data-target ="modalImport_txdata"><i class="fa fa-upload"></i> 导入填写数据</button>
								<button class="btn btn-sm btn-default" style="" id="btn_setresource" data-toggle="modal" onclick="btn_export_click();"><i class="fa fa-download"></i> 导出</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="cleardata">
								<button class="btn btn-sm btn-default" id="btn_setresource" data-toggle="modal" onclick="clear_date_click();"><i class="fa fa-trash-o"></i> 清除数据</button>
							</shiro:hasPermission>

						</div>
					</div>

				<%--</form>--%>
			</div>
			<div class="portlet-body flip-scroll">
				<div class="row">
					<div class="col-md-2 col-lg-2 col-sm-2" id="yeardiv">
						<ul class="nav nav-tabs">
							<li  class="active"><a href="javascript:void(0)" index="0">已导入</a></li>
						</ul>
						<table class="table table-striped table-bordered table-hover" id="yearTable">
							<thead>
							<tr>
								<th>序号</th>
								<th>年份</th>
							</tr>
							</thead>
						</table>
				    </div>
					<div class="col-md-10 col-lg-10 col-sm-10" id="itemdiv">
						<ul class="nav nav-tabs">
							<li role="presentation" class="active"><a href="#" data-toggle="tab" index="0">收入表</a></li>
							<li role="presentation"><a href="#" data-toggle="tab" index="1">支出表</a></li>
						</ul>
						<table class="table table-striped table-bordered table-hover" id="itemTable">
							<thead>
							<tr>
								<th>序号</th>
								<%--<th>年份</th>--%>
								<th>科目编码</th>
								<th>科目名称</th>
								<%--<th>金额</th>--%>
								<th>市本级</th>
								<th>高薪区</th>
								<th>临川区</th>
								<th>东乡区</th>
								<th>南城县</th>
								<th>南丰县</th>
								<th>黎川县</th>
								<th>崇仁县</th>
								<th>宜黄县</th>
								<th>乐安县</th>
								<th>金溪县</th>
								<th>资溪县</th>
								<th>广昌县</th>
							</tr>
							</thead>
						</table>
					</div>
				</div>

			</div>
		</div>
   	</div>
	<div class="modal fade" id="modalImport_base" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel_base" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel_base">导入基础数据</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form enctype="multipart/form-data">
						<div class="form-group center-block" style="width: 800px;" >
							<input id="modelInput_base" type="file" multiple class="file" data-overwrite-initial="false" data-min-file-count="1"
								   data-max-file-count="13" name="file" accept="/*" >
						</div>
					</form>
					<div id="kartik-file-errors_base"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<!--手动导入-->
	<div class="modal fade" id="modalImport_txdata" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel_txdata" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">导入填写数据</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form enctype="multipart/form-data">
						<div class="form-group center-block" style="width: 800px;" >
							<input id="modelInput_txdata" type="file" multiple class="file" data-overwrite-initial="false" data-min-file-count="1"
								   data-max-file-count="1" name="file" accept="/*" >
						</div>
					</form>
					<div id="kartik-file-errors"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 导出模态框-->
	<div id="fileModal_exprot" class="modal fade" role="dialog" tabindex="-1" data-backdrop="static">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="fileForm_exprot" enctype="multipart/form-data">
						<div class="form-group">
							<label class="control-label col-md-3">导出日期:</label>
							<div id="kssjDiv_export" class="col-md-5 col-lg-5 col-sm-5 input-group date dateDiv  pull-left paddingRight0">
								<input class="form-control borderRadiusRight0 borderRight0"  type="text" id="KSRQ_exprot" >
								<span class="input-group-btn">
								<button class="btn default date-set" type="button">
									<i class="fa fa-calendar"></i>
								</button>
							</span>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" onclick="btn_export_confrim();"><i class="fa fa-save"></i> 确定</button>
					<button class="btn btn-default" data-dismiss="modal" onclick="btn_close_click();"><i class="fa fa-remove"></i> 取消</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="${ctx}/plugins/assets/global/plugins/moment.min.js"></script>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<%--<script src="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/fileinput.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/theme.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/zh.js" type="text/javascript"></script>--%>
<script src="${ctx}/javascript/sys/initFileInput.js" type="text/javascript"></script>

<script src="${ctx}/plugins/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/ztree/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/jquery-multi-select/js/jquery.quicksearch.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/ajaxfileupload.js" type="text/javascript"></script>
<script src="${ctx}/javascript/sys/excel_13.js" type="text/javascript"></script>
<script>
    initFileInput("modelInput_base","${ctx}excel_13/importfileBypoi_Base");
    initFileInput("modelInput_txdata","${ctx}excel_13/importfileBypoi_txdata");
</script>
</html>