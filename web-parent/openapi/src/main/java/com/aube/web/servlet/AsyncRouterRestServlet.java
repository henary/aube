package com.aube.web.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(urlPatterns = "/router/rest", asyncSupported = true)
public class AsyncRouterRestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8310087578927796067L;
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		
	}
}
