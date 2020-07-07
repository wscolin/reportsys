/**
 * 时间戳格式化函数 
 */
function add0(m) {
	return m < 10 ? '0' + m : m;
}
function formatDateTime(str) {
	if(str){
		var time = new Date(parseInt(str.time));
		var y = time.getFullYear();
		var m = time.getMonth() + 1;
		var d = time.getDate();
		var h = time.getHours();
		var mm = time.getMinutes();
		var s = time.getSeconds();
		return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
	}else{
		return"";
	}
}
function formatDate(str) {
	var time = new Date(parseInt(str.time));
	var y = time.getFullYear();
	var m = time.getMonth() + 1;
	var d = time.getDate();
	return y + '-' + add0(m) + '-' + add0(d);
}
//关闭弹窗清空表单
function btn_close_click(obj) {
	//获取所有的label并去除它们验证不通过的样式
	$("label").each(function () {
		$(this).closest('div').removeClass('has-error');
	});
	$('form').each(function (index) {
		try {
			$(this).data('validator').resetForm();
		}catch (e){}
	});
}
//自定义验证
$.validator.addMethod("IP",
	function (value) {
		return /^((25[0-5]|2[0-4]\d|[01]?\d\d?)($|(?!\.$)\.)){4}$/.test(value);
	},
	"请输入正确IP"
)
$.validator.addMethod("IDENTITY",
	function (value) {
		return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
	},
	"请输入正确身份证"
)
$.validator.addMethod("PHONE",
	function (value) {
		return /^1[3-8]+\d{9}$|^(0\d{2,3}-?|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?$/.test(value);
	},
	"请输入正确电话"
)
$.validator.addMethod("EMAIL",
	function (value) {
		if(value){
			return /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(value);
		}else{
			return true;
		}
	},
	"请输入正确EMAIL"
)
//tree
function onExpand(event, treeId, treeNode){
	var cookie = $.cookie("z_tree");

	var z_tree = null;
	if(cookie){
		z_tree = JSON.parse(cookie);
	}

	if(!z_tree){
		z_tree = new Array();
	}
	if(jQuery.inArray(treeNode.id, z_tree)<0){
		z_tree.push(treeNode.id);
	}


	$.cookie("z_tree", JSON.stringify(z_tree))
}

function onCollapse(event, treeId, treeNode){
	var cookie = $.cookie("z_tree");

	var z_tree = null;
	if(cookie){
		z_tree = JSON.parse(cookie);
	}

	if(!z_tree){
		z_tree = new Array();
	}
	var index = jQuery.inArray(treeNode.id, z_tree);

	z_tree.splice(index, 1);

	$.cookie("z_tree", JSON.stringify(z_tree))
}
//传入ztree对象展开cookie中的节点（非异步时用）
function cookieztree(zTree) {
	var cookie = $.cookie("z_tree");
	z_tree = JSON.parse(cookie);
	if(z_tree){
		for(var i=0; i< z_tree.length; i++){
			var node = zTree.getNodeByParam('id', z_tree[i])
			zTree.expandNode(node, true)
		}
	}
}
//异步加载时 强行加载第一级
function ChildNodes(treeObj) {
	var nodes = treeObj.getNodes();
	if (nodes.length>0) {
		$.each(nodes,function () {
			treeObj.reAsyncChildNodes(this, "refresh");
		});
	}
}
//清除redis缓存
function cleanredis(redisname){
	$.ajax({
		url:ctx+"/cleanredis",
		type : "POST",
		data : "redisname="+redisname,
		success : function(result) {
			if(result=="success"){
				window.parent.toastr[MES_SUCCESS]("清除成功！！！");
			}else{
				window.parent.toastr[MES_ERROR]("清除失败！！！");
			}
		}
	});
}
//trim文本框的值
function trimval(obj) {
	$(obj).val($(obj).val().trim());
}
//计算表格高度
function tableHeight(otherheight){
	var domHeight = $(".page-content").height();
	var searchHeight = $(".search").outerHeight(true);
	var pageHeight = $(".pageRow").outerHeight(true);
	var tableHeadHeight = $(".dataTables_scrollHead").outerHeight(true);
	var buttonHeight = $(".portlet-title").outerHeight(true);
	var tableBodyHeight = domHeight - buttonHeight - pageHeight - tableHeadHeight - searchHeight-25;
	if(otherheight){
		tableBodyHeight=tableBodyHeight-otherheight;
	}
	$('.dataTables_scrollBody').height(tableBodyHeight);
}
//格式化时间 毫秒数转日期
function formatDate(timestamp) {
	var time = new Date(timestamp);
	var y = time.getYear()+1900;
	var m = time.getMonth() + 1;
	var d = time.getDate();
	var h = time.getHours();
	var mm = time.getMinutes();
	var s = time.getSeconds();
	return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
}
function DateUtil() {
}
DateUtil.Format = function(format,date){
	if (typeof(date)=="string") {
		date = date.replace(/-/g,"/").replace(/\+08:00/,"");
	}
	date = new Date(date);
	var o = {
		'M+' : date.getMonth() + 1, //month
		'd+' : date.getDate(), //day
		'H+' : date.getHours(), //hour
		'm+' : date.getMinutes(), //minute
		's+' : date.getSeconds(), //second
		'q+' : Math.floor((date.getMonth() + 3) / 3), //quarter
		'S' : date.getMilliseconds() //millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp('(' + k + ')').test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));
	return format;
}
DateUtil.getIntervalminu=function (format,m){
	var date = new Date();
	var timestamp = date.getTime()-(m*60*1000);
	return DateUtil.Format(format,timestamp);
}
DateUtil.comptime = function(begindate,enddate){
	var a = begindate.split(" ");var b = enddate.split(" ");
	var arr = a[0].split("-");var arrh = a[1].split(":");
	var arr1 = b[0].split("-");var arr1h = b[1].split(":");
	var begintime = new Date(arr[0], arr[1]-1, arr[2],arrh[0],arrh[1],arrh[2]).getTime();
	var endtime = new Date(arr1[0], arr1[1]-1, arr1[2],arr1h[0],arr1h[1],arr1h[2]).getTime();
	return begintime<=endtime?true:false;
};