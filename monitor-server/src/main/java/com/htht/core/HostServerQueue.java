package com.htht.core;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.htht.util.QueueStatistics;

public class HostServerQueue {
	
	/*public QueueStatistics getQueueStatisticsInfo(int deployType, int osType, String key){
		QueueStatistics queueStatistics = new QueueStatistics();
		// 
    	ConcurrentHashMap<String,List<Vcomputer>> map = null;
		switch(deployType){
		case COMMON_CONSTANT.COLONY_VM:
			if(osType == COMMON_CONSTANT.OS_TYPE_CENTOS){
				map = this.vmCosClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_REDHAT){
				map = this.vmRedClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_UBUNTU){// linux
				map = this.vmUbuClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_WINDOWS){// windows
				map = this.vmWinClustersNodes;
			}
			break;
		case COMMON_CONSTANT.COLONY_DC:
			if(osType == COMMON_CONSTANT.OS_TYPE_CENTOS){
				map = this.dcCosClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_REDHAT){
				map = this.dcRedClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_UBUNTU){// linux
				map = this.dcUbuClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_WINDOWS){// windows
				map = this.dcWinClustersNodes;
			}
			break;
		case COMMON_CONSTANT.COLONY_SPK:
			if(osType == COMMON_CONSTANT.OS_TYPE_CENTOS){
				map = this.spkCosClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_REDHAT){
				map = this.spkRedClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_UBUNTU){// linux
				map = this.spkUbuClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_WINDOWS){// windows
				map = this.spkWinClustersNodes;
			}
			break;
		case COMMON_CONSTANT.COLONY_STM:
			if(osType == COMMON_CONSTANT.OS_TYPE_CENTOS){
				map = this.stmCosClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_REDHAT){
				map = this.stmRedClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_UBUNTU){// linux
				map = this.stmUbuClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_WINDOWS){// windows
				map = this.stmWinClustersNodes;
			}
			break;
		case COMMON_CONSTANT.COLONY_PM:
			if(osType == COMMON_CONSTANT.OS_TYPE_CENTOS){
				map = this.pmCosClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_REDHAT){
				map = this.pmRedClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_UBUNTU){// linux
				map = this.pmUbuClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_WINDOWS){// windows
				map = this.pmWinClustersNodes;
			}
			break;
		}
		//
		List<Vcomputer> nodes = map.get(key);
		if(nodes == null || nodes.isEmpty()){
			return queueStatistics;
		}
		//
        int nlen = nodes.size();
        int m=0,x=0;
        for(int i=0;i<nlen;i++){
        	Vcomputer hi = nodes.get(i);
        	if(hi.isWorking()){
				logger.debug("$$$※ 【zx】node working{"+hi.getIp()+":"+hi.getKey()+":"+hi.getActionType()+":"+hi.getExecuteTime()+"}");
        		System.out.println("【zx】node working{"+hi.getIp()+":"+hi.getKey()+":"+hi.getActionType()+":"+hi.getExecuteTime()+"}");
        		m++;
        	}else{
        		x++;
        	}
        }
        queueStatistics.setTotalCount(nlen);
        queueStatistics.setUsedCount(m);
        queueStatistics.setFreeCout(x);
        logger.debug("$$$※ 【zx】node working{"+key+"（total:"+nlen+",used:"+m+",free:"+x+"）}");
        System.out.println("【zx】node working{"+key+"（total:"+nlen+",used:"+m+",free:"+x+"）}");    
		return queueStatistics;
	}
	public QueueStatistics getQueueStatisticsInfoByAll(int deployType, int osType) {
		QueueStatistics queueStatistics = new QueueStatistics();
		List<Vcomputer> nodes = null;
		int nlen = 0, m = 0, x = 0;
		ConcurrentHashMap<String,List<Vcomputer>> map = null;
		switch(deployType){
		case COMMON_CONSTANT.COLONY_VM:
			if(osType == COMMON_CONSTANT.OS_TYPE_CENTOS){
				map = this.vmCosClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_REDHAT){
				map = this.vmRedClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_UBUNTU){// linux
				map = this.vmUbuClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_WINDOWS){// windows
				map = this.vmWinClustersNodes;
			}
			break;
		case COMMON_CONSTANT.COLONY_DC:
			if(osType == COMMON_CONSTANT.OS_TYPE_CENTOS){
				map = this.dcCosClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_REDHAT){
				map = this.dcRedClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_UBUNTU){// linux
				map = this.dcUbuClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_WINDOWS){// windows
				map = this.dcWinClustersNodes;
			}
			break;
		case COMMON_CONSTANT.COLONY_SPK:
			if(osType == COMMON_CONSTANT.OS_TYPE_CENTOS){
				map = this.spkCosClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_REDHAT){
				map = this.spkRedClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_UBUNTU){// linux
				map = this.spkUbuClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_WINDOWS){// windows
				map = this.spkWinClustersNodes;
			}
			break;
		case COMMON_CONSTANT.COLONY_STM:
			if(osType == COMMON_CONSTANT.OS_TYPE_CENTOS){
				map = this.stmCosClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_REDHAT){
				map = this.stmRedClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_UBUNTU){// linux
				map = this.stmUbuClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_WINDOWS){// windows
				map = this.stmWinClustersNodes;
			}
			break;
		case COMMON_CONSTANT.COLONY_PM:
			if(osType == COMMON_CONSTANT.OS_TYPE_CENTOS){
				map = this.pmCosClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_REDHAT){
				map = this.pmRedClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_UBUNTU){// linux
				map = this.pmUbuClustersNodes;
			}else if(osType == COMMON_CONSTANT.OS_TYPE_WINDOWS){// windows
				map = this.pmWinClustersNodes;
			}
			break;
		}
		logger.debug("$$$※<deployType="+deployType+",osType="+osType+"> map == null("+(map == null)+") size("+(map == null ? 0 : map.size())+")");
		System.out.println("<deployType="+deployType+",osType="+osType+"> map == null("+(map == null)+") size("+(map == null ? 0 : map.size())+")");
		for (Map.Entry<String, List<Vcomputer>> e : map.entrySet()) {
			synchronized (ourMonitor) {
				nodes = e.getValue();
				nlen += nodes.size();
				if (nodes == null || nodes.isEmpty()) {
					continue;
				}
				for (int i = 0; i < nodes.size(); i++) {
					Vcomputer hi = nodes.get(i);
					if (hi.isWorking()) {
						m++;
					} else {
						x++;
					}
				}
			}
		}

		queueStatistics.setTotalCount(nlen);
		queueStatistics.setUsedCount(m);
		queueStatistics.setFreeCout(x);
        logger.debug("$$$※usedCount="+m+",freeCount="+x+",totalCount="+nlen);
		System.out.println("usedCount="+m+",freeCount="+x+",totalCount="+nlen);
		return queueStatistics;
	}*/
}
