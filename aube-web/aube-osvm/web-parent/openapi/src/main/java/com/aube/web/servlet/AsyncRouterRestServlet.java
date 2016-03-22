package com.aube.web.servlet;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aube.support.AubeExecutorThreadFactory;
import com.aube.util.BaseWebUtils;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

@WebServlet(urlPatterns = "/router/rest", asyncSupported = true)
public class AsyncRouterRestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8310087578927796067L;
	private static final AubeLogger logger = LoggerUtils.getLogger(AsyncRouterRestServlet.class);
	private ThreadPoolExecutor responseExecutor;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		ServletContext servletContext = servletConfig.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
		responseExecutor = new ThreadPoolExecutor(400, 400, 0L, TimeUnit.SECONDS, taskQueue, new AubeExecutorThreadFactory("OpenApi-AsyncRouterRestServlet"));
		responseExecutor.allowCoreThreadTimeOut(false);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		processRequest(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String remoteIp = BaseWebUtils.getRemoteIp(request);
			response.setHeader("cip", remoteIp);
		} catch (IOException e) {
			logger.error(request.getRequestURI(), e, 10);
		} catch (Exception e) {
			logger.error(request.getRequestURI(), e, 10);
		}
	}
}
