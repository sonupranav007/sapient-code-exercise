package com.example.demo.model;

import java.io.Serializable;

public class Employee implements Serializable {
	
	private String id;
	private String name;
	private String title;
	private String businessUnit;
	private String place;
	private String supervisorId;
	private String competencies;
	private double salary;
	
	public Employee ( String id, String name, String title, String businessUnit, String place, 
						String supervisorId, String competencies ,double salary) {
		this.id = id;
		this.name = name;
		this.title = title;
		this.businessUnit = businessUnit;
		this.place = place;
		this.supervisorId = supervisorId;
		this.competencies = competencies;
		this.salary = salary;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public String getSupervisorId() {
		return supervisorId;
	}
	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}
	public String getCompetencies() {
		return competencies;
	}
	public void setCompetencies(String competencies) {
		this.competencies = competencies;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	
	

}
