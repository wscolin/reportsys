<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/js.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
</head>
<script type="application/javascript">
	var danger_date = DateUtil.getIntervalminu("yyyy-MM-dd HH:mm:ss",${danger_date})
</script>
<body class="page-content">
	<div class="my-col-md">
		<div class="portlet light bordered">
			<div class="panel-group accordion" id="accordion1">
				<div class="panel panel-default">
					<div class="panel-heading" onclick="logtableHeight()">
						<h4 class="panel-title">
							<a class="accordion-toggle accordion-toggle-styled collapsed" data-toggle="collapse" data-parent="#accordion1" href="#collapse_1" style="font-family: Microsoft YaHei"> 查询条件 </a>
						</h4>
					</div>
					<div id="collapse_1" class="panel-collapse collapse">
						<div class="panel-body">
							<form id="searchForm" class="form-horizontal" role="form">
								<div class="form-group">
									<div class="col-md-2 col-lg-2">
										<label class="control-label col-md-4 col-lg-4 align-label">
											请求单号：
										</label>
										<div class="col-md-8 col-lg-8 paddingRight0">
											<input type="text" class="form-control" name="userid">
										</div>
									</div>
									<div class="col-md-8 col-lg-8">
										<label class="control-label col-md-2 col-lg-2 align-label">
											请求时间：
										</label>
										<div class="col-md-10 col-lg-10 paddingLeft0 paddingRight0">
											<div class="input-group date-picker input-daterange paddingRight0" data-date-format="yyyy-mm-dd">
												<input type="text" class="form-control" name="search_begin" readonly>
												<span class="input-group-addon">至</span>
												<input type="text" class="form-control" name="search_end" readonly>
											</div>
										</div>
									</div>
									<div class="col-md-2 col-lg-2">
										<a class="btn btn-sm btn-primary"onclick="initTable();"><i class="fa fa-search"></i> 查询</a>
										<a class="btn btn-sm btn-danger"onclick="cleansearch();"><i class="fa fa-trash-o"></i> 清空</a>
									</div>
								</div>

							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="portlet-body flip-scroll">
				<table class="table table-striped table-bordered table-hover" id="itemTable">
					<thead>
					<tr>
						<th>序号</th>
						<th>请求单号</th>
						<th>文件状态</th>
						<th>过期状态</th>
						<th>创建时间</th>
						<th>提取时间</th>
						<th>反馈详情</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<div id="itemModal" class="modal fade" role="dialog" tabindex="-1" data-backdrop="static">
		<!-- 	<div class="modal-dialog"> -->
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 class="modal-title">反馈详情</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<div class="col-md-12 col-lg-12">
							<label class="control-label col-md-2 col-lg-2 align-label">
								请求单号：
							</label>
							<div class="col-md-10 col-lg-10 paddingRight0">
								<input type="text" name="QQDH" class="form-control" readonly>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-12 col-lg-12">
							<label class="control-label col-md-2 col-lg-2 align-label">
								反馈文件地址：
							</label>
							<div class="col-md-10 col-lg-10 paddingRight0">
								<input type="text" name="FK_FILE_PATH" class="form-control" readonly>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">反馈文件提取状态：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="FK_STATE" class="form-control" readonly>
							</div>
						</div>
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">反馈文件过期状态：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text"  name="FK_FLAG" class="form-control" readonly>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">反馈文件创建时间：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="FK_CREATE_TIME" class="form-control" readonly>
							</div>
						</div>
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">反馈文件提取时间：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text"  name="FK_UPDATE_TIME" class="form-control" readonly>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="${ctx}/javascript/sys/cklog.js" type="text/javascript"></script>

</html>