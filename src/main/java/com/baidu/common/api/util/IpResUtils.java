package com.baidu.common.api.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ip库工具类
 * */
public class IpResUtils{
	private static final Logger logger = LoggerFactory.getLogger(IpResUtils.class);
	static List<IpSection> ipSectionList0=new CopyOnWriteArrayList<IpSection>();
	static List<IpSection> ipSectionList1=new CopyOnWriteArrayList<IpSection>();
	public volatile static boolean isLoaded=false;
	public volatile static long modifyTime;
	volatile static int currentIpLibrary=0;
	static String proviceConfidence= PropertyReader.getValue("/config.properties", "provinceConfidence");  
	static String cityConfidence=PropertyReader.getValue("/config.properties", "cityConfidence");  
	public static List<IpSection> loadFiles(String filePathName){
		File file = new File(filePathName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String tempString = null;
			int listbytes=0;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				try {
					if(tempString.startsWith("#")) {
						continue;
					}
					String[] strs=tempString.split("\\|");
					String ipStartStr=strs[0];
					String ipEndStr=strs[1];
					String provice=strs[4];
					String city=strs[5];
					int proviConfi=Integer.parseInt(strs[9]);
					int cityConfi=Integer.parseInt(strs[10]);
					//筛选有用的ip区间
					if(provice.equals("None")||city.equals("None")){
						continue;
					}
					if(proviConfi<Integer.parseInt(proviceConfidence)||cityConfi<Integer.parseInt(cityConfidence)){
						continue;
					}
					Ip ipStart=new Ip(ipStartStr);
					Ip ipEnd=new Ip(ipEndStr);
					IpSection ipSec=new IpSection(ipStart, ipEnd, provice,city,proviConfi,cityConfi);
					//如果当期正在运行的是0库 则加载1库
					if(currentIpLibrary==0)
						ipSectionList0.add(ipSec);
					else
						ipSectionList1.add(ipSec);
					listbytes+=ipSec.totalBytes();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			reader.close();
			isLoaded=true;
			logger.info("init load end ,ipSectionList0 size is {},ipSectionList1 size is {}",ipSectionList0.size(),ipSectionList1.size());
			logger.info("this load list total is {}bytes",listbytes);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return null;
		
	}
	public static List<IpSection> updateFiles(String filePathName){
		//如果存在则清空
		if(currentIpLibrary==0)
			ipSectionList1.clear();
		else
			ipSectionList0.clear();
		File file = new File(filePathName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				try {
					if(tempString.startsWith("#")) {
						continue;
					}
					String[] strs=tempString.split("\\|");
					String ipStartStr=strs[0];
					String ipEndStr=strs[1];
					String provice=strs[4];
					String city=strs[5];
					int proviConfi=Integer.parseInt(strs[9]);
					int cityConfi=Integer.parseInt(strs[10]);
					//筛选有用的ip区间
					if(provice.equals("None")||city.equals("None")){
						continue;
					}
					if(proviConfi<Integer.parseInt(proviceConfidence)||cityConfi<Integer.parseInt(cityConfidence)){
						continue;
					}
					Ip ipStart=new Ip(ipStartStr);
					Ip ipEnd=new Ip(ipEndStr);
					IpSection ipSec=new IpSection(ipStart, ipEnd, provice,city,proviConfi,cityConfi);
					//如果当期正在运行的是0库 则加载1库
					if(currentIpLibrary==0)
						ipSectionList1.add(ipSec);
					else
						ipSectionList0.add(ipSec);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			reader.close();
			isLoaded=true;
			logger.info("update load end ,ipSectionList0 size is {},ipSectionList1 size is {}",ipSectionList0.size(),ipSectionList1.size());
		    logger.info("current currentIpLibrary is {}",currentIpLibrary);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return null;
		
	}
	
	public static String getCityByIp(String ip) {
		try {
			if (currentIpLibrary == 0) {
				int index = CustBinarySearch.search(ipSectionList0, new Ip(ip));
				if (index != -1) {
					IpSection ipSec = ipSectionList0.get(index);
					return ipSec.getProvice() +"-"+ ipSec.getCity();
				}
			} else {
				int index = CustBinarySearch.search(ipSectionList1, new Ip(ip));
				if (index != -1) {
					IpSection ipSec = ipSectionList1.get(index);
					return ipSec.getProvice() +"-"+ ipSec.getCity();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean changeIpLib(){
		
		if(currentIpLibrary==0){
			currentIpLibrary=1;
		}else{
			currentIpLibrary=0;
		}
		logger.info("currentIpLibrary changed!currentIpLibrary is {}",currentIpLibrary);
		return true;
	}
	
}
