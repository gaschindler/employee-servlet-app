package com.revature.service;

import java.util.List;
import java.util.Optional;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;

public class EmployeeService {

	private EmployeeDao edao;
	
	/**
	 * Dependency Injection via Constructor Injection
	 * 
	 * Constructor Injection is a sophisticated way of ensuring
	 * that the EmployeeService object ALWAYS has an EmployeeDao object
	 */
	public EmployeeService(EmployeeDao edao) {
		this.edao = edao;
	}
	
	/**
	 * Return the Employee object with the passed
	 * username and password
	 */
	public Employee confirmLogin(String username, String password) {
		// stream through all the employees that are returned
		Optional<Employee> possibleEmp = edao.findAll().stream()
				.filter(e -> (e.getUsername().equals(username) && e.getPassword().equals(password)))
				.findFirst();
		
		// if the employee is present, return it, otherwise return empty Employee object with id of 0
		return possibleEmp.isPresent() ? possibleEmp.get() : new Employee();
		// ideally this is optimized and set up with a custom exception
	}
	
	/**
	 * Returns the full list of all Employees
	 */
	public List<Employee> getAll() {
		return edao.findAll();
	}
	
	/**
	 * Return the primary key of the newly inserted Employee,
	 * or -1 if the insert failed
	 */
	public int register(Employee e) {
		return edao.insert(e);
	}
	
}
