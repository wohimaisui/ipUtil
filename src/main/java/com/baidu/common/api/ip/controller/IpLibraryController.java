/**
 * 
 */
package com.baidu.common.api.ip.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.common.api.servce.IpLibrarySerivce;

/**
 * @author yangchangjian_d ip库的解析
 */
@RequestMapping("ip/")
@Controller
public class IpLibraryController {
	private final Logger logger = LoggerFactory.getLogger(IpLibraryController.class);
	@Resource
	private IpLibrarySerivce ipLibrarySerivce;

	/**
	 * @param code
	 *            0 成功 1 失败 2 未查到对应的city
	 * @param errMsg
	 *            当cod为1时 有值
	 * @param result
	 *            返回的省-市
	 */
	@RequestMapping("getCity")
	@ResponseBody
	public Map<String, String> getCity(HttpServletRequest req, String ip) {
		logger.info("getCity request start,remote client ip is {},param ip is {}",
				req.getRemoteAddr(), ip);
		Map<String, String> ret = new HashMap<String, String>();
		if (StringUtils.isBlank(ip)) {
			logger.warn("ip不能为空,ip is {}", ip);
			ret.put("code", "1");
			ret.put("errMsg", "ip不能为空");
			return ret;
		}
		try {
			long start=System.currentTimeMillis();
			String city = ipLibrarySerivce.getCityByClinetIp(ip);
			long end=System.currentTimeMillis();
			logger.info("this query cost time is {}",end-start);
			if (StringUtils.isBlank(city)) {
				ret.put("code", "2");
				ret.put("errMsg", "未查到对应的city");
			} else {
				ret.put("code", "0");
				ret.put("errMsg", "查询成功");
				ret.put("result", city);
			}

		} catch (Exception e) {
			logger.error("系统错误", e);
			ret.put("code", "1");
			ret.put("errMsg", "系统发生错误");
		}
		return ret;
	}
}
