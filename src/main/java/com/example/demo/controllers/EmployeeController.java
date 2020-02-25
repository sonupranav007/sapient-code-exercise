package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	
	@PutMapping("/employee/place/{place}/salary/{percentage}")
	public ResponseEntity updateEmployee( @PathVariable("place") String place,
										  @PathVariable("percentage") double percentage){
		
		List<Employee> employeeList;
		try {
			employeeList = employeeService.updateSalary(place, percentage);
			return ResponseEntity.ok(employeeList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/employee/{empid}/supervisees")
	public ResponseEntity getNestedSuperVisee( @PathVariable("empid") String empid ) {
		
		return ResponseEntity.ok(employeeService.getNestedSupervisee(empid));
	}
	
	@GetMapping("/employee/businessunit/{businessUnit}/salary")
	public ResponseEntity getTotalSalaryByBusinessUnit( @PathVariable("businessUnit") String bu ) {
		double totalSalary = employeeService.getTotalSalary("businessUnit", bu);
		return ResponseEntity.ok(totalSalary);
	}
	
	@GetMapping("/employee/supervisor/{supervisor}/salary")
	public ResponseEntity getTotalSalaryBySupervisorId( @PathVariable("supervisor") String supid ) {
		
		return ResponseEntity.ok(employeeService.getTotalSalary("supervisor", supid));
	}
	
	@GetMapping("/employee/place/{place}/salary")
	public ResponseEntity getTotalSalaryByPlace( @PathVariable("place") String place ) {
		
		return ResponseEntity.ok(employeeService.getTotalSalary("place", place));
	}
	
	
	@GetMapping("/employee/place/{place}/")
	public ResponseEntity getAllEmployeeofGivenPlace( @PathVariable("place") String place ) {
		
		return ResponseEntity.ok(employeeService.getAllEmployee(place));
	}
	
	@GetMapping("/employee/salary/title/{title}")
	public ResponseEntity getRangeofSalaryForTitle( @PathVariable("title") String title ) {
		
		return ResponseEntity.ok(employeeService.getRangeofSalaryForTitle(title));
	}

}
