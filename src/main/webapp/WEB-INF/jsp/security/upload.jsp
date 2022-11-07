<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
  <title>Upload</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
  <script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
  <script src="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
  <style>
    #preview div {
      display: inline-block;
      text-align: center;
      padding: 0.5%;
      width: 32%;
    }
    #preview div img{
      width: 100%;
    }
	div.ui-radio{
		display: inline-block;
		width: 32%;
		max-width: 120px;
	}
	div.ui-radio label{
		font-size: 14px;
		font-weight: 400;
	}
	div.ui-radio label.ui-radio-on{
		background-color: lavender !important;
	}
	div.ui-radio label.ui-radio-off{
		background-color: #f6f6f6 !important;
	}
	div.ui-radio label.ui-btn:hover{
		background-color: lavender;
	}
	div.ui-radio label.ui-btn-icon-left{
		padding: 0.5em;
	}
	div.ui-radio label.ui-btn-icon-left:after{
		content: none;
	}
	#chooseFile{
		padding: 0.5em;
	}
	#chooseFile span{
		margin: 0;
		padding: 0 0 0 2em;
		border: 0;
	}
	#preview div p{
		margin: 0 0 10px 0;
	}
	.step{
		width: 25px;
		height: 25px;
		line-height: 25px;
		border-width: 1px;
		border-style: solid;
		border-radius: 20%;
		vertical-align: middle;
		text-align: center;
		margin-top:1.5em;
	}
	.step_div{
		text-align: center;
	}
  </style>
</head>

<body>
  <div data-role="page" id="pageOne">
    <div data-role="header">
      <h3>Upload</h3>
    </div>

    <div data-role="content">
      <!-- <form method="post" action="http://192.168.2.92:8080/VIOVOSApplication/uploadVoucherImage/upload" id="uploadForm" enctype="multipart/form-data" data-ajax="false"> -->
      <form method="post" id="uploadForm" enctype="multipart/form-data" data-ajax="false">
			
		<div class="step" style="margin-top:0;">1</div>
		<div class="step_div">
			<label><input name="uploadType" type="radio" value="v" checked>Report</label>
			<label><input name="uploadType" type="radio" value="r">Identity</label>
			<label><input name="uploadType" type="radio" value="a">Progress</label>
			<br/>
		</div>
		
		<div class="step">2</div>
		<div class="step_div">
			<input id="vFile" type="file" name="file" accept="image/*" multiple/>
			<a id="chooseFile" class="ui-btn ui-btn-b ui-btn-inline ui-corner-all">
				<span class="ui-btn ui-btn-b ui-btn-inline ui-corner-all ui-btn-icon-left ui-icon-plus">Choose FIle</span>
				<span style="padding-left:0.5em;">/</span>
				<span class="ui-btn ui-btn-b ui-btn-inline ui-corner-all ui-btn-icon-left ui-icon-camera">Take Photo</span>
			</a>
		</div>
        
		<div id="preview"></div>
		
		<div class="step">3</div>
		<!-- <input type="submit" data-inline="true" value="submit"> -->
		<div class="step_div">
			<button id="upload-btn" class="ui-btn ui-btn-inline ui-shadow ui-corner-all">Upload</button>
			<input type="reset" data-inline="true" value="Reset">
		</div>
      </form>
	  

      <a id="errMsg" href="#myPopup" data-rel="popup" data-position-to="window"></a>
      <div data-role="popup" id="myPopup" class="ui-content" data-overlay-theme="b">
        <p id="popContent"></p>
      </div>
    </div>

		<div data-role="footer" data-position="fixed">
			<h3>2017~2018&copy;香港华讯系统</h3>
		</div>
  </div>

  <div data-role="page" id="pageTwo">
    <div data-role="header">
      <h3>Upload</h3>
    </div>

    <div data-role="content">
      <p class="msg"></p>
      <a href="#" onclick="javascript:window.close();">关闭</a>
    </div>

    <div data-role="footer" data-position="fixed">
			<h3>2017~2018&copy;香港华讯系统</h3>
		</div>
  </div>

  <script>
    $(document).ready(function() {
	  
	  if(window.innerWidth > 800){
		$(".step_div").css("text-align","left");
	  }

      var old;
      var isFirst = true;
      var totalFiles = [];
      $('#vFile')[0].value = "";
      $('#vFile').parent().hide();

      $("#chooseFile").click(function(e) {
        e.defaultPrevented;
        $('#vFile').trigger("click");
      });

      $('#vFile').change(function(e) {

        var qty = 0;
        var fileList = e.target.files || e.dataTransfer.files;
        if(!fileList.length || !!isImage(this)) return;
        for (var i = 0; i < totalFiles.length; i++) {
          if(!!totalFiles[i]) { qty ++; }
        }
        if(qty + fileList.length > 9) {
          $('#popContent').html("您上傳的圖片不能超過9張！請重新選擇！");
          $('#errMsg').trigger('click');
          this.value = "";
          return;
        }

        for (var i = 0; i < fileList.length; i++) {
          totalFiles.push(fileList[i]);
        }

        if(!isFirst) {
          var oldFile = $('<input type="file" hidden name="file" accept="image/*" multiple>');
          oldFile[0].files = old;
          $('#uploadForm').append(oldFile);
        }
        old = fileList;
        isFirst = false;
        loadImages(totalFiles, "preview");
      });

      function isImage(img) {
        var filePath = img.value;
        var fileExt = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
        if (!fileExt.match(/.jpeg|.jpg|.gif|.png|.bmp/i)) {
          $('#popContent').html("您上傳的文件不是圖片,請重新上傳！");
          $('#errMsg').trigger('click');
          img.value = "";
          return true;
        }
      }

      function loadImages(fileList, containerid) {
        if (!!FileReader) {
            var html = '', i = 0;
            if($("#preview .upload_pre"))
            $("#preview").html('<div class="upload_pre"></div>');
          	var funAppendImage = function() {
            	var file = fileList[i];
              if (!file && fileList.length-1 > i) {
                i++;
                funAppendImage();
              }else	if (file) {
            			var reader = new FileReader()
            			reader.onload = function(e) {
            				html = html + '<div id="uploadList_'+ i +'" class="upload_append_list"><p>'+
            					'<img id="uploadImage_' + i + '" src="' + e.target.result + '" class="upload_image" />'+
              				'<a href="javascript:" class="upload_delete" title="刪除" data-index="'+ i +'">刪除</a><br /></p>'+
            					'<span id="uploadProgress_' + i + '" class="upload_progress"></span>' +
            				'</div>';

            				i++;
            				funAppendImage();
            			}
            	    reader.readAsDataURL(file);
          		} else {
          			$("#preview").html(html);
          			if (html) {
          				//删除方法
          				$(".upload_delete").click(function() {
          					var idx = parseInt($(this).attr("data-index"));
                    $("#uploadList_" + idx).fadeOut();
                    totalFiles[idx] = '';
          					return false;
          				});
          			}
          		}
          };
        	funAppendImage();
        }
      }

      $("input[type='reset']").on("tap",function(){
        $('#preview').empty();
        isFirst = true;
        totalFiles = [];
      });

      $("#upload-btn").click(function(e) {

        if(!$('#uploadForm input[name="uploadType"]:checked').val()) {
          $('#popContent').html("請選擇上傳類型！");
          $('#errMsg').trigger('click');
          return false;
        }else if(totalFiles.length <= 0) {
          $('#popContent').html("沒有需要上傳的圖片！");
          $('#errMsg').trigger('click');
          return false;
        }else {

          var fd = new FormData();
          fd.append("uploadType", $('#uploadForm input[name="uploadType"]:checked').val());
          for (var i = 0; i < totalFiles.length; i++) {
            if(!!totalFiles[i]) {
              fd.append("file", totalFiles[i]);
            }
          }

          $.ajax({
            url: "/THCMSApplication/vosImg/uploadTran",
            method: "POST",
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            async: false,
            success: function(data){
              $('#pageTwo p.msg').html(data);
              window.location.href = '#pageTwo';
            },
            error: function(data){
              alert("upload failed, try again please!");
            }
          });
          return false;

        }

      });

    });
  </script>
</body>

</html>
