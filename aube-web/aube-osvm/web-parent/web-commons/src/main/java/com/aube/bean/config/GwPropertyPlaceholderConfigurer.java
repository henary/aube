package com.aube.bean.config;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.aube.Config;
public class GwPropertyPlaceholderConfigurer extends  PropertyPlaceholderConfigurer{
	@Override
	protected void convertProperties(Properties props){
		props.setProperty("dubbo.host", Config.getServerIp());
		super.convertProperties(props);
	}
}
