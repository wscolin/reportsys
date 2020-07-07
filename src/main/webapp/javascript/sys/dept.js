	$(document).ready(function() {
		$("#treediv").css("height",$(window).height()-50);
		//初始部门树
		innttree();
	});
	var DSJGDM,SHIJJGDM;//地市机构代码，县区机构代码
	//tree
	function innttree(){
		var setting = {
			async:{
				enable:true,
				type:"post",
				url:ctx+"/dept/tree",
				datatype:"json",
				autoParam: ["id"]
			},
			view: {
				dblClickExpand: true,
				showLine: true,
				selectedMulti: false,
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				fontCss: setFontCss
			},
			data: {
				simpleData: {
					enable:true
				}
			},callback: {
				onExpand: onExpand,
				onCollapse: onCollapse
			}
		};
		function setFontCss(treeId, treeNode) {
			return treeNode.ENABLED == 1 ? {color:"red"} : {};
		};
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (
				$("#editBtn_"+treeNode.tId).length>0 ||
				$("#addBtn_"+treeNode.tId).length>0  ||
				$("#upBtn_"+treeNode.tId).length>0  ||
				$("#setBtn_"+treeNode.tId).length>0  ||
				$("#downBtn_"+treeNode.tId).length>0
			) return;
			var addStr = "<span style='margin: 0px 5px;' class='font-blue icon-plus' id='addBtn_" + treeNode.tId + "' title='添加' onfocus='this.blur();'></span>";
			var editStr = "<span style='margin: 0px 5px;' class='font-blue-steel icon-note' id='editBtn_" + treeNode.tId + "' title='修改' onfocus='this.blur();'></span>";
			var upStr = "<span style='margin: 0px 5px;' class='font-red-thunderbird icon-arrow-up' id='upBtn_" + treeNode.tId + "' title='上移' onfocus='this.blur();'></span>";
			var downStr = "<span style='margin: 0px 5px;' class='font-red-sunglo icon-arrow-down' id='downBtn_" + treeNode.tId + "' title='下移' onfocus='this.blur();'></span>";
			var setStr = "<span style='margin: 0px 5px;' class='font-green icon-reload' id='setBtn_" + treeNode.tId + "' title='状态' onfocus='this.blur();'></span>";
			sObj.append(addStr);
			sObj.append(editStr);
			sObj.append(upStr);
			sObj.append(downStr);
			sObj.append(setStr);
			//绑定事件
			//状态设置
			var setBtn = $("#setBtn_"+treeNode.tId);
			if (setBtn) setBtn.bind("click", function(){
				var pId = "0";
				if(treeNode.pId!=null){
					pId = treeNode.pId
				}
				$.ajax({
					url : ctx+"/dept/set",
					type : "POST",
					data : "id="+treeNode.id+"&enabled="+treeNode.ENABLED,
					success : function(result) {
						var treeObj = $.fn.zTree.getZTreeObj("typeIdIdtree");
						var node = treeObj.getNodeByParam("id", pId, null);
						treeObj.reAsyncChildNodes(node, "refresh");
					}
				});
			});
			//上
			var upBtn = $("#upBtn_"+treeNode.tId);
			if (upBtn) upBtn.bind("click", function(){
				var pId = "0";
				if(treeNode.pId!=null){
					pId = treeNode.pId
				}
				$.ajax({
					url : ctx+"/dept/sortup",
					type : "POST",
					data : "id="+treeNode.id+"&pId="+pId,
					success : function(result) {
						if(result=="nomove"){
							window.parent.toastr[MES_INFO]("已在第一位！");
						}else{
							var treeObj = $.fn.zTree.getZTreeObj("typeIdIdtree");
							var node = treeObj.getNodeByParam("id", pId, null);
							treeObj.reAsyncChildNodes(node, "refresh");
						}
					},
					error : function() {
						window.parent.toastr[MES_ERROR]("数据保存失败！");
					}
				});
			});
			//下
			var downBtn = $("#downBtn_"+treeNode.tId);
			if (downBtn) downBtn.bind("click", function(){
				var pId = "0";
				if(treeNode.pId!=null){
					pId = treeNode.pId
				}
				$.ajax({
					url : ctx+"/dept/sortdown",
					type : "POST",
					data : "id="+treeNode.id+"&pId="+pId,
					success : function(result) {
						if(result=="nomove"){
							window.parent.toastr[MES_INFO]("已在末位！");
						}else{
							var treeObj = $.fn.zTree.getZTreeObj("typeIdIdtree");
							var node = treeObj.getNodeByParam("id", pId, null);
							treeObj.reAsyncChildNodes(node, "refresh");
						}
					},
					error : function() {
						window.parent.toastr[MES_ERROR]("数据保存失败！");
					}
				});
			});
			//添加
			var btnadd = $("#addBtn_"+treeNode.tId);
			if (btnadd) btnadd.bind("click", function(){
				editid=null;
				$("#itemForm input[name=DEPTID]").removeAttr("readonly");
				$("#PARENTID").val(treeNode.name);
				$("#PARENTID").attr("title",treeNode.name);
				$("#itemForm input[name=PARENTID]").val(treeNode.id);
				//实际业务上级
				$("#itemForm input[name=DEPT_FUN]").attr("title",treeNode.name);
				$("#itemForm input[name=DEPT_FUN]").val(treeNode.name);
				$("#itemForm input[name=DEPT_CATE]").val(treeNode.id);

				$("h4").text("新增");
				$('#itemModal').modal('show');
				$('#itemModal').on('shown.bs.modal', function() {
					$('input[name=DEPTID]').focus();
				});
			});
			//修改
			var btnedit = $("#editBtn_"+treeNode.tId);
			if (btnedit) btnedit.bind("click", function(){
				$("#itemForm input[name=DEPTID]").attr("readonly","readonly");
				$.ajax({
					url:ctx+"/dept/getdeptbyid",
					type:"POST",
					dataType:"json",
					data:"id="+treeNode.id,
					success:function(data){
						if(data.PARENTID==0){
							$("#PARENTID").val("该部门为一级部门");
						}else{
							$("#PARENTID").val(data.PARENTNAME);
							$("#PARENTID").attr("title",data.PARENTNAME);
						}
						$("#itemForm input[name=PARENTID]").val(data.PARENTID);
						$("#itemForm input[name=DEPTID]").val(data.DEPTID);
						$("#itemForm input[name=NAME]").val(data.NAME);
						$("#itemForm select[name=DEGREE]").val(data.DEGREE);
						$("#itemForm select[name=JGLX]").val(data.JGLX);
						$("#itemForm input[name=DESCN]").val(data.DESCN);
						//实际业务上级
						$("#itemForm input[name=DEPT_FUN]").attr("title",data.DEPT_FUN);
						$("#itemForm input[name=DEPT_FUN]").val(data.DEPT_FUN);
						$("#itemForm input[name=DEPT_CATE]").val(data.DEPT_CATE);

						$("#itemForm select[name=SJJGDM]").val(data.SJJGDM);
						DSJGDM =data.DSJGDM;
						SHIJJGDM =data.SHIJJGDM;
						$("#itemForm select[name=SJJGDM]").trigger("change");

						editid = data.DEPTID;
					}
				});
				$("#itemForm input[name=id]").val("edit");
				$("h4").text("修改");
				$('#itemModal').modal('show');
				$('#itemModal').on('shown.bs.modal', function() {
					$('input[name=DEPTID]').focus();
				});
			});
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
			$("#editBtn_"+treeNode.tId).unbind().remove();
			$("#upBtn_"+treeNode.tId).unbind().remove();
			$("#downBtn_"+treeNode.tId).unbind().remove();
			$("#setBtn_"+treeNode.tId).unbind().remove();
		};
		$.ajax({
			url:ctx+"/dept/tree",
			type:"POST",
			dataType:"json",
			success:function(data){
				$.fn.zTree.init($("#typeIdIdtree"), setting, data);
				//强行展开一级
				ChildNodes($.fn.zTree.getZTreeObj("typeIdIdtree"));
			}
		});
	}
	var editid="";
	function btn_save_click(){
		var pId = $("#itemForm input[name=PARENTID]").val();
		$.ajax({
			url : ctx+"/dept/bankcodeisone",
			type : "POST",
			data : {
				deptid:$("#itemForm input[name=DEPTID]").val()
			},
			async: false,
			cache: false,
			success : function(result) {
				if (result == "success" ||$("#itemForm input[name=DEPTID]").val() == editid) {
					var url = ctx+"/dept/add";
					if ($("#itemForm input[name=id]").val() != "") {
						url = ctx+"/dept/edit";
					}
					var form = $("#itemForm");
					if (form.valid()) {
						$.ajax({
							url:url,
							type: "POST",
							data: $("#itemForm").serialize(),
							success: function (result) {
								if (result == "success") {
									$("#itemModal").modal("hide");
									window.parent.toastr[MES_SUCCESS]("保存成功");
									var treeObj = $.fn.zTree.getZTreeObj("typeIdIdtree");
									var node = treeObj.getNodeByParam("id", pId, null);
									treeObj.reAsyncChildNodes(node, "refresh");
									btn_close_click();
								} else {
									window.parent.toastr[MES_ERROR]("保存失败");
								}
							},
							error: function () {
								window.parent.toastr[MES_ERROR]("数据保存失败！");
							}
						});
					}
				}else{
					window.parent.toastr[MES_WARN]("部门编号已存在");
					$("#itemForm input[name=DEPTID]").val("");
				}
			}
		});
	}
	//验证
	$("#itemForm").validate({
		rules : {
			DEPTID : {
				required : true,
				number:true,
				minlength:11,
				maxlength : 11
			},
			NAME : {
				required : true,
				maxlength : 100
			},
			SJJGDM:{
				required : true,
			},
			SHIJJGDM:{
				required : true,
			},
			DSJGDM:{
				required : true,
			}
		},
		messages : {
			DEPTID : {
				number:"请输入数字",
				required : "请输入部门ID",
				minlength : "请输入11位部门编码",
				maxlength : "请输入11位部门编码"
			},
			NAME : {
				required : "请输入部门名称",
				maxlength : "内容过长."
			},
			SJJGDM:{
				required : "请选择",
			},
			SHIJJGDM:{
				required : "请选择",
			},
			DSJGDM:{
				required : "请选择",
			}
		},
		highlight : function(element) {
			$(element).closest('div').addClass('has-error');
		},
		success : function(label) {
			$(label).closest('div').removeClass('has-error');
			$(label).remove();
		}
	});