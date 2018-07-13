package com.htht.util;


/**
 * 运行队列数统计
 * @author masadmin
 *
 */
public class QueueStatistics {
	private Integer totalCount = 0;//总共多少队列
	private Integer usedCount = 0;//占用多少队列
	private Integer freeCout = 0;//剩于多少队列
	
	public synchronized Double getUsedProportion(){
		try{
			if(this.totalCount <= 0){
				return 1.0;
			}else{
				return (Double.valueOf(this.usedCount)/Double.valueOf(this.totalCount));
			}
		}catch(Exception ex){
			return 1.0;
		}
	}
  
	public Integer getFreeCout() {
		return freeCout;
	}
	public void setFreeCout(Integer freeCout) {
		this.freeCout = freeCout;
	}
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	 
	public Integer getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
	}

}
