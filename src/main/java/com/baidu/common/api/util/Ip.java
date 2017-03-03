package com.baidu.common.api.util;
/**
 * 
 */

/**
 * @author yangchangjian_d
 *
 */
public class Ip implements Comparable<Ip>{
  private int fir;
  private int sec;
  private int thrid;
  private int fouth;
  
  
public Ip (String ipStr){
	String[] ips=ipStr.split("\\.");
	this.fir=Integer.parseInt(ips[0]);
	this.sec=Integer.parseInt(ips[1]);
	this.thrid=Integer.parseInt(ips[2]);
	this.fouth=Integer.parseInt(ips[3]);
}

public int getFir() {
	return fir;
}

public void setFir(int fir) {
	this.fir = fir;
}

public int getSec() {
	return sec;
}

public void setSec(int sec) {
	this.sec = sec;
}

public int getThrid() {
	return thrid;
}

public void setThrid(int thrid) {
	this.thrid = thrid;
}

public int getFouth() {
	return fouth;
}

public void setFouth(int fouth) {
	this.fouth = fouth;
}
/**
 *@author yangchangjian_d
 *@return 0 相等 1 大于 -1 小于
 * */
@Override
public int compareTo(Ip o) {
	if(fir>o.fir){
		return 1;
	}else if(fir<o.fir){
		return -1;
	}
	//如果第一段相等,比较第二段
	if(sec>o.sec){
		return 1;
	}else if(sec<o.sec){
		return -1;
	}
	//如果第二段相等,比较第三段
	if(thrid>o.thrid){
		return 1;
	}else if(thrid<o.thrid){
		return -1;
	}
	//如果第三段相等,比较第四段
	if(fouth>o.fouth){
		return 1;
	}else if(fouth<o.fouth){
		return -1;
	}
	
	return 0;
}

@Override
public String toString() {
	return fir+"."+sec+"."+thrid+"."+fouth;
}
  
  
	
}
