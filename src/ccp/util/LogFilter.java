package ccp.util;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogFilter implements Filter {

	private FilterConfig config;
	private Logger log;

	public LogFilter() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {

		this.config = filterConfig;

		PropertyConfigurator
				.configure(config.getServletContext().getRealPath("/") + "WEB-INF/classes/servletLog.properties");

		log = Logger.getLogger(LogFilter.class);

		log.info("Logger instantiated in " + getClass().getName());

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException {

		HttpServletRequest req = null;
		HttpServletResponse res = null;

		if (log != null && (request instanceof HttpServletRequest)) {

			req = (HttpServletRequest) request;
			log.info("Request received from: " + req.getRemoteHost() + " for: " + req.getRequestURL() + " sessionID: "
					+ req.getRequestedSessionId());
		}

		if (log != null && (response instanceof HttpServletResponse)) {
			res = (HttpServletResponse) response;
			log.info("Class-->" + res.getClass() + " :: " + res.getStatus());
		}

		chain.doFilter(request, response);
	}

	public void destroy() {

		log = null;
	}
}