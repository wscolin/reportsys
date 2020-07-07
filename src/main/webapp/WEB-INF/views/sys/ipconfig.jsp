<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/js.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
</head>
<body class="page-content">

	<div class="my-col-md">
		<div class="portlet light">
			<div class="portlet-title">
		       	<div class="actions">
		       		<shiro:hasPermission name="st_ipconfig_add">
						<button class="btn btn-sm btn-primary" id="btn_add" data-toggle="modal" onclick="btn_add_click();"><i class="fa fa-plus"></i> 增加</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="st_ipconfig_edit">
						<button class="btn btn-sm btn-info" id="btn_edit" data-toggle="modal" onclick="btn_edit_click();"><i class="fa fa-pencil-square-o"></i> 修改</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="st_ipconfig_set">
						<button class="btn btn-sm btn-success" id="btn_set" onclick="btn_set_click();"><i class="fa fa-cog"></i> 状态</button> 
		          	</shiro:hasPermission>
		       	</div>
       		</div>
       		<div class="portlet-body flip-scroll">		                              
		 		<table class="table table-striped table-bordered table-hover" id="itemTable">
					<thead>
				  		<tr>
							<th>序号</th>
		             		<th>IP</th>
 		             		<th>使用用户</th>
							<th>备注</th>
							<th>状态</th>
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
									IP地址：
								</label>
								<div class="col-md-10 col-lg-10 paddingRight0">
									<input type="text" name="IPADDRESS" class="form-control" placeholder="请输入IP地址">
								</div>
							</div>
				       	</div>		
				    	<div class="form-group">
							<div class="col-md-12 col-lg-12">
								<label class="control-label col-md-2 col-lg-2 align-label">
									使用用户：
								</label>
								<div class="col-md-10 col-lg-10 paddingRight0">
									<input type="text" name="USERNAME" class="form-control" placeholder="请输入使用用户">
								</div>
							</div>
				       	</div>					       	       	
					 	<div class="form-group">
							<div class="col-md-12 col-lg-12">
								<label class="control-label col-md-2 col-lg-2 align-label">
									备注：
								</label>
								<div class="col-md-10 col-lg-10 paddingRight0">
									<textarea name="REMARKS" class="form-control" rows="10" ></textarea>
								</div>
							</div>
					   	</div>
						<input type="text" name="ID" hidden="hidden" />
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
<script src="${ctx}/javascript/sys/ipconfig.js" type="text/javascript"></script>
               
</html>