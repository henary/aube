package com.aube.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aube.Config;
import com.aube.util.VmUtils;

public class AubeStartupListener extends ContextLoaderListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		//根据环境加载配置文件
		String SPRING_CONFIG_KEY = "contextConfigLocation";
		String SPRING_CONFIG_VALUE = "classpath:spring/appContext-*.xml";
		
		String ip = Config.getServerIp();
		
		
		context.setInitParameter(SPRING_CONFIG_KEY, SPRING_CONFIG_VALUE);
		super.contextInitialized(event);
		
		//初始化其他信息
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		Config config = ctx.getBean(Config.class);
		VmUtils utils = new VmUtils();
		config.replacePageTool("VmUtils", utils);
	}
}
