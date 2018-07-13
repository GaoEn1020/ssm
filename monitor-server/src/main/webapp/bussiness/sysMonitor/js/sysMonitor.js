
var SERVER_LIST={};
var CURR_SERVER_ID;
var autoReloadHandler;
//整个页面的初始化
var page = {
    init : function(){
    	this.load(true);
    	autoReloadHandler = window.setInterval(function(){ 
    		page.load(false);
    	},10000);
    	var dirt = document.getElementById("dir_tree");
	    dirt.style.height = parent.if_height;
	    var iframe = document.getElementById("iframe_content");
//	    iframe.style.height = parent.if_height;
	    iframe.style.height = 637;
	/*    if (document.documentElement.clientWeight<=1312) {
	    	iframe.style.height = 937;
	    	console.log(parent.if_height);
		}*/
//	    console.log(parent.if_height)
    	
    },load : function(flag){
    	//需要查询的字段
        var filter = Htjs.form.initSearchObject({hostName:"hostName",
												ip:"ip",
												systemName:"systemName",
												status:"status"});
    	 //调用dwr查询页面数据信息
		 ProcessServerAction.listMonitor(-1, -1, filter, function(data){
			 if(data){
				 SERVER_LIST=data['list'];
				 
				 var length = SERVER_LIST.length;
				 $("#server_div").html('');
				 //将提示框的字段显示数据设置为空
				 
				 
			     //add_server_info();
			     
			     
				 for(var i=0; i< length; i++){
					 var processServer = SERVER_LIST[i];
					 show_server_list('server_div', processServer,i);
				 }
				 
				 show_server_info();
				 
				 if(flag){
				     $("#0_div").find("img").click();
				 }
			  }
		 });
    }
}

//显示服务器列表
  function show_server_list(parent_div,server_info,index){
	if(server_info){
		//将"."替换为"_"
		var server_id = server_info['id']; 
		var server_ip = server_info['ip']; 
		var server_type = server_info['classify']; 
		var webServers = server_info['webServers'];
		var status = server_info['status'];
//		var status_title = (status == 2)?'停用':'正常';
//		var status_img=(status == 2)?'/images/no.png':'/business/sysMonitor/img/Normal.gif';
//		console.log(server_ip+"=ip="+status);
		if(status == 1){// 正常
			var status_title = "正常";
			var status_img="/business/sysMonitor/img/Normal.gif";
		}else if(status == 2){// 停用
			var status_title = "停用";
			var status_img="/images/no.png";
		}else{// 异常
			var status_title = "异常";
			var status_img="/business/sysMonitor/img/Abnormal.gif";
		}
 
		var elId = server_id + "_" + server_ip + "_" + server_type;
		var html=''+
		'<div id="'+index+'_div" class="server_list" style="height: 140px; width: 98px; float: left; border: 1px solid rgb(255,255,255);margin:5px;">'+
		'<ul style="padding:0 0 0 10">'+
		'<li>'+
		'<img id="'+elId+'_img"  src="'+context+'/images/ico/jq.png" class="server_img" style="cursor: pointer; width:80px;height: 86px;"></img>'+
		'</li>'+
		'<li style="line-height:20px; font-weight: bold; text-align:center"><span id="status_'+server_id+'"><img style="width:20px;" title="'+status_title+'" src="'+ context + status_img+'"></img></span></li>'+
		'<li style="line-height:20px; font-weight: bold; text-align:center"><span>'+server_info['hostName']+'&nbsp;</span></li>'+
		'</ul>'+
		'</div>';
		
		$("#"+parent_div+"").append(html);
		
		if(server_id == CURR_SERVER_ID){
			$(document.getElementById(elId+"_img")).css('background-color', '#FFFFAA');
			$(document.getElementById(elId+"_img")).css('border','2px solid #CCC');
		}
		
		//测试WSDL，异步获取服务器状态
		var webServers = server_info['webServers'];
		var processServerID = server_info['id'];
		judgeServerStatus(webServers,processServerID);
			
	}
}

function judgeServerStatus(webServers,processServerID){
	// 配置文件修改，关联关系消失 webServers = null
	//console.log(webServers);
	ProcessServerAction.getResourceInfo(webServers, function(data){
		//console.log(processServerID+",data="+data);
		var status_dom = document.getElementById("status_" + processServerID);
		if(!data){
			//console.log("data=异常");
			status_dom.innerHTML ='<img title="异常" style="width:20px;" align="absmiddle" src="'+ context +'/business/sysMonitor/img/Abnormal.gif">&nbsp;异常';
		}	
	});
}

//显示服务器信息
function show_server_info(){

	$('.server_img').on({
		
		click:function(e){
			$(".server_img").css('background-color','#FFFFFF');
			$(".server_img").css('border','2px solid #E0E0E0');
			var current_div =$(e.target).attr("id");
			
			$(document.getElementById(current_div)).css('background-color', '#FFFFAA');
			$(document.getElementById(current_div)).css('border','2px solid #CCC');
			var param = current_div.split("_");
			CURR_SERVER_ID = param[0];
			$("#iframe_content").attr('src','systemMonitor_info.jsp?tphId='+param[0]+'&tphIp='+param[1]+'&tphType='+param[2]);
		}
	});
	
	var curr_index;
	$('.server_list').on({
		
	     mouseover: function() {
	    	 $("#server_info").html("<div align=\"center\" style=\"padding-top:10px;\"><img width=\"30px\" src=\""+ context+"/images/loading.gif\"></div>");
	    	 $("#server_info").show();
	    	 var current_div =$(this).attr("id");
	    	 //$("#server_info").html(' ');
	    	 var tempIndex = current_div.split('_')[0];
	    	 if(tempIndex == curr_index){
	    		 return;
	    	 }
	    	 curr_index = tempIndex;
	         add_server_info(SERVER_LIST[curr_index]);
	    	 
	         var X = $('#'+current_div).offset().top;
	         var Y = $('#'+current_div).offset().left;
	         $("#server_info").offset({top:X,left:Y+111});
	         
	     },
	     mouseleave: function(){
	    	 curr_index = -1;
	    	 $("#server_info").html("<div align=\"center\" style=\"padding-top:10px;\"><img width=\"40px\" src=\""+ context+"/images/loading.gif\"></div>");
	         $("#server_info").hide();
	     }
	 });
	
}
var count = 0;
function add_server_info(server_info){
	var webServers = server_info['webServers'];
	ProcessServerAction.getResourceInfo(webServers, function(data){
		var hostName,ip,status,os,system,totalMem,usedMem,cpuNum,cpuRate;
		hostName = data?data['hostName']:"";
		ip=server_info['ip'];
		if(!data){
			status = "异常";
		}else{
			status = server_info['status']==1?"正常":"停用";
		}
		os=data ? data['os']: "";
		system=data ? data['system']: "";
		totalMem=data ? data['totalMem']: "";
		usedMem=data ? data['usedMem']: "";
		cpuNum=data ? data['cpuNum']: "";
		cpuRate=data ? data['cpuRate']: "";
		 
		var html=''+
		'<div style="padding: 5px;">'+
		'<ul>'+
		'<li class="server_info"><span style="width:70px;text-align:right;">服务器名:</span><span style="color:blue">'+hostName+'</span></li>'+
		'<li class="server_info"><span style="width:70px;text-align:right;">IP地址:</span><span style="color:blue">'+ip+'</span></li>'+
		'<li class="server_info"><span style="width:70px;text-align:right;">服务状态:</span><span style="color:blue">'+status+'</span></li>'+
		'<li class="server_info"><span style="width:70px;text-align:right;">操作系统:</span><span style="color:blue">'+os+'</span></li>'+
		'<li class="server_info"><span style="width:70px;text-align:right;">系统类型:</span><span style="color:blue">'+system+'</span></li>'+
		'<li class="server_info"><span style="width:70px;text-align:right;">总内存:</span><span style="color:blue">'+totalMem+'</span></li>'+
		'<li class="server_info"><span style="width:70px;text-align:right;">已用内存:</span><span style="color:blue">'+usedMem+'</span></li>'+
		'<li class="server_info"><span style="width:70px;text-align:right;">CPU数目:</span><span style="color:blue">'+cpuNum+'</span></li>'+
		'<li class="server_info"><span style="width:70px;text-align:right;">CPU使用率:</span><span style="color:blue">'+cpuRate+'</span></li>'+
		'</ul>'+
		'</div>';
		
		$("#server_info").html(html);
	});
}


function showIcon(index){
	if(index == 1){
		return '/images/ico/dd.png';
	}else if(index == 2){
		return '/images/ico/db.png';
	}else if(index == 3){
		return '/images/ico/dt.png';
	}else if(index == 4){
		return '/images/ico/sj.png';
	}else if(index == 5){
		return '/images/ico/sc.png';
	}else if(index == 6){
		return '/images/ico/jq.png';
	}else if(index == 7){
		return '/images/ico/qt.png';
	}
}
var stateArray = new Array();	//状态数组

window.setTimeout(function(){
	WebServerAction.isConnect(stateArray);
}, 3000); 

var IntervalMap = setInterval("getSmapInterval()",5000);//1000为1秒钟

function getSmapInterval(){
	var i = 0;
	WebServerAction.getStateMap(function(data){
		for ( var key in data) {
			i++;
			if(data[key]==true){
				document.getElementById("Activation_"+key+"").setAttribute("src","img/Normal.gif");
			}else{
				document.getElementById("Activation_"+key+"").setAttribute("src","img/Abnormal.gif");
			}
		}
		if(i == stateArray.length){
			clearInterval(IntervalMap);
		}
	});
}


//监测站的内容
var pageStation = {
    init: function(){
		//实例化page组件.例:每页12行,ajax取回数据后写入到id为gridCon的div中,全局名称叫pageStation.grid
        this.grid = new Htjs.grid(12, "Con", "pageStation.grid");
        //添加列。例:列名"名称",支持排序
        this.grid.appendCol(new function(){
            this.title = "节点名称";
            this.isSort = true;
            this.col="hostName";
            this.getCol = function(data){
            	var classify = data['classify'];
            	var imgPath = context + showIcon(classify);
                var html = "<img width='35' align='absmiddle' src='"+imgPath+"'/>"+data['hostName'];
                return html;
            }
        });
        this.grid.appendCol(new function(){
            this.title = "节点IP";
            this.isSort = true;
            this.col="ip";
            this.getCol = function(data){
            	var html = data['ip'];
            	stateArray.push(html);
                return html;
            }
        });
        this.grid.appendCol(new function(){
            this.title = "操作系统";
            this.col="systemName";
            this.getCol = function(data){
            	var html = data['systemName'];
                return html;
            }
        });
        this.grid.appendCol(new function(){
            this.title = "系统版本";
            this.isSort = true;
            this.col="systemVersion";
            this.getCol = function(data){
            	var html = data['systemVersion'];
                return html;
            }
        });
        this.grid.appendCol(new function(){
            this.title = "分类";
            this.col="classify";
            this.getCol = function(data){
            	var classify = data['classify'];
            	if(classify == 1){
            		return '运行调度服务器';
            	}else if(classify == 2){
            		return '数据库服务器';
            	}else if(classify == 3){
            		return '地图服务器';
            	}else if(classify == 4){
            		return '数据收集服务器';
            	}else if(classify == 5){
            		return '产品自动生产服务器';
            	}else if(classify == 6){
            		return '数据处理服务器集群';
            	}else if(classify == 7){
            		return '其他';
            	}
            }
        });
        this.grid.appendCol(new function(){
            this.title = "节点状态";
            this.isSort = true;
            this.col="status";
            this.getCol = function(data){
//            	var status = data['status'];
//            	if(status == 1){
//            		return '正常';
//            	}else{
//            		return '异常';
//            	}
            	var ip = data['ip'];
            	var html = '<img id="Activation_'+ip+'" class="ws_state" width="21px" height:"21px"  src="img/noActivated.png" />';
            	return html;
            }
        });
        
        this.grid.appendCol(new function(){
            this.title = "操作";
            this.objectIndex = 0;
            this.getCol = function(data){
            	
            	var commandStr = Htjs.BuildCommand({
            		/*
					"update":{
						name:"修改",eventType:"onclick",
						command:'UpdateDialogShow(\'' +data["id"] +'\',this)'
					},
					"delete":{
						name:"删除",eventType:"onclick",
						command:'DeleteObj.deleteObj(\'' +data["id"] +'\')'
					},*/
					"pic":{
						name:"硬件性能监控信息",eventType:"onclick",
						
						command:'showSysMonitorInfo(\'' +data["id"] +'\',\'' +data["ip"] +'\')'
					}
				});
				return commandStr ;
            }
        });
        
        this.grid.load = function(currentPage, pageSize){
            var thisObj = this;
            //需要查询的字段
            var filter = Htjs.form.initSearchObject({hostName:"hostName",
													ip:"ip",
													systemName:"systemName",
													status:"status"});
           // filter['abc']="sdfsdf";
							  
            //调用dwr查询页面数据信息
            ProcessServerAction.list(currentPage, pageSize, filter, function(data){
                thisObj.loadReply(data);
            });
            
        };
        //创建页面信息列表
        this.grid.create();
        //设置页面分页
        this.grid.setNumBar("barCon1");
    }
}
//导出数据
function ExportExlData(obj){
	var exp_config = {
		exportKey:"Station",//数据表映射对象名
		columnList:[
			{columKey:"id",columShowName:"主键"},//对应的列名和列显示名
			{columKey:"address",columShowName:"地址"},
			{columKey:"principal",columShowName:"负责人"},
			{columKey:"monitoredTarget",columShowName:"检测对象"},
			{columKey:"managerDepart.mc",columShowName:"管理部门"},
			{columKey:"tel",columShowName:"联系电话"}
		]
	}
	ExportData.showExecte(obj, "exportDialog", exp_config);
}

/**
 * HdhtDialog使用范例说明
   HdhtDialog.config={
   		--弹出框类型(详细页面-HDHT_DIALOG_DETAIL_TYPE、
   				     添加页面-HDHT_DIALOG_ADD_TYPE、
   				     修改页面-HDHT_DIALOG_UPDATE_TYPE)
   		type:HDHT_DIALOG_DETAIL_TYPE,
		--内容部分DIV的ID
		divid:"StationDetailDialog",
		--EXT弹出框原始参数
		dialogconfig:{
			autoTabs:true,shadow:true,
			width:750,height:455,
			minWidth:400,minHeigth:350,
			proxyDrag:true,modal:true
		},
		--数据加载方法-窗体加载初始数据据时调用
		dialogInitFunc:function(dialog){
		},
		--数据存储方法-修改、添加按钮的事件定义
		buttons:{
			"修改":function(dialog){
			}
		}
	}
 */
function DetailDialogShow(id,name,elem){
	Htjs.dialog.config={
		divid:"StationDetailDialog",
		dialogconfig:{
			autoTabs:true,shadow:true,
			width:750,height:455,
			minWidth:400,minHeigth:350,
			proxyDrag:true,modal:true
		},
		dialogShowBeforeFunc:function(dialog){
			ProcessServerAction.getById(id, name, function(data) {
				if (data) {
					showInfo(data.fileBeans, null, "Station");
					Htjs.form.setDetailObject("stationDetailControl",data);
					setDictNameByKey(data["stationType"],"stationType_show_span");
				}	
			});
		}
	}
	Htjs.dialog.show(elem);
}

function UpdateDialogShow(id,elem){
	Htjs.dialog.config={
		divid:"editDialog",
		dialogconfig:{
			autoTabs:true,shadow:true,
			width:580,height:325,
			minWidth:580,minHeigth:325,
			proxyDrag:true,modal:true
		},
		dialogShowAfterFunc:function(dialog){
			ProcessServerAction.getById(id, function(data){
				Htjs.form.setObject("editObj", data);
	        });
		},
		buttons:{
			"修改":function(dialog){
				Htjs.form.execute(dialog,"editObj",function(obj){
				        ProcessServerAction.update(obj, function(data){
				            if (data) {
				                Htjs.alert.msg('修改成功', '修改成功！', null);
				                dialog.hide();
				                pageStation.grid.reload();
				            } else {
				                alert("修改失败！");
				            }
				        });
				});
			}
		}
	}
	Htjs.dialog.show(elem);
}

function AddDialogShow(elem){
	Htjs.dialog.config={
		divid:"addDialog",
		dialogconfig:{
			autoTabs:true,shadow:true,
			width:580,height:325,
			minWidth:580,minHeigth:325,
			proxyDrag:true,modal:true
		},
		dialogShowAfterFunc:function(dialog){
			//弹出框显示后验证
			Validator.initValidate("addObj",dialog);
		},
		buttons:{
			"添加":function(dialog){
				Htjs.form.execute(dialog,"addObj",function(obj){
					
					ProcessServerAction.add(obj, function(data){
						if(data){
							Htjs.alert.msg('添加成功','添加成功！',null);
							dialog.hide();
							pageStation.grid.reload();
						} else {
							alert("添加失败！");
						}
					});
					
				});
			}
		}
	}
	Htjs.dialog.show(elem);
}

//删除操作
var DeleteObj = {
    deleteSelected: function(){
        var idArray = checkbox.getAllSelectedValues("selectBox");
        if (!idArray || idArray.length == 0) {
            alert('请选择要删除的记录。');
            return;
        }
        if (window.confirm("您确定要删除当前选择的记录吗？")) {
            ProcessServerAction.deleteMore(idArray, function(flg){
                if (flg) {
                    Htjs.alert.msg('删除成功', '删除操作成功!共' + flg + '条.', null);
                    pageStation.grid.reload();
                }
                else {
                    alert("删除附件失败！");
                }
            });
        }
    },
    deleteObj: function(id){
        if (window.confirm("您确定要删除吗？")) {
            ProcessServerAction.deleteById(id, function(flg){
                if (flg) {
                    Htjs.alert.msg('删除成功', '删除操作成功!', null);
                    pageStation.grid.reload();
                }
                else {
                    alert('删除附件失败！')
                }
            });
        }
    }
}
///target="_blank"
function showSysMonitorInfo(id, ip){
	var url = context + "/business/sysMonitor/systemMonitor_info.jsp?tphId="+id+"&tphIp="+ip;
	window.open(url, "_blank", "height=730, width=1000, toolbar= no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes,top=0,left=200");
}
//调用onReady方法时可以带三个参数，
//第一个参数是必须的，表示要执行的函数或匿名函数，
//第二参数表示函数的作用域，第三个参数表示函数执行的一些其它特性，比如延迟多少毫秒执行等
//hdht.onReady(page.init, page, true);
//hdht.EventManager.onDocumentReady(page.init, page, true);
document.ready();

