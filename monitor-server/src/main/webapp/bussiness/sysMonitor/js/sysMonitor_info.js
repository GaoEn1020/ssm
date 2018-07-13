var wsdl = "";
var serverTime = null;
var sysData = null;
var sysInfowsd= new Array(); 
var _cpu_val=10, _nc_val=20;

//整个页面的初始化
var page = {
    init : function(){
    	if(!tphId || tphId =="null"){
    		sysMonitor_info.changeTab('B');
    	} else{
    		sysMonitor_info.changeTab('A');    
    	}
    }
}

var sysMonitor_info = {
	changeTab : function(tab_type){
		if(tphType == "1"){
			$("#Hardware_tab").hide();
		}else{
			$("#Hardware_tab").show();
		}
		
		if (tab_type == "A") {
			$("#Hardware_tab").addClass("on");
			$("#Task_tab").removeClass("on");
			$("#webServerList").show();
			$("#statisticsList").hide();
		
			showWebServerListPage();
		}else {
			
			$("#Hardware_tab").removeClass("on");
			$("#Task_tab").addClass("on");
			$("#webServerList").hide();
			$("#statisticsList").show();
			ProcessTaskDataAction.getServerTime(function(time){
    			//获取服务器时间
				serverTime = time.getTime();
              	window.setInterval(function(){ serverTime = serverTime + 1000; },1000);
              	statisticsListShow();   
            });
		} 
	}
}

var reLoadPageWebServer;
function statisticsListShow(){
	//统计图绘制
	var hostIP = [];
	hostIP[0] = tphIp;
	
	BusinessLogAction.statisticsBySchName(null, hostIP, function(data){
		sysData = data;
		//statisticsTable(data);
		pictgrid('line');
    });
	
	//显示节点处理结果信息
	statisticsPage.init();	
    //显示节点服务监控信息
    pageWebServer.init();
 
    window.clearInterval(reLoadPageWebServer);
    reLoadPageWebServer = window.setInterval(function(){statisticsPage.init();	pageWebServer.init(); },10000);
}

function pictgrid(type){
	//判断切换按钮显示不同的数据
	if(type == "table"){
		//显示表格
		statisticsTable(sysData);
		document.getElementById('statisticsTable').style.display = 'block';
		document.getElementById('container').style.display = 'none';
	}else{
		document.getElementById('statisticsTable').style.display = 'none';
		document.getElementById('container').style.display = 'block';
		//显示图标
		var time = sysData['time'];
		var hostIP = sysData['hostIP'];
		var dataMap = sysData['dataMap'];

		if(!hostIP){
			return;
		}
		var count = hostIP.length;
		var datas = new Array();
		for(var i=0;i<count;i++){
			var sIPAddress = hostIP[i];
			var ipAddr = "节点"+sIPAddress.substring(sIPAddress.lastIndexOf('.')+1);    
			datas.push({name: ipAddr,data:dataMap[hostIP[i]]});
		}
		
		$('#container').highcharts({
			chart: {
	            type: type
	            //type: 'column'
	        },
			credits: { 
	            enabled: false   //右下角不显示LOGO 
	        },
	        title: {
	            text: '处理情况实时统计图',
	            x: -20 //center
	        },
	        subtitle: {
	            x: -20
	        },
	        xAxis: {
	        	title: {
	                text: '时间 '
	            },
	        	categories:time      
	        },
	        yAxis: {
	            title: {
	                text: '处理文件数量 '
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: '个'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series:datas
	    });
	}
}

function statisticsTable(data){
	
	//清除table下所有的<tr>
	var tb =document.getElementById("statisticsTable");
	//tb.firstChild.removeServer(true)
	
    var rowNum=tb.rows.length;
    for (i=0;i<rowNum;i++)
    {
        tb.deleteRow(i);
        rowNum=rowNum-1;
        i=i-1;
    }
	
	var time = data['time'];
	var dataMap = data['dataMap'];
	var dataList = dataMap[tphIp];
	var totalCount = 0;
	
	if(!time){
		return;
	}
	
	var tableStr = "<tr><td colspan='2' align='center' style='position: relative;line-height:30px;font-size:16px;' class='table_title'>";
	tableStr = tableStr + "近十小时"+tphIp+"节点处理文件统计</td></tr>";
	tableStr = tableStr + "<tr style='color:#000; font-weight:bold;'><td align='center'>统计时间</td>";
	tableStr = tableStr + "<td style='text-align: center;padding-right: 20px;'>文件数量</td></tr>";
    $("#statisticsTable").append(tableStr);
    
    var count = time.length;
	for(var i=0;i<count;i++){
		var theTime = time[i];
		var dataCount = dataList[i];
		totalCount = totalCount+dataCount;
		
		var statisticsStr="<tr><td align='center'>"+theTime+"<br /></td>" +
		"<td style='text-align: center;padding-right:20px;'>"+dataCount+"</td></tr>";
		$("#statisticsTable").append(statisticsStr);
	}
	
	tableStr = "<tr ><td align='center'style='font-size:12px;color:blue;'><strong><b>总计</b></strong></td>";
	tableStr = tableStr + "<td align='center' style='font-size:12px;color:blue;'><strong id='gobalOfs'></strong></td></tr>";
	$("#statisticsTable").append(tableStr);
	
	$("#gobalOfs").html(totalCount+"");
}

function showWebServerListPage(){
	if(sysInfowsd.length <= 0 ){
		WebServerAction.getSysWsdl(function(data){
	    	if(data){
	    		sysInfowsd = data;
	    		webServer.ShowList();
	    	}
	    });	
	}
}

/************ 主机处理结果 ************/
var current = 0;
var statisticsPage = {
	init:function (){
  	
	  //实例化page组件.例:每页10行,ajax取回数据后写入到id为gridCon的div中,全局名称叫pageStation.grid
      this.grid = new Htjs.grid(8, "statisticsPage", "statisticsPage.grid");
      
//      this.grid.appendCol(new function(){
//          this.title = "主机IP";
//          this.getCol = function(data){
//			return "<img width='16' src='"+context+"/images/computer.png'></img>" + data['hostIp'];	
//          }
//      });
      
      this.grid.appendCol(new function(){
          this.title = "序号";
          this.getCol = function(data){
              return ++current;
          }
      });
      
      this.grid.appendCol(new function(){
          this.title = "任务名称";
          this.col="schedulerName";
          this.getCol = function(data){
              return data["schedulerName"];
          }
      });
       
      this.grid.appendCol(new function(){
      	this.isSort = true;
          this.col="type";
          this.title = "处理类型";
          this.isSort = true;
          this.getCol = function(data){
        	  if(data["type"] == "projection"){
          		actionType = "投影";
          	}else if(data["type"] == "mosaic"){
          		actionType = "拼接";
          	}else if(data["type"] == "block_005"){
          		actionType = "五度分幅";
          	}else if(data["type"] == "block_010"){
          		actionType = "十度分幅";	
          	}else if(data["type"] == "block_BHW"){
          		actionType = "渤海湾分幅";	
          	}else if(data["type"] == "block_ZDSY"){
          		actionType = "重点水域分幅";	
          	}else if(data["schedulerType"] == "model"){
        		actionType = "模型";	
     	   }else{
          		actionType = "自定义分幅";	
          	}
          	
          	return actionType;
          }
      });
      
/*      this.grid.appendCol(new function(){
      	  this.isSort = true;
          this.col="beginTime";
          this.title = "开始时间";
          this.getCol = function(data){
          	var zt = data["stauts"];
          	if(zt==2){
          		return "";
          	}
          	if(!data["beginTime"]){
          		return "";
          	}
              return data["beginTime"].format("hh:mm");
          }
      });
      
      this.grid.appendCol(new function(){
      	  this.isSort = true;
          this.col="beginTime";
          this.title = "用时";
          this.getCol = function(data){
        	 
          	var zt = data["stauts"];
          	if(zt==2){
          		return "";
          	}
          	if(!data["beginTime"]){
          		return "";
          	}
       
          	var beginTime = data["beginTime"].getTime();
          	var endTime = null;
          	
          	if(zt==3){
          		endTime = serverTime;
          	} else {
          		endTime = data["endTime"]?data["endTime"].getTime():null;
          	}
          	
          	if(endTime == null){
          		return "";
          	}
          	
          	var leave = (endTime - beginTime);
          	var day = Math.floor(leave /(1000 * 60 * 60 * 24));
          	var hour = Math.floor(leave/(1000*60*60)) - (day * 24);   
      		var minute = Math.floor(leave/(1000*60)) - (day * 24 *60) - (hour * 60) ; 
      		var second = Math.floor(leave/1000) - (day * 24 *60*60) - (hour * 60 * 60) - (minute*60);
      		
      		if(hour > 0){
      			return hour+"时"+minute+"分"+second+"秒";
      		}else if(minute > 0){
      			return minute+"分"+second+"秒";
      		}else{
      			return second+"秒";
      		}
          }
      });*/

//      this.grid.appendCol(new function(){
//          this.title = "结束时间";
//          this.getCol = function(data){
//          	if(data["endTime"]){
//          		return data["endTime"].format("hh:mm");
//          	}else{
//          		return "";
//          	}
//          }
//      });
      
      this.grid.appendCol(new function(){
      	this.isSort = true;
          this.col="beginTime";
          this.title = "处理时间";
          this.getCol = function(data){
          	var zt = data["zt"];
          	if(zt==2){
          		return "";
          	}
          	if(!data["handleTime"]){
          		return "";
          	}
              return data["handleTime"].format("yyyy-MM-dd hh:mm:ss");
          }
      });
      
      
      this.grid.appendCol(new function(){
      	  this.isSort = true;
          this.col="stauts";
          this.title = "状态";
          this.getCol = function(data){
          	var zt = data["zt"];
          	var imgname = "";
          	var status = "";
          	if(zt==0){
          		imgname = "box_grey.png";
          		status = "失败";
          	}else if(zt==1){
          		imgname = "box_green.png";
          		status = "成功";
          	}else if(zt==2){
          		imgname = "box_blue.png";
          		status = "队列中";
          	}else if(zt==3){
          		imgname = "box_run.gif";
          		status = "执行中";
          	}
          	if(status == "执行中"){
          		 return "<img width='20' align='absmiddle' title = '"+status+"'src='"+context+"/images/ico/"+imgname+"'/>" + status;
          	}else{
          		 return "<img width='18' align='absmiddle' title = '"+status+"'src='"+context+"/images/ico/"+imgname+"'/>" + status;
          	}
          }
      });
      this.grid.appendCol(new function(){
          this.title = "数据名称";
          this.isSort = true;
          this.col="fileName";
          this.getCol = function(data){
          	var fileName = data["fileName"];
          	var sdes = "";
          	if(fileName){
          		sdes =CommonUtil.viewStr(fileName,10);
          	}
				return "<img width='16' src='"+context+"/images/ico/default.icon.gif'></img><span title='"+fileName+"'>"+sdes+"</span>";	
          }
      });
      
  	 this.grid.appendCol(new function(){
           this.title = "操作";
           this.getCol = function(data){
           	var html = "<img width='18' align='absmiddle' src='"+context+"/images/ico/report_check.png'/><a href='javascript:void(0)' onclick='logDetailDialogShow(\""+data["logPath"]+"\",\""+actionType+"\",\""+data["dataName"]+"\")'>日志</a>";
               return html;
           }
       }); 
      
      this.grid.load = function(currentPage, pageSize){
    	  current = (currentPage-1)*pageSize;
    	  var thisObj = this;
    	    var filter = {"hostIp":tphIp};

            BusinessLogAction.listAll(currentPage,pageSize,filter, function(data){
            		
            		thisObj.loadReply(data);
            
            });
    	  
      };
      //创建页面信息列表
      this.grid.create();
      //设置页面分页
//      this.grid.setNumBar("gridbar");
   }
}

function logDetailDialogShow(logPath, actionType, dataName){
	
	document.getElementById('DetailDialog').style.display = 'block';
	var name = "事件【"+actionType+"】数据【"+dataName+"】";
	document.getElementById("s_event_name").innerHTML = name;
	logPanel.init(logPath);
}

var logPanel = {
		panelItemDom : null,
		eventID : null,
		lastCount : 0,
		init : function(logPath){
			this.logPath = logPath;
			this.panelItemDom = document.getElementById("logListItems");
			this.panelItemDom.innerHTML = "";
			this.load();
		    //autoReloadHandler = window.setInterval(function(){logPanel.load();}, 3000);
		},
		load : function(){
			//dwr.engine.setAsync(false);//设置dwr同步
			//弹出框显示的日志信息
			ProcessTaskDataAction.listLoggerInfo(this.logPath, function(data){
				var thisCount = parseInt(data['all']);
				
				if( thisCount != 0){
					logPanel.panelItemDom.innerHTML = "";
					logPanel.append(data);
				}
			});	
			//dwr.engine.setAsync(true);//设置dwr异步
		},
		append : function(data){
			if(data){
				var dataArray = data['list'];
				var count = dataArray.length;
				for(var i = 0; i<count; i++){
					var d = dataArray[i];
					$(this.panelItemDom).append('<li class="liclass">'+d+'</li>');
				}
				if(count<1){
					this.showServertial();
				}else{
					this.scrollBottom();
				}
			}else{
				this.showServertial();
			}
			
		},
		showServertial : function(){
			this.panelItemDom.innerHTML = 
				('<li class="liclass">无详细日志信息</li>');
		},
		scrollBottom: function(){
			var div = document.getElementById('gridDetailLog');
			div.scrollTop = div.scrollHeight;
		},
	    close: function(){
	    	document.getElementById('DetailDialog').style.display='none';
	    	//window.clearInterval(autoReloadHandler);
	    }
	}

/**************** web服务信息管理 *****************/
function showSystemInfo(){
	var systemInfo;
	var list = parent.SERVER_LIST;
	if(!list || list.length <= 0){
        var filter = {"ip" : tphIp};
		ProcessServerAction.list(-1, -1, filter, function(data){
			 laodSystemInfo(data['list']);
		});
	}else{
		laodSystemInfo(list);
	}
	
}

function laodSystemInfo(list){
	if(list && list.length > 0){
		for(var i=0;i<list.length;i++){
			var obj = list[i];
			if(obj && obj['ip'] == tphIp){
				systemInfo = obj;
				break;
			}
		}

		//console.log("data.tphIp="+tphIp);
		var webServers = systemInfo['webServers'];
		//请求服务器信息地址："http://localhost:8080/monitor-server/processServer/getResourceInfo.do"
		//ProcessServerAction.getResourceInfo(webServers, function(data){
		$.ajax({
			url:"http://localhost:8080/monitor-server/processServer/getResourceInfo.do",
			type:"get",
			cache:false,
			data:{},
			success:function(data){
				systemInfo['resourceInfo'] = data;
				//console.log("data.osVersion="+systemInfo["resourceInfo"]["osVersion"]);
				CommonUtil.setDetailObject("systemInfo_tab", systemInfo);
				if(!data){
					$("#server_status").html('异常');
				}else{
					$("#server_status").html(systemInfo['status']==1?'正常':'停用');
				}
			}
		});
	}
}

var pageWebServer = {
  init:function (){
	  var filter = {"processServer.ip":tphIp};
	  
	  //当前节点队列信息
	  ProcessServerAction.getQueueStatisticsInfo(tphIp, function(data){
		  if(data){
			  CommonUtil.setDetailObject("queueInfoCon", data);
		  }
	  });
	  
      //调用dwr查询页面数据信息
      WebServerAction.list(-1, -1, filter, function(data){
          if(data && data.list){
        	  var obj;
        	  var imgHtml = "";
        	  var imgTip = "";
        	  var tableHtml = "<table style='width:100%;'><tr align='center'>";
        	  var webServer_html = $("#webServerCon");
        	  var list = data.list;
        	  //noActivated.png  400
        	  //cluster.png
        	  //Abnormal.gif 0
        	  //business/ProcessServer/img/Normal.gif 1
        	  for(var i =0; i<list.length; i++){
        		  obj = list[i];
        		  var infImg = "";
        		  var infName =  obj['interfaceName'];
        		  if(infName == "monitor"){
        			  infImg = "bzh.png";
        		  }else if(infName == "standard"){
        			  infImg = "sfk.png";
        		  }else if(infName == "SysInfo"){
        			  infImg = "yjjk.png";
        		  }else{
        			  infImg = "dt.png";
        		  }
        		  
        		  if(i!=0 && i%1==0){
        			  tableHtml +="</tr><tr align='center'>"
        		  }
        		  
        		  if(obj['status']==400){
        			  imgTip = "停止";
        			  imgHtml = "noActivated.png";
        		  }else if(obj['status']==1){
        			  imgTip = "正常";
        			  imgHtml = "Normal.gif";
        		  } else{
        			  imgTip = "异常";
        			  imgHtml = "Abnormal.gif"
        		  }
        		  
        		  var ip = obj['processServer'].ip;
        		  var webUrl = MAS2_MQ_BASEURL + "?JMSDestination="+ip+":"+obj['wsdlUrl'];
        		  tableHtml += "<td><img style='width:75px; heigth:75px;cursor:pointer;' alt='"+obj['wsdlUrl']+"' src='"+context+"/images/ico/"+infImg+"' onclick='pageWebServer.windowOpen(\""+webUrl+"\")'></img><br>";
        		  tableHtml += "<span style='vertical-align:middle;'><img style='width:18px; heigth:18px;' alt='"+imgTip+"' src='"+context+"/business/sysMonitor/img/"+imgHtml+"'></img></span><br>"+obj['name'];
        		  tableHtml += "</td>";
        	  }
        	  
        	  tableHtml += "</tr></table>";
        	  
        	  webServer_html.html(tableHtml);
          }
      });
  },windowOpen:function(wsdlURL){
	 // wsdlURL = wsdlURL + "?wsdl";
	  window.open(wsdlURL, "_blank", "height=730, width=1000, toolbar= no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes,top=100,left=200");
  }
}

var reLoadInterval;
var webServer = {
	ShowList:function(){
		for(var i = 0 ;i<sysInfowsd.length;i++){
		    var tempIp = sysInfowsd[i].processServer.ip;
			if(tempIp == tphIp){
				wsdl= tempIp + ":" + sysInfowsd[i].wsdlUrl;
				break;
			}
		}
		 
		//显示系统信息  每10秒刷新一次
		window.clearInterval(reLoadInterval);
		showSystemInfo();
		reLoadInterval = window.setInterval(function(){ showSystemInfo(); },10000);
		 
		//显示CPU仪表盘
		webServer.speedometer("speedometer",10);
		 
		//显示CPU仪表盘
	    webServer.speedometer_1("speedometer_1",10);
		
		SysMonitor.getSystemInfo("getCpuInfo",wsdl,function(retV){ 
			//显示CPU曲线图
			webServer.cpuColumn("cpu", wsdl, retV);
        });
		SysMonitor.getSystemInfo("getMemoryInfo",wsdl,function(retV){ 
			//显示内存曲线图
			webServer.sysInfo("memory", wsdl, retV);
        });

		SysMonitor.getSystemInfo("testFileSystemInfo",wsdl,function(retV){
    		//显示柱状图
	    	webServer.diskColumn(retV);
    	});
		
	},
	sysInfo:function(name, wsdl, mSize){
		Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
		$('#'+name).highcharts({
            chart: {
                zoomType: 'x',
                events: {
                    load: function() {
                        // set up the updating of the chart each second
                        var series = this.series[0];
                        var title = this.title;
                       
                        setInterval(function() {
                        	SysMonitor.getSystemInfo("getMemory",wsdl,function(retV){
                        		var x = (new Date()).getTime(); // current time
 	                            var y = parseFloat(retV);
 	                            _nc_val = Math.round(y);
 	                            series.addPoint([x, y], true, true);
                	    	});
                        }, 1000);
                    }
                }
            },
            title: {
//                text: '<font style="font-size:12px;">内存(%)&nbsp;&nbsp;&nbsp;&nbsp;'+mSize+'</font>'
            	text: '<font style="font-size:12px;">内存(%)    '+mSize+'</font>'
            },
          credits:{
		    	enabled:false
		    },
            xAxis: {
            	type: 'datetime',
                tickPixelInterval: 80
            },
            yAxis: {
            	min: 0,
 		        max: 100,
            	title: {text: '内存(%)' },
                plotLines: [{
                  value: 0,
                  width: 1,
                  color: '#808080'
              }]  
            },
            legend: {
                enabled: false
            },
            tooltip: {
                formatter: function() {
                      return Highcharts.dateFormat('%H:%M:%S', this.x) +'<br/> '+ this.series.name +':'+
                      Highcharts.numberFormat(this.y, 2);
               }
            },
            plotOptions: {
                area: {
                    fillColor: {
                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                        stops: [
                            [0, Highcharts.getOptions().colors[1]],
                            [1, Highcharts.Color(Highcharts.getOptions().colors[1]).setOpacity(0).get('rgba')]
                        ]
                    },
                    marker: {
                        radius: 0
                    },
                    lineWidth: 0,
                    states: {
                        hover: {
                            lineWidth: 2
                        }
                    },
                    threshold: null
                }
            },
            series: [{
                type: 'area',
                name: '利用率',
                data: (function() {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;
					
                for (i = -60; i <= 0; i++) {
                    data.push({
                        x: time + i*1000,
                        y: 0
                    });
                }
                return data;
            })()
            }]
        });
	},
	cpuColumn:function(name,wsdl, chText){
		
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
        $('#'+name).highcharts({
            chart: {
                zoomType: 'x',
                events: {
                load: function() {
                    // set up the updating of the chart each second
                    var series = this.series[0];
                    setInterval(function() {
                    	SysMonitor.getSystemInfo("testCpuPerc",wsdl,function(retV){
                  		   var x = (new Date()).getTime(); // current time
                           var y = parseFloat(retV);
                           _cpu_val = Math.round(y);
                           series.addPoint([x,y], true, true);
                    	});
                      }, 1000);
                   }
                }
            },
            title: {
                text: '<font style="font-size:12px;">'+chText+'</font>'
            },
          credits:{
		    	enabled:false
		    },
            xAxis: {
            	type: 'datetime',
                tickPixelInterval: 80
            },
            yAxis: {
            	min: 0,
 		        max: 100,
            	title: {text: 'CPU(%)' },
                plotLines: [{
                  value: 0,
                  width: 1,
                  color: '#808080'
              }]  
            },
            legend: {
                enabled: false
            },
            tooltip: {
                formatter: function() {
                      return Highcharts.dateFormat('%H:%M:%S', this.x) +'<br/> '+ this.series.name +':'+
                      Highcharts.numberFormat(this.y, 2);
               }
            },
            plotOptions: {
                area: {
                    fillColor: {
                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                        stops: [
                            [0, Highcharts.getOptions().colors[0]],
                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                        ]
                    },
                    marker: {
                        radius: 0
                    },
                    lineWidth: 0,
                    states: {
                        hover: {
                            lineWidth: 2
                        }
                    },
                    threshold: null
                }
            },
            series: [{
                type: 'area',
                name: '利用率',
                data: (function() {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;
					
                for (i = -60; i <= 0; i++) {
                    data.push({
                        x: time + i*1000,
                        y: 0
                    });
                }
                return data;
            })()
            }]
        });
	},
	speedometer:function(name,value){
	    $('#'+name+'').highcharts({
		    chart: {
		        type: 'gauge',
		        plotBackgroundColor: null,
		        plotBackgroundImage: null,
		        plotBorderWidth: 0,
		        plotShadow: false,
		        events: {
                    load: function() {
                        var series = this.series[0];
                        setInterval(function() {
                        	series.addPoint(_cpu_val, true, true);
                        }, 3000);
                    }
                }
		    },
		    
		    title: {
		        text: ''
		    },
		    credits:{
		    	enabled:false
		    },
		    pane: {
		        startAngle: -150,
		        endAngle: 150,
		        background: [{
		            backgroundColor: {
		                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
		                stops: [
		                    [0, '#FFF'],
		                    [1, '#333']
		                ]
		            },
		            borderWidth: 0,
		            outerRadius: '50%'
		        }, {
		            backgroundColor: {
		                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
		                stops: [
		                    [0, '#333'],
		                    [1, '#FFF']
		                ]
		            },
		            borderWidth: 1,
		            outerRadius: '50%'
		        }, {
		            // default background
		        }, {
		            backgroundColor: '#DDD',
		            borderWidth: 0,
		            outerRadius: '50%',
		            innerRadius: '50%'
		        }]
		    },
		       
		    // the value axis
		    yAxis: {
		        min: 0,
		        max: 100,
		        
		        minorTickInterval: 'auto',
		        minorTickWidth: 1,
		        minorTickLength: 10,
		        minorTickPosition: 'inside',
		        minorTickColor: '#666',
		
		        tickPixelInterval: 30,
		        tickWidth: 2,
		        tickPosition: 'inside',
		        tickLength: 10,
		        tickColor: '#666',
		        labels: {
		            step: 2,
		            rotation: 'auto'
		        },
		        title: {
		        	y:-35,
		            text: 'CPU(%)'
		        },
		        plotBands: [{
		            from: 0,
		            to: 60,
		            color: '#55BF3B' // green
		        }, {
		            from: 60,
		            to: 80,
		            color: '#DDDF0D' // yellow
		        }, {
		            from: 80,
		            to: 100,
		            color: '#DF5353' // red
		        }]        
		    },
		
		    series: [{
		        name: '当前',
		        data: [value],
		        tooltip: {
		            valueSuffix: ' 使用率:%'
		        }
		    }]
		
		} 
		// Add some life
//			function (chart) {
//				if (!chart.renderer.forExport) {
//				    setInterval(function () {
//				        var point = chart.series[0].points[0],
//				            newVal,
//				            inc = Math.round(this.temporary);
//				        
//				        newVal = inc;
//						
//				        
//				        point.update(newVal);
//				    }, 4000);
//				}
//			}
		);
		
	},
	speedometer_1:function(name,value){
	    $('#'+name+'').highcharts({
		    chart: {
		        type: 'gauge',
		        plotBackgroundColor: null,
		        plotBackgroundImage: null,
		        plotBorderWidth: 0,
		        plotShadow: false,
		        events: {
                    load: function() {
                        var series = this.series[0];
                        setInterval(function() {
                        	series.addPoint(_nc_val, true, true);
                        }, 3000);
                    }
                }
		    },
		    
		    title: {
		        text: ''
		    },
		    credits:{
		    	enabled:false
		    },
		    pane: {
		        startAngle: -150,
		        endAngle: 150,
		        background: [{
		            backgroundColor: {
		                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
		                stops: [
		                    [0, '#FFF'],
		                    [1, '#333']
		                ]
		            },
		            borderWidth: 0,
		            outerRadius: '50%'
		        }, {
		            backgroundColor: {
		                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
		                stops: [
		                    [0, '#333'],
		                    [1, '#FFF']
		                ]
		            },
		            borderWidth: 1,
		            outerRadius: '50%'
		        }, {
		            // default background
		        }, {
		            backgroundColor: '#DDD',
		            borderWidth: 0,
		            outerRadius: '50%',
		            innerRadius: '50%'
		        }]
		    },
		       
		    // the value axis
		    yAxis: {
		        min: 0,
		        max: 100,
		        
		        minorTickInterval: 'auto',
		        minorTickWidth: 1,
		        minorTickLength: 10,
		        minorTickPosition: 'inside',
		        minorTickColor: '#666',
		
		        tickPixelInterval: 30,
		        tickWidth: 2,
		        tickPosition: 'inside',
		        tickLength: 10,
		        tickColor: '#666',
		        labels: {
		            step: 2,
		            rotation: 'auto'
		        },
		        title: {
		        	y:-35,
		            text: '内存(%)'
		        },
		        plotBands: [{
		            from: 0,
		            to: 60,
		            color: '#55BF3B' // green
		        }, {
		            from: 60,
		            to: 80,
		            color: '#DDDF0D' // yellow
		        }, {
		            from: 80,
		            to: 100,
		            color: '#DF5353' // red
		        }]        
		    },
		
		    series: [{
		        name: '当前',
		        data: [value],
		        tooltip: {
		            valueSuffix: ' 使用率:%'
		        }
		    }]
		
		});
	},
	diskColumn:function(retV){
		eval("var json = " + retV);
		
		var disk = json[0];
		var used = json[1];
		var idel = json[2];
			 
		$('#disk').highcharts({
            chart: {
                type: 'column'
            },
            title: {
            	text: '<font style="font-size:12px;">硬盘使用情况</font>'
            },
            xAxis: {
                categories: disk
            },
            yAxis: {
                title: {
                    text: '单位:GB'
                },
                stackLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            legend: {
//                align: 'right',
//                x: -70,
//                verticalAlign: 'top',
//                y: 20,
//                floating: true,
//                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
//                borderColor: '#CCC',
//                borderWidth: 1,
//                shadow: false
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                        this.series.name +': '+ this.y +'<br/>'+
                        'Total: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                        style: {
                            textShadow: '0 0 3px black, 0 0 3px black'
                        }
                    }
                }
            },
		    credits: {
		        enabled: false
		    },
            series: [{
            	name: '可用空间',
                data: idel
            }, {
            	 name: '已用空间',
                 data: used
            }]
        });
	}
};

/*hdht.onReady(page.init, page, true);*/
window.onReady(page.init, page, true);