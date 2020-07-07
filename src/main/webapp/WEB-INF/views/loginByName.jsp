<%@ page language="java" import="org.apache.shiro.SecurityUtils" pageEncoding="UTF-8" %>
<%@ page import="org.apache.shiro.subject.Subject" %>
<html>
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="js.jsp" %>
    <!-- 自定义CSS -->
    <style type="text/css">
        .loginPage {
            background-image: url(${ctx}/images/login_bg2.jpg) !important;
            background-size: cover;
            background-attachment: fixed;
            background-repeat: no-repeat;
            -webkit-background-size: cover;
        }
    </style>
    <script type="text/javascript">
        // base64加密开始
        var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"
            + "wxyz0123456789+/" + "=";

        function encode64(input) {

            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;
            do {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);
                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;
                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }
                output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
                    + keyStr.charAt(enc3) + keyStr.charAt(enc4);
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
            } while (i < input.length);

            return output;
        }
        // base64加密结束
    </script>
</head>
<body class="loginPage container-fluid">
<div class="row" style="margin-left: 0px;margin-right: 0px;height:200px;">
</div>
<div class="row" style="margin-left: 0px;margin-right: 0px; height:50%;">
    <div class="col-xs-7 col-md-7 col-lg-7">
    </div>
    <div class="col-xs-3 col-md-3 col-lg-3">
        <div class="portlet light blue" style="height:300px;width: 450px;">
            <div class="portlet-title">
                <div class="caption" style="font-size: 18px;">用户登录</div>
            </div>
            <div class="portlet-body form">
                <form class="form-horizontal" action="login" name="form1" method="post">
                    <div class="form-body" style="padding-top: 20px;">
                        <div class="form-group" style="margin-bottom: 15px;">
                            <label class="col-xs-3 col-md-3 col-lg-3" style="text-align:right;margin-top: 7px;">用户名:</label>
                            <div class="col-xs-6 col-md-6 col-lg-6">
                                <div class="input-group"><span class="input-group-addon" style="background-color: #fff;"><i class="fa fa-user"></i></span>
                                    <input style="height: 35px;width:150px;border-radius: 0px!important;" type="user" id="iptUser" class="form-control" name="username" placeholder="请输入用户名" value="${hasuser.USERID}"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" style="margin-bottom: 15px;">
                            <label class="col-xs-3 col-md-3 col-lg-3" style="text-align:right;margin-top: 7px;">密&nbsp;码:</label>
                            <div class="col-xs-6 col-md-6 col-lg-6">
                                <div class="input-group"><span class="input-group-addon" style="background-color: #fff;"><i class="fa fa-lock"></i></span>
                                    <input style="height: 35px;width:150px;border-radius: 0px!important;" type="password" id="iptPwd" class="form-control" placeholder="请输入密码" value="${hasuser.PASSWORD}"/>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="password">
                        <br>
                        <div class="form-group">
                            <div class="col-xs-3 col-md-3 col-lg-3"></div>
                            <div class="col-xs-6 col-md-6 col-lg-6">
                                <button id="gologin" data-required="1" class="btn btn-success" type="button" onclick="submintlogin();">登录</button>&nbsp;&nbsp;
                                <a href="javascript:;" id="btnQuit" data-required="1" class="btn btn-info">取消</a></div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-xs-2 col-md-2 col-lg-2">
    </div>
</div>
<div class="row" style="margin-left: 0px;margin-right: 0px;height:20%;">
</div>
<script type="application/javascript">
    //防止iframe 嵌套登录页
    if (window != top) {
        top.location.href = location.href;
    }
    //已经登录再次访问登录直接跳转到主页
    <%
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipal()!=null){
    %>
    window.location = "${ctx}";
    <%
        }
    %>
    //登录失败验证
    if ("${error}" != "") {
        bootbox.alert("${error}");
    }
    ;
    if ("${hasuser}" != "") {
        $("#gologin").click();
    }
    function submintlogin() {
        $("form input[name=password]").val(encode64($("#iptPwd").val()));
        $("form").submit();
    }
    $(document).on("keydown", function (e) {
        if (e.keyCode != 13)return;
        submintlogin();
    });

</script>
</body>
</html>
