﻿var uusdma =  document.domain;
var gisdma = "219.239.44.127";
	gisdma = "10.0.65.136";
var MAS2_IMG_BASEURL = "http://10.0.65.135/mas2-img/";
var MAS2_MQ_BASEURL = "http://10.24.175.102:8161/admin/browse.jsp";

var mas_cfg = {
	"auto":{	
		MAS2_MAPSERVER_CHINA:"http://"+ gisdma+"/ArcGIS/rest/services/hdmas2/MapServer",
//		MAS2_MAPSERVER_CHINA_TITLE:"http://192.168.1.117:6080/arcgis/rest/services/H8server1/MapServer",
		MAS2_MAPSERVER_CHINA_TITLE:"http://"+gisdma+"/ArcGIS/rest/services/mas2title/MapServer",
		MAS2_MAPSERVER_BASESNW:"http://"+ gisdma+"/ArcGIS/rest/services/BaseSnw/MapServer",
		MAS2_MAPSERVER_SNW:"http://"+ gisdma+"/ArcGIS/rest/services/Snow/MapServer",
		MAS2_MAPSERVER_HRF:"http://"+ gisdma+"/ArcGIS/rest/services/Hrf/MapServer",
        	MAS2_MAPSERVER_BLOCK:"http://"+ gisdma+"/ArcGIS/rest/services/masblock/MapServer",
		MAS2_IMGSERVER_URL:"http://10.0.65.135/mas2-img/",
		MAS2_IMGSERVER_SNW:"http://10.0.65.135/mas2-snwimg/",
		MAS2_PRODUCTFILE:"http://10.0.65.135/mas2-product/",
		MAS2_UUSIMAGES:"http://10.0.65.135/mas2-uus"
	},
	"window98":{	
		MAS2_MAPSERVER_CHINA:"http://10.0.65.136/ArcGIS/rest/services/hdmas2/MapServer",
		MAS2_MAPSERVER_CHINA_TITLE:"http://10.0.65.136/ArcGIS/rest/services/mas2title/MapServer",
//		MAS2_MAPSERVER_CHINA_TITLE:"http://192.168.1.117:6080/arcgis/rest/services/H8server1/MapServer",

		MAS2_MAPSERVER_BASESNW:"http://10.0.65.136/ArcGIS/rest/services/BaseSnw/MapServer",
		MAS2_MAPSERVER_SNW:"http://10.0.65.136/ArcGIS/rest/services/Snow/MapServer",
		MAS2_MAPSERVER_HRF:"http://10.0.65.136/ArcGIS/rest/services/Hrf/MapServer",
		MAS2_MAPSERVER_BLOCK:"http://10.0.65.136/ArcGIS/rest/services/masblock/MapServer",
		MAS2_IMGSERVER_URL:"http://10.0.65.135/mas2-img/",
		MAS2_IMGSERVER_SNW:"http://10.0.65.135/mas2-snwimg/",
		MAS2_PRODUCTFILE:"http://10.0.65.135/mas2-product/",
		MAS2_UUSIMAGES:"http://10.0.65.135/mas2-uus"
	},
	"yanjianzhong":{
		MAS2_MAPSERVER_CHINA:"http://yanjzpc/ArcGIS/rest/services/hdmas2/MapServer",
		MAS2_MAPSERVER_CHINA_TITLE:"http://yanjzpc/ArcGIS/rest/services/mas2title/MapServer",
		MAS2_IMGSERVER_URL:"http://yanjzpc:8080/mas2-img/",
		MAS2_IMGSERVER_SNW:"http://yanjzpc:8080/mas2-snwimg/",
		MAS2_PRODUCTFILE:"http://yanjzpc:8080/mas2-product/",
		MAS2_UUSIMAGES:"http://yanjzpc:8080/mas2-uus"
	}
}

/**
 * 默认地图范围，不同的省份进行差异化配置。
 */
DmsMapExtent={
	000000:"中国",710000:"台湾",120000:"天津",540000:"西藏",150000:"内蒙古",230000:"黑龙江",
	340000:"安徽",110000:"北京",350000:"福建",420000:"湖北",620000:"甘肃",440000:"广东",
	450000:"广西",520000:"贵州",460000:"海南",130000:"河北",410000:"河南",430000:"湖南",
	220000:"吉林",320000:"江苏",360000:"江西",210000:"辽宁",640000:"宁夏",630000:"青海",
	650000:"新疆",370000:"山东",140000:"山西",610000:"陕西",310000:"上海",510000:"四川"		
}
DEFAULT_DMS_PROVCODE="000000";



//基础地图
MAS2_MAPSERVER_CHINA 	= "http://rsapp.nsmc.cma.gov.cn/ArcGIS/rest/services/hdmas2/MapServer";

//土地利用类型
MAS2_MAPSERVER_BASESNW 	= "http://rsapp.nsmc.cma.gov.cn/ArcGIS/rest/services/BaseSnw/MapServer"
MAS2_MAPSERVER_SNW 		= "http://rsapp.nsmc.cma.gov.cn/ArcGIS/rest/services/Snow/MapServer"
	
MAS2_MAPSERVER_HRF		= "http://rsapp.nsmc.cma.gov.cn/ArcGIS/rest/services/Hrf/MapServer";

//图片服务器
MAS2_IMGSERVER_URL 		= "http://10.0.65.135/mas2-img/";
MAS2_IMGSERVER_SNW 		= "http://10.0.65.135/mas2-snwimg/";

MAS2_MAPSERVER_BLOCK = "http://10.0.65.136/ArcGIS/rest/services/masblock/MapServer";


CURRENT_CONGIF = null;
function initcfg(flg){
	CURRENT_CONGIF = mas_cfg[flg];
	if(CURRENT_CONGIF){
		MAS2_MAPSERVER_CHINA = CURRENT_CONGIF.MAS2_MAPSERVER_CHINA;
		MAS2_MAPSERVER_BASESNW = CURRENT_CONGIF.MAS2_MAPSERVER_BASESNW;
		MAS2_MAPSERVER_SNW = CURRENT_CONGIF.MAS2_MAPSERVER_SNW;
		MAS2_MAPSERVER_HRF = CURRENT_CONGIF.MAS2_MAPSERVER_HRF;
		MAS2_IMGSERVER_URL = CURRENT_CONGIF.MAS2_IMGSERVER_URL;
		MAS2_IMGSERVER_SNW = CURRENT_CONGIF.MAS2_IMGSERVER_SNW;
        	MAS2_MAPSERVER_BLOCK = CURRENT_CONGIF.MAS2_MAPSERVER_BLOCK;
		MAS2_MAPSERVER_CHINA_TITLE = CURRENT_CONGIF.MAS2_MAPSERVER_CHINA_TITLE;
		MAS2_PRODUCTFILE = CURRENT_CONGIF.MAS2_PRODUCTFILE;
		MAS2_UUSIMAGES = CURRENT_CONGIF.MAS2_UUSIMAGES;
	}
}
initcfg("window98");





