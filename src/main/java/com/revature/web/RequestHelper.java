package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

public class RequestHelper {

	private static EmployeeService eserv = new EmployeeService(new EmployeeDao());
	private static ObjectMapper om = new ObjectMapper();
	
	/**
	 * -- Extracts the parameters from a request (username and password) from the UI
	 * -- Call the confirmLogin() method from the EmployeeService and
	 * 		see if a user with that username and password exists
	 */
	public static void processLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// extract the parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// call confirmLogin() from the EmployeeService and see what it returns
		Employee e = eserv.confirmLogin(username, password);
		
		// if the user exists, add them to the session and print their info to the screen
		if (e.getId() > 0) {
			// grab the session
			HttpSession session = request.getSession();
			
			// add the user to the session
			session.setAttribute("the-user", e);
			
			// print out the user's data with PrintWriter
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<h1>Welcome " + e.getFirstName() + "!</h1>");
			out.println("<h3>You have successfully logged in!</h3>");
			
			// can print out the object as a JSON string
			String jsonString = om.writeValueAsString(e);
			out.println(jsonString);
		} else {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
//			response.setStatus(204); // 204 means successful connection to the server, but no content found
			out.println("<h3>No user found, sorry.</h3>");
		}
	}
	
	public static void processRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// extract all values from the parameters
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// construct a new Employee object
		Employee e = new Employee(firstname, lastname, username, password);
		
		// call the register() method from the service layer
		int pk = eserv.register(e);
		
		// check its ID... if it's > 0 it's successful
		if (pk > 0) {
			e.setId(pk);
			
			HttpSession session = request.getSession();
			session.setAttribute("the-user", e);
			
			request.getRequestDispatcher("welcome.html").forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			
			out.println("<h1>Registration failed. User already exists.</h1>");
			out.println("<a href=\"index.html\">Back</a>");
		}
	}
	
	/**
	 * This method will call the EmployeeService's getAll method()
	 * and use an ObjectMapper to transform that list to a JSON String
	 * and use the PrinterWriter to print out that JSON string to
	 * the screen
	 */
	public static void processEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("application/json");
		
		List<Employee> emps = eserv.getAll();
		
		String jsonString = om.writeValueAsString(emps);
		
		PrintWriter out = response.getWriter();
		// notice write function instead of println
		out.write(jsonString);
	}
	
}
