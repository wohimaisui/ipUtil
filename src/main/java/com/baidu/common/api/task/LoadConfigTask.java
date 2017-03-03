package com.baidu.common.api.task;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.baidu.common.api.util.IpResUtils;


@Component
public class LoadConfigTask {
	
	Logger log = LoggerFactory.getLogger(LoadConfigTask.class);
	
	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
	
	
	/**
	 * 每5s定时定时ip库是否更新
	 */
	@PostConstruct
	@Scheduled(cron="*/5 * * * * *")
	public void loadConfig() {
		// 异步定时加载ip库
				fixedThreadPool.execute(new Runnable() {
					@Override
					public void run() {
							URL url = ClassUtils.getDefaultClassLoader().getResource("colombo_iplib.txt");
							File ipFile = new File(url.getPath());
							if (ipFile.exists() && ipFile.lastModified() != IpResUtils.modifyTime) {
								IpResUtils.updateFiles(url.getPath() + "/");
								IpResUtils.modifyTime=ipFile.lastModified();
								IpResUtils.changeIpLib();
							}
					}
				});
}
}