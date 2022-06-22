package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Employee;
import com.revature.util.HibernateUtil;

public class EmployeeDao {

	// C.R.U.D. Methods
	
	// (Create) a new Employee object and persist them to the DB
	public int insert(Employee e) {
		// grab the session object
		Session ses = HibernateUtil.getSession();
		
		// begin a tx
		Transaction tx = ses.beginTransaction();
		
		// capture the pk returned when the session method save() is called
		int pk = (int) ses.save(e);
		
		tx.commit();
		
		return pk;
	}
	
	// (Read) all Employees in the DB and return them in a list
	public List<Employee> findAll() {
		// grab the session object
		Session ses = HibernateUtil.getSession();
		
		// make an HQL -- Hibernate Query Language: odd mix of OOP & native SQL
		List<Employee> emps = ses.createQuery("from Employee", Employee.class).list();
		
		// return the list of employees
		return emps;
	}
	
	// (Update) the corresponding Employee in the DB
	public boolean update(Employee e) {
		return false;
	}
	
	// (Delete) the Employee in the DB with the passed id
	public boolean delete(int id) {
		return false;
	}
	
}
