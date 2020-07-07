<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/js.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
</head>
<link href="${ctx}/plugins/assets/global/plugins/ztree/metroStyle/metroStyle.css" rel="stylesheet" type="text/css"/>
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
</style>
<body class="page-content">
<ul id="typeIdIdtree" class="ztree"></ul>
<!-- 弹窗-->
<div id="itemModal" class="modal fade" role="dialog" tabindex="-1" data-backdrop="static">
	<!-- 	<div class="modal-dialog"> -->
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="itemForm">
					<div class="form-group">
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">部门ID：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="DEPTID" class="form-control" placeholder="请输入部门ID">
							</div>
						</div>
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">部门名称：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="NAME" class="form-control" placeholder="请输入部门名称">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">描述：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="DESCN" class="form-control">
							</div>
						</div>
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">上级：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" readonly="readonly" id="PARENTID" class="form-control">
								<input type="text" hidden="hidden" name="PARENTID">
							</div>
						</div>
					</div>
					<input type="text" name="id" hidden="hidden" />
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" onclick="btn_save_click();"><i class="fa fa-save"></i> 保存</button>
				<button class="btn btn-default" data-dismiss="modal" onclick="btn_close_click();" id="btn_close"><i class="fa fa-remove"></i> 取消</button>
			</div>
		</div>
	</div>
</div>
</body>
<script src="${ctx}/plugins/assets/global/plugins/ztree/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/javascript/sys/dept.js" type="text/javascript"></script>

</html>