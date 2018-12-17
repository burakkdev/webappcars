package ccp.util;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import ccp.business.Cart;
import ccp.business.LineItem;
import ccp.business.Product;
import ccp.business.User;

public class ListenAttributesCarParts implements HttpSessionAttributeListener {

	// Creates new SessionAttribListen
	public ListenAttributesCarParts() {

		System.out.println(getClass().getName());
	}

	public void attributeAdded(HttpSessionBindingEvent se) {

		HttpSession session = se.getSession();
		if (se.getName() == "product") {
			String id = session.getId();
			String name = se.getName();
			Product product = (Product) se.getValue();
			String source = se.getSource().getClass().getName();
			String message = new StringBuffer("Attribute bound to session in ").append(source)
					.append("\nThe attribute name: ").append(name).append("\n").append("The attribute value:")
					.append(product.getCarName()).append("\n").append("The session ID: ").append(id).toString();

			System.out.println(message);
		} else if (se.getName() == "cart") {
			String id = session.getId();
			String name = se.getName();
			Cart cart = (Cart) se.getValue();
			List<LineItem> lines = cart.getItems();
			String source = se.getSource().getClass().getName();

			String messageFirst = new StringBuffer("Attribute bound to session in ").append(source)
					.append("\nThe attribute name: ").append(name).append("\n").toString();
			System.out.println(messageFirst);
			
			LineItem line = lines.get(0);
			
			String message = new StringBuffer("").append("The attribute value:").append(line.getProduct().getCarName())
							 .append("\n").append("The session ID: ").append(id).toString();
			
			System.out.println(message);
		} else if (se.getName() == "user") {
			String id = session.getId();
			String name = se.getName();
			User user = (User) se.getValue();
			String source = se.getSource().getClass().getName();

			String message = new StringBuffer("Attribute bound to session in ").append(source)
					.append("\nThe attribute name: ").append(name).append("\n").append("The attribute value:")
					.append(user.getFirstName()).append("+").append(user.getLastName()).append("\n")
					.append("The session ID: ").append(id).toString();
			System.out.println(message);
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent se) {

		HttpSession session = se.getSession();

		String id = session.getId();

		String name = se.getName();

		if (name == null)
			name = "Unknown";
       
		// In here if session coming with Cart object , do the required casting operation
		// for (String) se.getValue();
		String value = (String) se.getValue();

		String source = se.getSource().getClass().getName();

		String message = new StringBuffer("Attribute unbound from session in ").append(source)
				.append("\nThe attribute name: ").append(name).append("\n").append("The attribute value: ")
				.append(value).append("\n").append("The session ID: ").append(id).toString();

		System.out.println(message);
	}

	public void attributeReplaced(HttpSessionBindingEvent se) {

		String source = se.getSource().getClass().getName();

		String message_replaced = new StringBuffer("Attribute replaced in session  ").append(source).toString();

		HttpSession session = se.getSession();
		String id = session.getId();
		String name = se.getName();
		

		if (se.getName() == "cart") {
			Cart cart = (Cart) se.getValue();
			List<LineItem> lines = cart.getItems();
			String messageFirst = new StringBuffer("Attribute bound to session in ").append(source)
					.append("\nThe attribute name: ").append(name).append("\n").toString();
			System.out.println(messageFirst);
			Iterator t = lines.iterator();
			while (t.hasNext()) {
				LineItem line = (LineItem) t.next();
				String messageSecond = new StringBuffer("The attribute value:").append(line.getProduct().getCarName())
						.toString();
				System.out.println(messageSecond);
			}
			String message = new StringBuffer("").append("\n").append("The session ID: ").append(id).toString();
			System.out.println(message);
		}

		System.out.println(message_replaced);
	}
}