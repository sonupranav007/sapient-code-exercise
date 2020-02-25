package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Employee;
import com.example.demo.utils.CacheUtils;

@Service
public class EmployeeService {
	
	private List<Employee> employeeList = new ArrayList<>(); 
	
	@Autowired
	CacheUtils cacheUtils;
	
	public EmployeeService() {}
	
	public EmployeeService( List<Employee> employeeList ) {
		this.employeeList = employeeList;
	}
	
	@PostConstruct
	public void init() {
		employeeList = cacheUtils.readEmployeeFromCache();
	}
	
	/**
	 * 
	 * @param place
	 * @param percentage
	 * @return
	 * @throws Exception
	 */
	public List<Employee> updateSalary( String place, double percentage ) throws Exception{
		
		List<Employee> filteredList = employeeList.stream()
			      .filter(empl -> empl.getPlace().equals(place))
			        .collect(Collectors.toList());
		
		if( filteredList.isEmpty() ) {
			throw new Exception ( "Incorrect place entered");
		}
		filteredList.stream().forEach(emp-> emp.setSalary(emp.getSalary()+emp.getSalary()*percentage/100));
		
		return employeeList;
	}
	
	/**
	 * 
	 * @param empid
	 * @return
	 */
	public String getNestedSupervisee( String empid ) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(empid);
		List<String> empids = employeeList.stream().filter( emp ->emp.getSupervisorId().
				equals(empid)).map(Employee::getId).collect(Collectors.toList());
		if( empids.isEmpty() ) {
			return buffer.toString();
		}else {
			for( String emp : empids ) {
				return buffer.append("->").append(getNestedSupervisee(emp)).toString();
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public double getTotalSalary( String key, String value) {
		switch (key) {
		case "businessUnit":
			return employeeList.stream().filter( emp -> emp.getBusinessUnit().
					equals(value)).collect(Collectors.summingDouble(Employee::getSalary));
			
		case "supervisor":
			return employeeList.stream().filter( emp -> emp.getSupervisorId().
					equals(value)).mapToDouble(Employee::getSalary).sum();
			
		case "place":
			return employeeList.stream().filter( emp -> emp.getPlace().
					equals(value)).mapToDouble(Employee::getSalary).sum();

		default:
			return 0;
		}
		
	}
	
	/**
	 * 
	 * @param place
	 * @return
	 */
	public List<Employee> getAllEmployee( String place ){
		return employeeList.stream().
				filter( emp -> emp.getPlace().equals(place)).collect(Collectors.toList());
	}
	
	
	public String getRangeofSalaryForTitle(String title) {
		OptionalDouble min = employeeList.stream().
		filter(emp -> emp.getTitle().equals(title)).mapToDouble(Employee::getSalary).min();
		
		OptionalDouble max = employeeList.stream().
				filter(emp -> emp.getTitle().equals(title)).mapToDouble(Employee::getSalary).max();
		
		return min.getAsDouble() +" - "+max.getAsDouble();
	}
	
	
	/**
	 * 
	 */
	@PreDestroy
	  public void onExit() {
	    try {
	    	System.out.println("shutting down");
	    	cacheUtils.writeToCache(employeeList);
	      Thread.sleep(5 * 1000);
	    } catch (InterruptedException e) {
	    }
	  }

	
	

}
