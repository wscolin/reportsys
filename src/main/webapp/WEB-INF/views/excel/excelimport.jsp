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
		width: 75%;
	}
</style>
<body class="page-content">
	<div class="my-col-md">
		<div class="portlet light">
			<div class="portlet-title" >
				<%--<form class="form-inline">--%>
					<div class="form-group" style="float:left">
						<label class="control-label col-md-1col-lg-1 col-sm-1 " for="querydate">查询日期</label>
                        <div id="querydateDiv" class="col-md-2 col-lg-2 col-sm-2 input-group date dateDiv  pull-left paddingRight0">
                            <input class="form-control borderRadiusRight0 borderRight0"  type="text" id="querydate" name="querydate" onchange="queryDate(this);">
                            <span class="input-group-btn">
								<button class="btn default date-set" type="button">
									<i class="fa fa-calendar"></i>
								</button>
							</span>
                        </div>
						<div class="col-md-4 col-lg-4 col-sm-4">
							<shiro:hasPermission name="st_user_import">
								<%--<button class="btn btn-sm green-turquoise" id="btn_setresource" data-toggle="modal" onclick="btn_dwload_mb();"><i class="fa fa-download"></i>收入-支出表导入模板下载</button>--%>
								<button class="btn btn-sm btn-default " id="btn_setresource" data-toggle="modal" onclick="btn_import_click();"><i class="fa fa-upload"></i> 导入</button>
								<button class="btn btn-sm btn-default" id="btn_setresource" data-toggle="modal" onclick="btn_export_click();"><i class="fa fa-download"></i> 导出</button>
							</shiro:hasPermission>
						</div>
					</div>

				<%--</form>--%>
			</div>
			<div class="portlet-body flip-scroll">
				<ul class="nav nav-tabs">
					<li role="presentation" class="active"><a href="#" data-toggle="tab" index="0">收入表</a></li>
					<li role="presentation"><a href="#" data-toggle="tab" index="1">支出表</a></li>
				</ul>
				<table class="table table-striped table-bordered table-hover" id="itemTable">
					<thead>
						<tr>
							<th>序号</th>
							<th>年份</th>
							<th>科目编码</th>
							<th>科目名称</th>
							<th>金额</th>
							<th>市值</th>
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
<!-- 弹框-->
<div id="fileModal" class="modal fade" role="dialog" tabindex="-1" data-backdrop="static">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="fileForm" enctype="multipart/form-data">
					<div class="form-group">
						<label class="control-label col-md-3">导入excel:</label>
						<div class="col-md-9" style="padding-left: 1px">
							<div class="fileinput fileinput-new" data-provides="fileinput">
								<div class="input-group input-large">
									<div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput" style="border-radius: 5px 0 0 5px!important;">
										<i class="fa fa-file fileinput-exists"></i>&nbsp;
										<span class="fileinput-filename"> </span>
									</div>
									<span class="input-group-addon btn default btn-file" style="border-radius: 0 5px 5px 0!important;">
										<span class="fileinput-new">选择文件</span>
										<span class="fileinput-exists">重新选择</span>
										<input type="file" id="fileUpload" name="file" multiple="multiple">
									</span>
									<a href="javascript:;" class="input-group-addon btn red fileinput-exists delfile" data-dismiss="fileinput" style="border-radius: 0 5px 5px 0!important;">删除</a>
								</div>
							</div>
						</div>
						<label class="control-label col-md-3">导入日期:</label>
						<div id="kssjDiv" class="col-md-5 col-lg-5 col-sm-5 input-group date dateDiv  pull-left paddingRight0">
							<input class="form-control borderRadiusRight0 borderRight0"  type="text" id="KSRQ" name="KSRQ">
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
				<button class="btn btn-primary" onclick="btn_savefile_click(this);"><i class="fa fa-save"></i> 导入</button>
				<button class="btn btn-default" data-dismiss="modal" onclick="btn_close_click();"><i class="fa fa-remove"></i> 取消</button>
			</div>
		</div>
	</div>
</div>
	<!-- 弹框-->
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
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/ztree/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/jquery-multi-select/js/jquery.quicksearch.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>

<script src="${ctx}/plugins/assets/global/plugins/ajaxfileupload.js" type="text/javascript"></script>
<script src="${ctx}/javascript/sys/excel.js" type="text/javascript"></script>
               
</html>