<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>
<%-- <%@ include file="/common/meta.jsp"%> --%>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
<html>
<head>
<title>服务节点管理</title>
<%-- <script type="text/javascript" src="<s:url value="/business/map/config.js" includeParams="false"/>"></script>  --%>
<style type="text/css">
	.domain {
		width: 100%;
		padding: 0px;
		margin: 0px;
	}
	#webServerList > table{display:inline-block;width:100%}
	#webServerList > table > tbody{display:inline-block;width:100%}
	#webServerList > table > tbody > tr{display:inline-block;width:100%}
	.table_head1{font-size:16px;font-weight:bold;color:#666666;}
	.block_cont1{border:1px solid #c8c8c8;background:#fff;}
	.rightC_syzt_table td{font-size:12px; border-bottom:1px dashed #d8d8d8;height:20px; line-height:18px;}
	.rightC_syzt_table1 td{font-size:12px; border-bottom:1px dashed #d8d8d8;height:25px; line-height:25px;}
	.table_title{font-size: 16px; font-family: "Lucida Grande", "Lucida Sans Unicode", Verdana, Arial, Helvetica, sans-serif; white-space: nowrap; visibility: visible; color: #274b6d;}
	#webServerList td{width:9%}
	#webServerList td:nth-child(1){border-left: none;}
	#webServerList tr:nth-child(2) td{border-bottom: none;}
	#systemInfo_tab td span{width:70px;display:inline-block;}
	#systemInfo_tab td{text-align:left !important}
	#webServerList tbody td{line-height:22px !important;height:52px;}
	html::-webkit-scrollbar{width:0px}
	html{-ms-overflow-style:none;overflow:-moz-scrollbars-none}
</style>
<link ref="stylesheet" href="style/blue/domain_style/domain.css"/>
<link ref="stylesheet" href="style/red/domain_style/domain.css"/>
<!-- 业务模块的dwr -->
<%-- <script type="text/javascript"
	src="<s:url value="/dwr/interface/ProcessServerAction.js"/>"></script>
	<script type="text/javascript"
	src="<s:url value="/dwr/interface/ProcessTaskDataAction.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/dwr/interface/WebServerAction.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/dwr/interface/BusinessLogAction.js"/>"></script> --%>

<%-- <script type="text/javascript"
	src="<s:url value="/dwr/interface/SysMonitor.js"/>"></script> --%>
<!-- 业务功能js -->
<script type="text/javascript"
	src="bussiness/sysMonitor/js/sysMonitor_info.js"/>"></script>

<script type="text/javascript"
	src="Highcharts-3.0.6/js/highcharts.js"/>"></script>
<script type="text/javascript"
	src="Highcharts-3.0.6/js/highcharts-more.js"/></script>
<script type="text/javascript"
	src="jquery/jquery-1.7.2.min.js"/>"></script>
<script type="text/javascript">
	   var tphId = '<%= request.getParameter("tphId")%>';
	   var tphIp = '<%= request.getParameter("tphIp")%>';
	   var tphType = '<%= request.getParameter("tphType")%>';
</script>
</head>
<div class="domain"
	style="background-color: #FFF;height: 100%;padding:10px;padding-right:0px;width: 100%;">
		<div class="context" style="padding-left:0px">
		        <div class="img">
		        <!-- background:url('/webfront/style/seafoam/domain_style/layers.svg') no-repeat; -->
		        	<span style="width:15px;display:block;margin-left:6px;background-size:cover;height:15px "></span>
		    	</div>
		    	<div class="key">您现在的位置为：</div>
		    	<div class="value">资源状态</div>
		    </div>
	<div class="body" style="width:100%;background: #fff;margin: 0 auto">
		<!-- 硬件性能监控信息列表 -->
		<div class="x-tabs-wrap">
			
			<div class="x-tabs-strip-wrap" style="margin-left:10px;text-align: left;">
				<table class="x-tabs-strip" border="0" cellspacing="0"
					cellpadding="0" >
					<tbody style="background:#fff;">
						<!-- <tr>
							<td id="Hardware_tab" class="on"><a class="x-tabs-right"
								href="javascript:void(0)" onclick="sysMonitor_info.changeTab('A');"><span
									class="x-tabs-left"><em class="x-tabs-inner"><span
											title="硬件监控" class="x-tabs-text">硬件监控</span> </em> </span> </a></td> -->
											
							
							<!-- <td id="Task_tab"><a class="x-tabs-right"
								href="javascript:void(0)" onclick="sysMonitor_info.changeTab('B');"><span
									class="x-tabs-left"><em class="x-tabs-inner"><span
											title="任务监控" class="x-tabs-text">任务监控</span> </em> </span> </a></td> -->
						<!-- </tr> -->
					</tbody>
				</table>
			</div>
		</div>
		<div id="webServerList" >
		
			<table style="width: 98%">
				<tr>
					<td>
						<div class="menu" style="width: 100%;margin-top:10px;height:34px">
							<ul class="domain_tool">
								<li style="font-size: 14px; font-weight: bold;line-height:34px;height:34px !important">硬件基本信息</li>
							</ul>
						</div>
						<div
							style="width: 100%; border-right: 0px; border-top: 1px solid #FFF;">
							<!-- <table id="systemInfo_tab" cellspacing="0" cellpadding="0"
								style="width: 100%;display:inline-block !important;margin-top:4px" class="xform">
								 -->
							<table id="systemInfo_tab" cellspacing="0" cellpadding="0"
								style="width: 100%;margin-top:4px" class="xform">
								<tr>
								    <td class="key">服务器名</td>
									<td class="value" ><span property="hostName">
									</td>
									<td class="key">IP地址</td>
									<td class="value" ><span property="ip">
									</td>
									<td class="key">操作系统</td>
									<td class="value" ><span subproperty="os" property="resourceInfo"></td>
									<!-- <td class="value" ><span subproperty="osVersion" property="resourceInfo">
									</td> -->
									<td class="key">系统版本</td>
									<td class="value" ><span subproperty="osVersion" property="resourceInfo">
									</td>
									<td class="key">系统类型</td>
									<td class="value" ><span subproperty="system" property="resourceInfo">
									</td>
									<td class="key">服务状态</td>
									<td class="value" ><span id="server_status">
									</td>
									<!-- <td class="key">最大作业数</td>
									<td class="value" ><span property="processingCapacity"></td> -->
								</tr>
								<tr>
									<td class="key">最大swap空间</td>
									<td class="value"><span subproperty="totalSwap" property="resourceInfo">
									</td>
									<td class="key">可用swap空间</td>
									<td class="value"><span subproperty="freeSwap" property="resourceInfo">
									</td>
									<td class="key">内存总量</td>
									<td class="value"><span subproperty="totalMem" property="resourceInfo"></td>
									<!-- <td class="value" ></td> -->

									<td class="key">可用内存</td>
									<td class="value"><span subproperty="freeMem" property="resourceInfo">
									</td>
									<td class="key">CPU数目</td>
									<td class="value"><span subproperty="cpuNum" property="resourceInfo">
									</td>
									<td class="key">CPU使用率</td>
									<td class="value"><span subproperty="cpuRate" property="resourceInfo">
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td valign="top" align="left">
						<div class="menu" style="width: 100%; margin-top: 10px;">
							<ul class="domain_tool">
								<li style="font-size: 14px; font-weight: bold;">硬件性能监控</li>
							</ul>
						</div>
						<div
							style="width: 100%;border-right: 0px; border-top: 1px solid #FFF;"
							id="webServerConStyle">
							<table cellspacing="0" cellpadding="0" width="100%"
								align="center" border="0">
								<tbody style="display:inline-block">
									<tr style="height: 170px;">
										<td align="left">
											<div id="speedometer"
												style="min-width: 100px; height: 150px; width: 180px; margin: 0 auto"></div>
										</td>
										<td align="left;width:100%">
											<div id="cpu"
												style="min-width: 100px; height: 150px; width: 350px; margin: 0 auto"></div>
										</td>
										<td rowspan="2">
											<!-- 硬盘使用情况柱状图 -->
											<div id="disk"
												style="width: 450px; height: 300px; margin: auto"></div></td>
									</tr>
									<tr>
										<td align="left">
											<div id="speedometer_1"
												style="min-width: 100px; height: 150px; width: 180px; margin: 0 auto"></div>
										</td>
										<td align="left">
											<div id="memory"
												style="min-width: 100px; height: 150px; width: 350px; margin: 0 auto"></div>
										</td>
									</tr>
								</tbody>
							</table>
						</div></td>
				</tr>"G:/A-weather/硬件监控相关代码/systemMonitor_info.jsp"
			</table>
		</div>

		<div id="statisticsList" width="100%"> 
			<table cellspacing="0" cellpadding="0" width="100%" border="0">
				<tr>
					<td width="90%" height="350px" style="padding:2px;padding-left:0px;">
			            <div id="highchartsColumn" style="display:block;position: absolute;margin-top: 6px; margin-left: 30px; z-index: 2">
			     			<img style="cursor: pointer;" src="images/exit.png" width="16" height="16" onclick="pictgrid('line')" title="切换到曲线图统计" />
			     		</div>
			     		<div id="highchartsLine" style="display:block;position: absolute;margin-top: 2px; margin-left: 60px; z-index: 2">
			     			<img style="cursor: pointer;" src="webfront/images/ico/icon031a1.gif" width="22" height="22" onclick="pictgrid('column')" title="切换到柱状图统计" />
			     		</div>
			     		<div id="highchartsColumn" style="display:block;position: absolute;margin-top: 5px; margin-left: 95px; z-index: 2">
			     			<img style="cursor: pointer;" src="images/exit.png" width="16" height="16" onclick="pictgrid('table')" title="切换到表格统计" />
			     		</div>
					    <!-- 近十小时节点处理数据统计 -->
					    <table id="statisticsTable" class="block_cont1 rightC_syzt rightC_syzt_table" width="100%" height="100%" border="0" cellspacing="2" cellpadding="2">
					    </table>
						<div id="container" style="width: 100%; height:100%;" class="block_cont1"></div>
					</td>
					<td valign="top" width="10%"  height="650px" rowspan="2" style="padding:2px; padding-left:0px; padding-right:0px;" >
						<div class="menu" style="width: 100%; height:30px;">
							<ul class="domain_tool">
								<li style="font-size: 12px; font-weight: bold;">虚拟处理能力池</li>
							</ul>
						</div>
						<div class="block_cont1" style="width: 100%; height:620px; padding:0px;" > 
							<div style="width: 100%;padding-top:10px;padding-bottom:25px">
								<table id="queueInfoCon" cellspacing="0" cellpadding="0"
									style="width: 100%;" class="rightC_syzt rightC_syzt_table1"
									style="padding:0px;">
									<tr>
										<td style="padding-left: 5px; font-family:微软雅黑; font-size:14px; font-weight:bold;">总处理能力</td>
									</tr>
									<tr>
										<td align="center"><span property="totalCount" style="color: blue;font-size:18px; font-weight:bold;"></td>
									</tr>
									<tr>
										<td style="padding-left: 5px; font-family:微软雅黑; font-size:14px; font-weight:bold;">占用</td>
									</tr>
									<tr>
										<td align="center"><span property="usedCount" style="color: red;font-size:18px; font-weight:bold;"></td>
									</tr>
									<tr>
										<td style="padding-left: 5px; font-family:微软雅黑; font-size:14px; font-weight:bold;">空闲</td>
									</tr>
									<tr>
										<td align="center"><span property="freeCout" style="color: green;font-size:18px; font-weight:bold;"></td>
									</tr>
								</table>
							</div>
												<div class="menu" style="width: 100%; height:30px;">
							<ul class="domain_tool">
								<li style="font-size: 12px; font-weight: bold;">节点服务监测</li>
							</ul>
						</div>
							<div id="webServerCon" style="width: 100%; overflow-y:auto; ">
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td style="padding:2px;padding-left:0px;height:300px;">
				    	<div id="statisticsPage" class="gridCon" style="width:100%; height:100%; overflow-y:auto; "></div>
				    </td>
				</tr>
			</table>
		</div>
	</div>
	</div>
	
	<div id="DetailDialog" style="border:6px solid #AFCBE8;position:absolute;display:none;top: 50%;left: 50%;height: 400px;  margin-top: -200px; width: 800px;  margin-left: -400px; z-index:50; ">
           <div style="background:#AFCBE8;font-family:'微软雅黑';font-weight:bold;color:#000000;height:24px;line-height:24px;padding-left:20px;">
             <div style="float:left;">
               <span id="s_event_name"></span>
             </div>
             <div style="float:right;">
             <a href="javascript:"
             	style="background:#E04343;color:#fff;padding:4px 13px;text-decoration:none; font-family:'宋体';font-size:12px;" 
             	onclick="logPanel.close();">×</a>
             </div>
           </div>
		<div id="gridDetailLog" style="width:100%;height:376px;overflow:scroll;background:#000;">
			<ul id="logListItems" style="LIST-STYLE-TYPE: none; MARGIN: 0px; PADDING: 10px; TOP: 5px;background-color:#000000;">
			</ul>
		</div>
	</div>
</html>
