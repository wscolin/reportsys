<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/js.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
</head>
<%--<style>
	.auto{
		min-width: 200px;
		min-height: 50px;
		overflow-y: scroll;
		overflow-x: scroll;
	}
</style>--%>
<body class="page-content">

	<div class="my-col-md">
		<div class="portlet light">
			<div class="portlet-title">
		       	<div class="actions">
		       		<shiro:hasPermission name="st_params_add">
						<button class="btn btn-sm btn-primary" id="btn_add" data-toggle="modal" onclick="btn_add_click();"><i class="fa fa-plus"></i> 增加</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="st_params_edit">
						<button class="btn btn-sm btn-info" id="btn_edit" data-toggle="modal" onclick="btn_edit_click();"><i class="fa fa-pencil-square-o"></i> 修改</button>
					</shiro:hasPermission>
					<button class="btn btn-sm btn-success" data-toggle="modal" onclick="cleanredis('PARAM');"><i class="fa fa-refresh"></i>重置参数</button>
		       	</div>
       		</div>
       		<div class="portlet-body flip-scroll">		                              
		 		<table class="table table-striped table-bordered table-hover" id="itemTable">
					<thead>
				  		<tr>
							<th>序号</th>
		             		<th>参数编码</th>
 		             		<th>参数值</th>
							<th>参数说明</th>
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
		          	<h4 class="modal-title"></h4>
		   		</div>
			 	<div class="modal-body">
			   		<form class="form-horizontal" role="form" id="itemForm">
				    	<div class="form-group">
							<div class="col-md-12 col-lg-12">
								<label class="control-label col-md-2 col-lg-2 align-label">
									参数编码：
								</label>
								<div class="col-md-10 col-lg-10 paddingRight0">
									<input type="text" name="PARM_KEY" class="form-control" placeholder="请输入参数编码">
								</div>
							</div>
				       	</div>		
				    	<div class="form-group">
							<div class="col-md-12 col-lg-12">
								<label class="control-label col-md-2 col-lg-2 align-label">
									参数说明：
								</label>
								<div class="col-md-10 col-lg-10 paddingRight0">
									<input type="text" name="REMARKS" class="form-control" placeholder="请输入参数值">
								</div>
							</div>
				       	</div>					       	       	
					 	<div class="form-group">
							<div class="col-md-12 col-lg-12">
								<label class="control-label col-md-2 col-lg-2 align-label">
									参数值：
								</label>
								<div class="col-md-10 col-lg-10 paddingRight0">
									<textarea name="PARM_VALUE" class="form-control" rows="10" ></textarea>
								</div>
							</div>
					   	</div>
						<input type="text" name="ID" hidden="hidden"/>
			    	</form>
			 	</div>
				<div class="modal-footer">
					<button class="btn btn-primary" onclick="btn_save_click();"><i class="fa fa-save"></i> 保存</button>
			       	<button class="btn btn-default" data-dismiss="modal" onclick="btn_close_click();"><i class="fa fa-remove"></i> 取消</button>
			  	</div>
			</div>
		</div>
	</div>

</body>
<script src="${ctx}/javascript/sys/params.js" type="text/javascript"></script>
               
</html>