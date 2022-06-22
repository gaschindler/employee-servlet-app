package com.revature.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

public class EmployeeServiceTests {

	private EmployeeService eserv;
	private EmployeeDao mockdao;
	
	@Before
	public void setup() {
		mockdao = mock(EmployeeDao.class);
		eserv = new EmployeeService(mockdao);
	}
	
	@After
	public void teardown() {
		mockdao = null;
		eserv = null;
	}
	
	@Test
	public void testConfirmLogin_success() {
		// create a fake list of emps
		// this is the dummy data we feed to mockito
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green");
		Employee e2 = new Employee(21, "Clint", "Barton", "hawkeye", "arrows");
		
		List<Employee> emps = new ArrayList<>();
		emps.add(e1);
		emps.add(e2);
		
		// setup the dao's behavior for findAll()
		when(mockdao.findAll()).thenReturn(emps);
		
		
		// capture the actual output of the method
		Employee actual = eserv.confirmLogin("thehulk", "green");
		
		// capture the expected output of the method
		Employee expected = e1;
		
		// assert expected == actual
		assertEquals(expected, actual);
	}
	
	@Test
	public void testConfirmLogin_failure() {
		// create a fake list of emps
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green");
		Employee e2 = new Employee(21, "Clint", "Barton", "hawkeye", "arrows");
		
		List<Employee> emps = new ArrayList<>();
		emps.add(e1);
		emps.add(e2);
		
		// setup the dao's behavior for findAll()
		when(mockdao.findAll()).thenReturn(emps);
		
		// capture the actual output of the method
		Employee actual = eserv.confirmLogin("captain", "america");
		
		// capture the expected output of the method
		Employee expected = new Employee();
		
		assertEquals(expected, actual);
	}
	
}
