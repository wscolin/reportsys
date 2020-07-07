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
			<div class="portlet-title">
				<div class="actions">
					<shiro:hasPermission name="st_user_import">
						<button class="btn btn-sm green-turquoise" id="btn_setresource" data-toggle="modal" onclick="btn_dwload_mb();"><i class="fa fa-download"></i> 导入模板下载</button>
						<button class="btn btn-sm btn-danger" id="btn_setresource" data-toggle="modal" onclick="btn_import_click();"><i class="fa fa-upload"></i> 导入</button>
						<button class="btn btn-sm btn-danger" id="btn_setresource" data-toggle="modal" onclick="btn_export_click();"><i class="fa fa-download"></i> 导出</button>
					</shiro:hasPermission>
				</div>
			</div>
			<div class="portlet-body flip-scroll" style="display: none">
				<div class="row">
					<div class="col-md-3">
						<ul id="deptquerytree" class="ztree" style="overflow: auto"></ul>
					</div>
					<div class="col-md-9">
						<table class="table table-striped table-bordered table-hover" id="itemTable">
							<thead>
								<tr>
									<th>序号</th>
									<th>用户名</th>
									<th>人员姓名</th>
									<th>部门名称</th>
									<th>身份证号码</th>
									<th>手机号</th>
									<th>EMAIL</th>
									<th>职位</th>
									<th>状态</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
   	</div> 
<!-- 弹框-->
<div id="itemModal" class="modal fade" role="dialog" tabindex="-1" data-backdrop="static">
	<!-- 	<div class="modal-dialog"> -->
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="itemForm" enctype="multipart/form-data">
					<div class="form-group">
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">用户名：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="USERID" class="form-control" placeholder="请输入用户名">
							</div>
						</div>
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">姓名：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="OPERATORNAME" class="form-control" placeholder="请输入姓名" onblur="trimval(this)">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">部门：</label>
							<div class="col-md-8 col-lg-8">
								<input name="DEPTNAME" class="form-control" readonly="readonly" onclick="showMenu();">
								<input name="DEPTID"  class="form-control" style="display: none">
								<div id="menuContent" class="menuContent inputtree">
									<ul id="depttree" class="ztree" style="margin-top:0; width:100%"></ul>
								</div>
							</div>
						</div>
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">职位：</label>
							<div class="col-md-8 col-lg-8">
								<select name="POSITION"  class="form-control">
									<c:forEach items="${ZW}" var="temp">
										<option value="${temp.ITEM_CODE}">${temp.ITEM_NAME}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">邮箱：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="EMAIL" class="form-control">
							</div>
						</div>
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">身份证号码：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="IDENTITY" class="form-control">
							</div>
						</div>
					</div>
					<input type="text" name="OPERATORID" hidden="hidden" />
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" onclick="btn_save_click();"><i class="fa fa-save"></i> 保存</button>
				<button class="btn btn-default" data-dismiss="modal" onclick="userclose();"><i class="fa fa-remove"></i> 取消</button>
			</div>
		</div>
	</div>
</div>

<div id="userroleModal" class="modal fade" tabindex="-1" data-backdrop="static" data-focus-on="input:first">
	<div class="modal-dialog modal-lg" class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">用户角色设置</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="userroleForm">
					<div class="form-group">
						<label class="control-label col-md-2 col-lg-2">用户角色：</label>
						<div class="col-md-9 col-lg-9">
							<select id="Role" name="Role"  class="form-control multi-select"  multiple="multiple">
							</select>
						</div>
					</div>
					<input type="text" name="operatorid" hidden="hidden" />
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" onclick="btn_saveuserrole_click();"><i class="fa fa-save"></i> 保存</button>
				<button class="btn btn-default" data-dismiss="modal"><i class="fa fa-remove"></i> 取消</button>
			</div>
		</div>
	</div>
</div>
<div id="userModal" class="modal fade" role="dialog" tabindex="-1" data-backdrop="static">
	<!-- 	<div class="modal-dialog"> -->
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="userForm">
					<div class="form-group">
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">生日：</label>
							<div class="col-md-8 col-lg-8">
								<input class="form-control form-control-inline date-picker" data-date-format="yyyy-mm-dd" type="text" name="BIRTHDATE" />
							</div>
						</div>
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">性别：</label>
							<div class="col-md-8 col-lg-8">
								<select name="GENDER"  class="form-control">
									<option value="1">男</option>
									<option value="2">女</option>
									<option value="0">未知</option>
								</select>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">手机号码：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="TELNO" class="form-control" >
							</div>
						</div>
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">电话号码：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="PHONENO" class="form-control" >
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">经侦民警编号：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="JZMJBH" class="form-control" >
							</div>
						</div>
						<div class="col-md-6 col-lg-6">
							<label class="control-label col-md-4 col-lg-4">经侦民警职位：</label>
							<div class="col-md-8 col-lg-8">
								<input type="text" name="JZMJZW" class="form-control" >
							</div>
						</div>
					</div>
					<input type="text" name="OPERATORID" hidden="hidden" />
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" onclick="btn_saveuserinfo_click();"><i class="fa fa-save"></i> 保存</button>
				<button class="btn btn-default" data-dismiss="modal" onclick="btn_close_click(this);"><i class="fa fa-remove"></i> 取消</button>
			</div>
		</div>
	</div>
</div>
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
						<div class="col-md-9">
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
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" onclick="btn_savefile_click();"><i class="fa fa-save"></i> 导入</button>
				<button class="btn btn-default" data-dismiss="modal" onclick="btn_close_click();"><i class="fa fa-remove"></i> 取消</button>
			</div>
		</div>
	</div>
</div>
<div id="errorModal" class="modal fade" tabindex="-1" role="dialog"  data-backdrop="static">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<div class="col-md-12">
						<table class="table table-striped table-bordered table-hover" id="errorTable">
							<thead>
								<tr>
									<th>序号</th>
									<th>机构编码</th>
									<th>机构名称</th>
									<th>管理员姓名</th>
									<th>用户名</th>
									<th>身份证号码</th>
									<th>电话号码</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" data-dismiss="modal"><i class="fa fa-remove"></i> 确定</button>
			</div>
		</div>
	</div>
</div>
</body>
<script src="${ctx}/plugins/assets/global/plugins/ztree/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/jquery-multi-select/js/jquery.quicksearch.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
<script src="${ctx}/plugins/assets/global/plugins/ajaxfileupload.js" type="text/javascript"></script>
<script src="${ctx}/javascript/sys/excel.js" type="text/javascript"></script>
               
</html>