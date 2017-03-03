package com.baidu.common.api.util;
/**
 * @author yangchangjian_d
 * ip区间
 */
public class IpSection implements Comparable<Ip>{
  private Ip ipStart;
  private Ip ipEnd;
  /**
   * 省名称
   **/
  private String provice;
  /**
   * 市名称
   * */
  private String city;
  /**
   *省级 置信度
   * 
   * */
  private int proviceConfi;
  /**
   *城市级 置信度
   * 
   * */
  private int cityConfi;
  
public IpSection(Ip ipStart, Ip ipEnd, String provice, String city,
		int proviceConfi, int cityConfi) {
	super();
	this.ipStart = ipStart;
	this.ipEnd = ipEnd;
	this.provice = provice;
	this.city = city;
	this.proviceConfi = proviceConfi;
	this.cityConfi = cityConfi;
}

@Override
public String toString() {
	return "IpSection [ipStart=" + ipStart + ", ipEnd=" + ipEnd + ", provice="
			+ provice + ", city=" + city + ", proviceConfi=" + proviceConfi
			+ ", cityConfi=" + cityConfi + "]";
}
/**
 * 所谓的相等,就是在这个区间
 * */
@Override
public int compareTo(Ip o) {
		//如果区间的end小于目标区间,改区间肯定在目标区间上游
		if(ipEnd.compareTo(o)<0){
			return -1;
		}
		//如果区间的end大于目标区间并且区间的start也大于目标区间,则肯定处于目标渠区间的下游
		if(ipEnd.compareTo(o)>0&&ipStart.compareTo(o)>0){
			return 1;
		}
		//如果区间的end大于目前区间,并且区间的start小于目标区间,则肯定处于区间之中
		if(ipEnd.compareTo(o)>=0&&ipStart.compareTo(o)<=0){
			return 0;
		}
		return ipStart.compareTo(o);
}

public Ip getIpStart() {
	return ipStart;
}

public void setIpStart(Ip ipStart) {
	this.ipStart = ipStart;
}

public Ip getIpEnd() {
	return ipEnd;
}

public void setIpEnd(Ip ipEnd) {
	this.ipEnd = ipEnd;
}

public String getProvice() {
	return provice;
}

public void setProvice(String provice) {
	this.provice = provice;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public int getProviceConfi() {
	return proviceConfi;
}

public void setProviceConfi(int proviceConfi) {
	this.proviceConfi = proviceConfi;
}

public int getCityConfi() {
	return cityConfi;
}

public void setCityConfi(int cityConfi) {
	this.cityConfi = cityConfi;
}
/**
 * 字节对象占用字节数
 * @return 字节个数
 * */
public int totalBytes(){
	//1. 1个Ip对象占用16个字节(1个int是4字节)
	int provices=provice.getBytes().length;
	int citys=city.getBytes().length;
	return provices+citys+16*2+4*2;
	
}
}
