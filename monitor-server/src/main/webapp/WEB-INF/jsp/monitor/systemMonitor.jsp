<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage="" %>
<%-- <%@ include file="/common/meta.jsp"%> --%>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
<html>
  <head>
    <title>服务节点管理</title>
    <style type="text/css">
    	.domain{width:100%;padding:0px;margin:0px;}
    </style>
    <link rel="stylesheet" type="text/css" href="<s:url value="../../newStyle/css/button.css"/>"/>
	<!-- 业务模块的dwr -->
	<%-- <script type="text/javascript" src="<s:url value="/dwr/interface/ProcessServerAction.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/dwr/interface/WebServerAction.js"/>"></script>
	
	<script type="text/javascript" src="<s:url value="/dwr/interface/SysMonitor.js"/>"></script> --%>
	<!-- 业务功能js -->
    <script type="text/javascript" src="business/sysMonitor/js/sysMonitor.js"></script>
     
	<script type="text/javascript" src="Highcharts-3.0.6/js/highcharts.js"></script>
	<script type="text/javascript" src="Highcharts-3.0.6/js/highcharts-more.js"></script>
	
	<style type="text/css">
	html{-ms-overflow-style:none;}
	.mtable {
	padding: 0;
	margin: 0;
	border-collapse: collapse;
}

td {
	border: 1px solid #C1DAD7;
	background: #fff;
	font-size: 11px;
	padding: 1px 1px 1px 1px;
	color: #4f6b72;
	font-size: 12px;
}

td.alt {
	background: #F5FAFA;
	color: #797268;
}

input.te {
	text-indent: 30px;
}

input.buttona {
	width: 99px;
	height: 28px;
	background: url(images/xgzh_1.png) no-repeat;
	border: none;
	cursor: pointer;
	color: #fff;
	text-align: left;
	text-indent: 25px;
}

input.buttonb {
	width: 99px;
	height: 30px;
	background: url(images/gxcg_1.png) no-repeat;
	border: none;
	cursor: pointer;
	color: #fff;
	text-align: left;
	text-indent: 18px;
}

input.buttonc {
	width: 69px;
	height: 21px;
	background: url(images/code-btn-bg.gif) no-repeat;
	border: none;
	cursor: pointer;
	color: #000;
	text-align: left;
	text-indent: 20px;
}
    	#initGridStyle .gridCon table tr td{height: 40px;line-height: 40px;}
    	#initGridStyle .gridCon table thead tr td{height: 35px;line-height: 35px;}
    	.yj{
    	    background-color:#FFFFAA;
		    border: 2px solid #CCC;
		    -moz-border-radius: 15px; 
		    -webkit-border-radius: 15px; 
		    border-radius:15px;           
		}

    	.server_img{
		    border: 2px solid #E0E0E0;
		    -moz-border-radius: 15px; 
		    -webkit-border-radius: 15px; 
		    border-radius:15px;           
		}
		#triangle-left {
			float:left;
			position:relative;
		    width: 0;
		    height: 0;
		    border-top: 50px solid transparent;
		    border-right: 100px solid red;
		    border-bottom: 50px solid transparent;
		}
		.server_info{
			line-height:20px;
			font-weight: bold;
		}
		.server_info span span{
			color:blue;
		}
		td .menu{display:block;height:60px;background:#dfe9f5 !important;border-radius:6px;border: 1px solid #cdddf0 !important;}
		tbody,tr{width:100%;display:block}
		table tbody td{display:inline-block}
		#sanDynamic_id input{width:50% !important}
		#sanDynamic_id li{float:left;}
		#sanDynamic_id{clear:both;padding:16px}
		#sanDynamic_id li:nth-child(1){height:36px !important;width:140px}
		.menu{background:#fff !important}
		#sanDynamic_id li:nth-child(2){margin:0px !important}
		#sanDynamic_id button{margin:-3px !important}
		#server_div{margin-bottom:250px}
		#dir_tree{height:670px !important}
		
		
		@media (max-width: 1336px) {
      			.body .menu{height:90px}
      			#sanDynamic_id li:nth-child(1){width:100%;}
		}
		@media (max-width: 1312px) {
      			#dir_tree{height:840px !important}
		}
		
		
		
		
    </style>
   
    </head>
    <body style="background: #fff" scrolling="no" >
	<div class="" style="width: 100%">
		<!-- 页面能见区域的内容开始 -->
		<div class="body" id="dir_tree" style="background:#ebf0f6;">
			<table style="width: 100%; height: 100%;display:inline-block;margin-top:10px">
				<tr style="min-width:1009px">
					<td valign="top" style="width:17%;display:inline-block;box-shadow: 0 2px 8px rgba(1,39,66,0.1);margin-top: 4px;  border-radius: 5px 5px 0 0;border-top: 4px solid #a1b3c9;margin-left:8px;margin-right:6px;">
					    <div >
							<!-- 查询输入信息的开始 -->
							<div class="menu" style="width:94%;background-color: #E6E6E6;margin:10px 6px">
								<ul id="sanDynamic_id"  class="domain_tool">
									<li>
									<span>服务器：</span>
										<input  type="text" name="queryObj" id="hostName"/>
									</li>
									<li id="san_disable_time"><a href="javascript:void(0)" onclick="page.init();"><button type="button" onclick="productPage.grid.reload(false, this);" class="x_button button-royal button-rounded button-raised" style="width: 50px;height: 22px;line-height: 22px">查询</button></a></li>
								</ul>
							</div>
					        <!-- 查询输入信息的结束 -->
					        
							<div id="server_div" style="float:left; background-color:rgb(255,255,255);overflow-y:auto;">
							</div>
						</div>
					</td>
					<td valign="top" style="width:80%;box-shadow: 0 2px 8px rgba(1,39,66,0.1);margin-top: 4px;  border-radius: 5px 5px 0 0;border-top: 4px solid #a1b3c9;">
					    <iframe marginwidth="0px" marginheight="0px"
							name="iframe_content" id="iframe_content" scrolling="no"
							frameborder="0" frameborder="0" height="800px " src="" width="100%"></iframe>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	 <!--服务器信息弹出框  -->
  	 <div id="server_info" class="yj" style="width: 220px; height: 220px; display: none; position: absolute; ">
     </div>
</body>
</html>
