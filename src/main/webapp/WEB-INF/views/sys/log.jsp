<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/js.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
</head>
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
											用户名：
										</label>
										<div class="col-md-8 col-lg-8 paddingRight0">
											<input type="text" class="form-control" name="userid">
										</div>
									</div>
									<div class="col-md-8 col-lg-8">
										<label class="control-label col-md-2 col-lg-2 align-label">
											操作时间：
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
						<th>用户名</th>
						<th>姓名</th>
						<th>部门名称</th>
						<th>类名</th>
						<th>操作说明</th>
						<th>IP地址</th>
						<th>操作时间</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
<script src="${ctx}/javascript/sys/log.js" type="text/javascript"></script>

</html>