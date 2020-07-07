<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/js.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
</head>
<script type="application/javascript">
	var FKS = "${FKS}";
</script>
<body class="page-content">
	<div class="my-col-md">
		<div class="portlet light bordered">
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
					</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
<script src="${ctx}/javascript/sys/ckfklog.js" type="text/javascript"></script>

</html>