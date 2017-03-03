package com.baidu.common.api.servce.impl;

import java.io.File;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import com.baidu.common.api.servce.IpLibrarySerivce;
import com.baidu.common.api.util.IpResUtils;

/**
 * @author yangchangjian_d
 */
@Service
public class IpLibrarySerivceImpl implements IpLibrarySerivce,InitializingBean{
	private final Logger logger = LoggerFactory.getLogger(IpLibrarySerivceImpl.class);

	@Override
	public String getCityByClinetIp(String ip) {
		try {
			return IpResUtils.getCityByIp(ip);
		} catch (Exception e) {
			logger.error("ip library service error!", e);
			return null;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		 URL url =ClassUtils.getDefaultClassLoader().getResource("colombo_iplib.txt");
		 logger.info("load ip start...");
		 long start=System.currentTimeMillis();
		 IpResUtils.loadFiles(url.getPath()+"/");
		 long end=System.currentTimeMillis();
		 logger.info("load ip end...,costtime is {}ms",end-start);
	 //2.定时任务 每天去扫描ip库文件最后修改时间是否变化了
		 File ipFile=new File(url.getPath());
		 if(ipFile.exists()){
			 IpResUtils.modifyTime=ipFile.lastModified();
		 }
		
	}

}
