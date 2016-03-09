package com.aube.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class AubeIpConfig {
	private static final String serverIp;
	private final static String hostname;
	private static final List<String> searchList;
	
	static{
		searchList = new ArrayList<>();
		searchList.addAll(Arrays.asList(new String[]{"192.168.100."/**青云服务器*/}));
		
		String[] host = AubeIpConfig.getServerAddr();
		serverIp = host[0];
		//hostname = host[1];
		String h = "";
		try {
			h = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(h)){
			h = host[1];
		}
		hostname = h;
	}
	public static String[] getServerAddr(){
		Map<String, String> hostMap = getServerAddrMap();
		for(String search: searchList){
			for(String addr: hostMap.keySet()){
				if(addr.startsWith(search)){
					return new String[]{addr, hostMap.get(addr)};
				}
			}
		}
		return new String[]{"127.0.0.1", "localhost"};
	}
	private static Map<String, String> getServerAddrMap(){
		Map<String, String> hostMap = new TreeMap<String, String>();
		try{
			Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
			while(niList.hasMoreElements()){
				NetworkInterface ni = niList.nextElement();
				Enumeration<InetAddress> addrList = ni.getInetAddresses();
				while(addrList.hasMoreElements()){
					InetAddress addr = addrList.nextElement();
					if(addr instanceof Inet4Address) {//只做IPV4
						hostMap.put(addr.getHostAddress(), addr.getHostName());
					}
				}
			}
		}catch(Exception e){
		}
		return hostMap;
	}
	
	public static String getServerip() {
		return serverIp;
	}
	public static String getHostname() {
		return hostname;
	}
	
	
}
