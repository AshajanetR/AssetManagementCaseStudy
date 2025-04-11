package com.java.assetmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class Employees {
	private int employee_id;
	private String name;
	private String department;
	private String email;
	private String password;
	
	
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Employees(int employee_id, String name, String department, String email, String password) {
		this.employee_id = employee_id;
		this.name = name;
		this.department = department;
		this.email = email;
		this.password = password;
	}
	public Employees() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Employees [employee_id=" + employee_id + ", name=" + name + ", department=" + department + ", email="
				+ email + ", password=" + password + "]";
	}
	
	
}
