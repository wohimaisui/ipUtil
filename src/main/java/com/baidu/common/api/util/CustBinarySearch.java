package com.baidu.common.api.util;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustBinarySearch {
	private static final Logger logger = LoggerFactory.getLogger(CustBinarySearch.class);
	    /** 
	     * @param ipSesc 已经排序的list
	     * @param ip 待匹配的ip
	     */  
	    public static int search(List<IpSection> ipSesc,Ip ip) {  
	        int low = 0;  
	        int upper =ipSesc.size()-1;  
	       
	        while (low <= upper) {  
	            int mid = (low + upper) / 2;  
	            if (ipSesc.get(mid).compareTo(ip)<0)  
	                low = mid + 1;  
	            else if (ipSesc.get(mid).compareTo(ip)>0)  
	                upper = mid - 1;  
	            else  
	                return mid;  
	        }  
	        return -1;  
	    }  
	  
}
