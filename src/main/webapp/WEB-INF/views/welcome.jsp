<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>报表管理系统</title>
	<%@ include file="js.jsp"%>
</head>
<style>
	#titletext{
		margin-top: 10px;
	}
	.nav-tabs>li {
		border-right: 1px solid #ddd;
		border-left: 1px solid #ddd;
	}
	.close:hover{
		background-image: url("${ctx}/images/guanbi2.png");
	}
	.nav-tabs>li>a{
		margin-left: 2px;
		margin-right: 2px;
	}
	.nav>li>a {
		padding: 8px 15px;
	}
	.page-content-wrapper .page-content {
		padding:0px!important;
	}
	.tabbable{
		border: 2px solid #ededed;
	}
	.tabbable >ul{
		background-color: #ededed;
	}
</style>
<body>
<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
	<div class="page-sidebar-wrapper">
		<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->
			<ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200" style="font-family: 微软雅黑">
				<li class="sidebar-toggler-wrapper">
					<div class="page-logo">
						<div>
							<div onclick="$('#titletext').toggle()" class="menu-toggler sidebar-toggler" style="margin: 8px;"/>
						</div>
					</div>
				</li>
				<c:forEach items="${menus}" var="menu" varStatus="status">
					<c:if test="${menu.VALUES!=''}">
						<c:if test="${status.first}">
							<li class="nav-item active open">
						</c:if>
						<c:if test="${!status.first}">
							<li class="nav-item">
						</c:if>
						<a href="javascript:;" class="nav-link nav-toggle">
								${menu.ICONCLS}<span class="title">${menu.NAME}</span>
							<span class="arrow"></span>
							<span class="selected"></span>
						</a>
						<ul class="sub-menu">
							<c:forEach items="${menu.VALUES}" var="VALUE">
								<li class="nav-item"><a class="ajaxify" ecicon="${VALUE.ICONCLS}" ecaction="${VALUE.ACTION}" ecname="${VALUE.NAME}" onclick="menuclick(this);">${VALUE.ICONCLS}&nbsp;${VALUE.NAME}</a></li>
							</c:forEach>
						</ul>
						</li>
					</c:if>
				</c:forEach>
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
	</div>
	<!-- END SIDEBAR -->

	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<div class="tabbable"><!-- tabbable-custom 有外边距 谷歌会出滚动条 -->
				<ul class="nav nav-tabs">

				</ul>
				<div class="tab-content">
				</div>
			</div>
		</div>
	</div>
	<!-- END CONTENT -->
</div>
<!-- END FOOTER -->
<script type="application/javascript">
	$(function () {
		$(".nav-tabs").tabdrop();
		menuclick();
		//fk();
	});
	function  menuclick(obj) {
		var item = {};
		item.frameHeight = $(document).height() - 70;
		item.id = "home";
		item.url = ctx + "/home";
		item.title = "首页";
		item.close =false;
		if(obj){
			var url = $(obj).attr("ecaction"); var icon = $(obj).attr("ecicon"); var title = $(obj).attr("ecname");
			item.id = url.replace("/");
			item.url = ctx + url;
			item.title = icon+title;
			item.close =true;
		}
	//	alert(item.url);
		_gas_analysis.addTabs(item);//添加tab（默认添加的一行放不下会放两行）
		$(window).resize();//手动触发窗体resize时间  触发tabdrop
	}
	function fk() {
		var xml="<QUERY_XML><ITEMS><ITEM>http://localhost:14080/gas_bank_web/files/430000E002H1011101010014300028620170707145254.zip</ITEM><ITEM>http://localhost:14080/gas_bank_web/files/430000E002H1011101010014300028620170707150048.zip</ITEM><ITEM>http://localhost:14080/gas_bank_web/files/430000E002H1011101010014300028620170707150824.zip</ITEM><ITEM>http://localhost:14080/gas_bank_web/files/430000E002H1011101010014300028620170707153636.zip</ITEM><ITEM>http://localhost:14080/gas_bank_web/files/430000E002H1011101010014300028620170707153816.zip</ITEM><ITEM>http://localhost:14080/gas_bank_web/files/430000E002H1011101010014300028620170707163205.zip</ITEM><ITEM>http://localhost:14080/gas_bank_web/files/430000E002H1011101010014300028620170707165230.zip</ITEM><ITEM>http://localhost:14080/gas_bank_web/files/430000E002H1011101010014300028620170707165412.zip</ITEM><ITEM>http://localhost:14080/gas_bank_web/files/430000E002H1011101010014300028620170707165615.zip</ITEM></ITEMS></QUERY_XML>";
		$.ajax({
			url:ctx+"/feedback_ckext",
			cache : false,
			type : "POST",
			data : {
				YHJGDM : "E002H101110101001",
				TOKEN:"RTAwMkgxMDExMTAxMDEwMDEyMDE3MDcyMQ==",
				QUERY_XML:xml
			},
			success : function(result) {
				alert(result)
			},
			error : function(error) {
				alert(result+'121212')
			}
		});
	}
</script>
<%--<a href="logout">logout</a>--%>
</body>
</html>