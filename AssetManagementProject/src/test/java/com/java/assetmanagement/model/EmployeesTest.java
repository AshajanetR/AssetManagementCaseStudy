package com.java.assetmanagement.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class EmployeesTest {

	@Test
	public void testconstructor() {
		Employees e  =  new Employees();
		assertNotNull(e);
		Employees emp  =  new Employees(1,"Alice Johnson","HR","alice.johnson@example.com","Pass@123");
		assertEquals(1, emp.getEmployee_id());
		assertEquals("Alice Johnson", emp.getName());
		assertEquals("HR", emp.getDepartment());
		assertEquals("alice.johnson@example.com", emp.getEmail());
		assertEquals("Pass@123", emp.getPassword());
	}

	
	@Test
	public void testgettersandsetters() {
		Employees emp  =  new Employees();
		emp.setEmployee_id(1);
		emp.setName("Alice Johnson");
		emp.setDepartment("HR");
		emp.setEmail("alice.johnson@example.com");
		emp.setPassword("Pass@123");
		
		assertEquals(1, emp.getEmployee_id());
		assertEquals("Alice Johnson", emp.getName());
		assertEquals("HR", emp.getDepartment());
		assertEquals("alice.johnson@example.com", emp.getEmail());
		assertEquals("Pass@123", emp.getPassword());
		
	}
	
	@Test
	public void testtostring() {
		Employees emp  =  new Employees(1,"Alice Johnson","HR","alice.johnson@example.com","Pass@123");
		String res = "Employees [employee_id=1, name=Alice Johnson, department=HR, email=alice.johnson@example.com, password=Pass@123]";
		assertEquals(res, emp.toString());

	}
}