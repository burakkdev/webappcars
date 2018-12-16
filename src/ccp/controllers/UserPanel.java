package ccp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ccp.business.User;
import ccp.data.UserDB;

/**
 * Servlet implementation class UserPanel
 */
public class UserPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserPanel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/deleteCookies")) {
            url = deleteCookies(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		String url = "";
		if (requestURI.endsWith("/subscribeToEmail")) {
			url = subscribeToEmail(request, response);
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	private String subscribeToEmail(HttpServletRequest request, HttpServletResponse response) {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);

		request.setAttribute("user", user);

		String url;
		String message;

		if (UserDB.emailExists(email)) {
			message = "This mail address is already in our family <br>"
					+ "Are you a member in our family or a new one ? Please check and continue.";
			request.setAttribute("message", message);
			url = "/email/index.jsp";
		} else {
			UserDB.insert(user);
			message = "";
			request.setAttribute("message", message);
			url = "/email/thanks.jsp";
		}
		return url;
	}
	
	private String deleteCookies(HttpServletRequest request,
            HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);  //delete the cookie
            cookie.setPath("/");  //entire application
            response.addCookie(cookie);
        }
        return "/delete_cookies.jsp";
    }
}
