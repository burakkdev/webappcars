package ccp.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CarPartsContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		
		String contextPath = sc.getContextPath();
		System.out.println("context listener-context path is -->" + contextPath);
		String realPath ="http://localhost:8080" + contextPath;
		sc.setAttribute("realPath", realPath);
		
		String custServEmail = sc.getInitParameter("custServEmail");
		sc.setAttribute("custServEmail", custServEmail);
		
		GregorianCalendar currentDate = new GregorianCalendar();
        int currentYear = currentDate.get(Calendar.YEAR);
        sc.setAttribute("currentYear", currentYear);
		
        ArrayList<String> creditCardYears = new ArrayList<>();
        for(int i = 0; i<8; i++) {
        	int year = currentYear + i;
        	String yearString = Integer.toString(year);
        	creditCardYears.add(yearString);
        }
        
        sc.setAttribute("creditCardYears", creditCardYears);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
