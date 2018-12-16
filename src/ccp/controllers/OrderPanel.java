package ccp.controllers;

import java.io.IOException;
import java.util.Enumeration;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ccp.business.Cart;
import ccp.business.Invoice;
import ccp.business.LineItem;
import ccp.business.Product;
import ccp.business.User;
import ccp.data.InvoiceDB;
import ccp.data.ProductDB;
import ccp.data.UserDB;
import ccp.util.CookieUtil;
import ccp.util.MailUtil;


public class OrderPanel extends HttpServlet {
	private static final String defaultURL = "/cart/cart.jsp";
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url = "";
		Enumeration r =  request.getParameterNames();
		while(r.hasMoreElements()) {
			System.out.println(r.nextElement());
		}
		if(requestURI.endsWith("/addItem")) {
			url = addItem(request,response);
		} else if (requestURI.endsWith("/updateItem")) {
			url = updateItem(request,response);
		} else if (requestURI.endsWith("/removeItem")) {
			url = removeItem(request,response);
		} else if (requestURI.endsWith("/checkUser")) {
			url = checkUser(request,response);
		} else if (requestURI.endsWith("/processUser")) {
			url = processUser(request,response);
		} else if (requestURI.endsWith("/displayInvoice")) {
			url = displayInvoice(request,response);
		} else if (requestURI.endsWith("/displayUser")) {
			url = "/cart/user.jsp";
		} else if (requestURI.endsWith("displayCreditCard")) {
			url = "/cart/credit_card.jsp";
		} else if (requestURI.endsWith("/completeOrder")) {
			url = completeOrder(request,response);
		}
		
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
        String url = defaultURL;
        if (requestURI.endsWith("/showCart")) {
            showCart(request, response);
        } else if (requestURI.endsWith("/checkUser")) {
            url = checkUser(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
	}

	private String showCart(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null || cart.getCount() == 0) {
			request.setAttribute("emptyCart", "There's no product in cart");
		} else {
			request.getSession().setAttribute("cart", cart);
		}
		return defaultURL;
	}
	
	private String addItem(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null)
			cart = new Cart();
		
		String productCode = request.getParameter("productCode");
		Product product = ProductDB.selectProduct(productCode);
		if (product != null) {
			LineItem lineItem = new LineItem();
			lineItem.setProduct(product);
			cart.addItem(lineItem);
		}
		session.setAttribute("cart", cart);
		return defaultURL;
	}
	
	private String updateItem(HttpServletRequest request,HttpServletResponse response) {
		String quantityString = request.getParameter("quantity");
		String productCode = request.getParameter("productCode");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		int quantity;
		
		try {
			quantity = Integer.parseInt(quantityString);
			if (quantity < 0)
				quantity = 1;
		} catch (NumberFormatException ex) {
			quantity = 1;
		}
		
		Product product = ProductDB.selectProduct(productCode);
		if (product != null && cart != null) {
			LineItem lineItem = new LineItem();
			lineItem.setProduct(product);
			lineItem.setQuantity(quantity);
			if (quantity > 0)
				cart.addItem(lineItem);
			else
				cart.removeItem(lineItem);
		}
		
		return defaultURL;
	}
	
	private String removeItem(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		String productCode = request.getParameter("productCode");
		Product product = ProductDB.selectProduct(productCode);
		if (product != null && cart != null) {
			LineItem lineItem = new LineItem();
			lineItem.setProduct(product);
			cart.removeItem(lineItem);
		}
		return defaultURL;
	}
	
	private String checkUser(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		String url = "/cart/user.jsp";
		if (user != null && !user.getAddress1().equals("")) {
			url = "/orderPanel/displayInvoice";
		} else {
			Cookie [] cookies = request.getCookies();
			String email = CookieUtil.getCookieValue(cookies,"emailCookie");
			if(email.equals("")) {
				user = new User();
				url = "/cart/user.jsp";
			} else {
				user = UserDB.selectUser(email);
				if(user != null && !user.getAddress1().equals("")) {
					url = "/orderPanel/displayInvoice";
				}
			}
		}
		session.setAttribute("user", user);
		return url;
	}
	
	private String processUser(HttpServletRequest request,HttpServletResponse response) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String companyName = request.getParameter("companyName");
		String email = request.getParameter("email");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zip = request.getParameter("zip");
		String country = request.getParameter("country");
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			user = new User();
		}
		
		if (UserDB.emailExists(email)) {
			user = UserDB.selectUser(email);
			user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setCompanyName(companyName);
            user.setAddress1(address1);
            user.setAddress2(address2);
            user.setCity(city);
            user.setState(state);
            user.setZip(zip);
            user.setCountry(country);            
            UserDB.update(user);
		} else {
			user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setCompanyName(companyName);
            user.setAddress1(address1);
            user.setAddress2(address2);
            user.setCity(city);
            user.setState(state);
            user.setZip(zip);
            user.setCountry(country);
            UserDB.insert(user);
		}
		
		session.setAttribute("user", user);
		
		return "/orderPanel/displayInvoice";
	}
	
	private String displayInvoice(HttpServletRequest request,HttpServletResponse repsonse) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Cart cart = (Cart) session.getAttribute("cart");
		
		java.util.Date today = new java.util.Date();
		
		Invoice invoice = new Invoice();
		invoice.setUser(user);
		invoice.setInvoiceDate(today);
		invoice.setLineItems(cart.getItems());
		
		session.setAttribute("invoice", invoice);
		
		return "/cart/invoice.jsp";
	}
	
	
	private String completeOrder(HttpServletRequest request,HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		Invoice invoice = (Invoice) session.getAttribute("invoice");
		
		String creditCardType = request.getParameter("creditCardType");
		String creditCardNumber = request.getParameter("creditCardNumber");
		String creditCardExpMonth = request.getParameter("creditCardExpirationMonth");
		String creditCardExpYear = request.getParameter("creditCardExpirationYear");
		
		User user = invoice.getUser();
		user.setCreditCardType(creditCardType);
		user.setCreditCardNumber(creditCardNumber);
		user.setCreditCardExpirationDate(creditCardExpMonth + "/" + creditCardExpYear);  
		
		if (UserDB.emailExists(user.getEmail())) {
			UserDB.update(user);
		} else {
			UserDB.insert(user);
		}
		
		invoice.setUser(user);
		
		InvoiceDB.insert(invoice);
		
		Cookie emailCookie = new Cookie("emailCookie",user.getEmail());
		emailCookie.setMaxAge(60*24*365*2*60);
		emailCookie.setPath("/");
		response.addCookie(emailCookie);
		
		session.setAttribute("cart", null);
		
		String to = user.getEmail();
        String from = "confirmation@freshcornrecords.com";
        String subject = "Order Confirmation";
        String body = "Dear " + user.getFirstName() + ",\n\n" +
            "Thanks for ordering from us. " +
            "You should receive your order in 3-5 business days. " + 
            "Please contact us if you have any questions.\n" +
            "Have a great day and thanks again!\n\n" +
            "Joe King\n" +
            "Fresh Corn Records";
        
        boolean isBodyHTML = false;
        try {
        	MailUtil.sendMail(to,from,subject,body,isBodyHTML);
        } catch (MessagingException e) {
        	this.log("Can not send the email to" + to);
        }
        
        return "/cart/complete.jsp";
	}
	
	
	
	
}
