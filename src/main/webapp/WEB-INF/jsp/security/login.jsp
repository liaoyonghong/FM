<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
	<title>VTC System</title>
	<link rel="icon" href="/VTC/assets/images/vioIcon.png"/>
	<style type="text/css" media="all">
		html {
			font-size: 14px;
			font-family: Arial, Helvetica, sans-serif;
		}

		#content {
			position: absolute;
			top: calc(50vh - 150px);
			left: calc(50vw - 435px);
			/*width: 360px;*/
			padding: 20px;
			border-radius: 3px;
			/*background-color: #dceff8;*/
		}

		#content form {
			display: inline-block;
			vertical-align: top;
		}

		#content input[type=text], #content input[type=password] {
			width: 98%;
			border: 1px solid #bbdceb;
			border-radius: 4px;
			height: 35px;
			text-indent: 15px;
			margin: 10px 0;
			font-family: Open Sans, helvetica, Roboto, sans-serif;
		}

		#content input[type=submit] {
			width: 100%;
			border: 1px solid #115E7F;
			border-radius: 4px;
			height: 35px;
			margin: 10px 0;
			color: #fff;
			background-color: #177FAB;
			cursor: pointer;
		}

		#content input[type=submit]:hover {
			color: #fff;
			border-color: #1CB1EF;
			background-color: #157299;
		}
	</style>
</head>

<body>
<div id="header" style="width:100%;margin-top:20px;" hidden>
	<div style="float:left;">
		<img src="/VTC/assets/images/vtcLogo.png">
		<div style="font-weight:bold;">
			<span>服務網絡:韋予力醫生醫務所</span><span style="color:#990066">(Vio)</span><br>
			<span style="color:#990066;margin-right:20px;">查詢電話 Enquiries: 2116 0011</span>
			<span style="color:#990066;">e-mail: vtc@doctorvio.com</span><br>
			<span>(有關批核查詢，請聯絡VTC人力資源科)</span>
		</div>
	</div>
	<img src="/VTC/assets/images/vioLogo.png" style="float: right;">
</div>
<div id="content">
	<img src="/VTC/assets/images/vioLogo.png">
	<form id="loginForm" method="post" action="#" autocomplete="off">
		<input type="text" name="username" placeholder="user用戶" required="required"/>
		<input type="password" name="password" placeholder="password密碼" required="required"/>
		<input type="submit" id="submit" value="Sign登錄"/>
		<div id="errorMsg"></div>
	</form>
</div>

<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/translate.min.js"></script>
<script>
	$(document).ready(function () {
		$("#loginForm").find("input[type=text],input[type=password]").val("");
		$("#submit").click(function (e) {
			$("#loginForm").off("submit");
			$("#loginForm").submit(function (e) {
				var postData = $(this).serializeArray();
				$.ajax({
					url: "/VTC/filterUrl",
					//url: "/VTC/user/loginValid",
					type: "POST",
					data: postData,
					/*
                    data: JSON.stringify({
                        username: $("#loginForm [name='username']").val(),
                        password: $("#loginForm [name='password']").val()
                    }),
                    contentType: "application/json; charset=utf-8",*/
					success: function (data) {
						console.log(data);
						const jData = JSON.parse(data);
						if (jData.success)
							window.location = 'home';
						else
							$("#errorMsg").html(jData.message);
					},
					error: function (data) {
						$("#errorMsg").html("Sorry,you are not allowed to access our website!");
					}
				})
				return false;
			})
		})
		document.onkeydown = function (event) {
			var event = document.all ? window.event : event;
			if ((event.keyCode || event.which) == 13) {
				$("#submit").click()
			}
		}
	})
</script>

</body>
</html>
