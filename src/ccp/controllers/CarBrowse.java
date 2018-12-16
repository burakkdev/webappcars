package ccp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ccp.business.Product;
import ccp.data.ProductDB;

/**
 * Servlet implementation class CarBrowse
 */
public class CarBrowse extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		System.out.println("requet from carbrowse is: " + requestURI);
		String url;
		url = showProduct(request,response);
		
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String showProduct(HttpServletRequest request,HttpServletResponse reponse) {
		
		String productCode = request.getPathInfo();
		System.out.println("Product URI path here is --> " + productCode);
		if(productCode != null) {
			productCode = productCode.substring(1);
			Product product = ProductDB.selectProduct(productCode);
			HttpSession session = request.getSession();
			session.setAttribute("product", product);
		}
		
		return "/carbrowse/" + productCode + "/index.jsp";
	}

}
