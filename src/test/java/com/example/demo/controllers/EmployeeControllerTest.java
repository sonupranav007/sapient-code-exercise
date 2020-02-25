package com.example.demo.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService service;
	
	private static List<Employee> empList ;
	
	@BeforeAll
	public static void setUp() {
		empList = createEmployeeList();
	}
	
	
	@Test
	public void testUpdateEmployee() throws Exception {
	  when(service.updateSalary("BLR", 20)).thenReturn(empList);
	  this.mockMvc.perform( put("/employee/place/BLR/salary/20")).andDo(print()).andExpect(status().isOk())
	  .andExpect(content().string(containsString("Pranav")));
	  
		
	}
	
	@Test
	public void testInvalidUpdateEmployee() throws Exception {
	  when(service.updateSalary("place", 20)).thenThrow(new Exception("Incorrect place entered"));
	  this.mockMvc.perform( put("/employee/place/place/salary/20")).andDo(print())
	  .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Incorrect place entered")));;
	  
	  
		
	}
	
	@Test
	public void testTotalSalaryByBusinessUnit() throws Exception {
	  when(service.getTotalSalary("businessUnit", "BU1")).thenReturn(2000.0);
	  this.mockMvc.perform( get("/employee/businessunit/BU1/salary")).andDo(print()).andExpect(status().isOk())
	  .andExpect(content().string(containsString("2000.0")));
	  
	}
	
	
	@Test
	public void testTotalSalaryBySupervisorId() throws Exception {
	  when(service.getTotalSalary("supervisor", "12")).thenReturn(100000.0);
	  this.mockMvc.perform( get("/employee/supervisor/12/salary")).andDo(print()).andExpect(status().isOk())
	  .andExpect(content().string(containsString("100000.0")));
	  
	}
	
	
	@Test
	public void testTotalSalaryByPlace() throws Exception {
	  when(service.getTotalSalary("place", "BLR")).thenReturn(100000.0);
	  this.mockMvc.perform( get("/employee/place/BLR/salary")).andDo(print()).andExpect(status().isOk())
	  .andExpect(content().string(containsString("100000.0")));
	  
	}
	
	
	@Test
	public void testGetAllEmployeeofGivenPlace() throws Exception {
	  when(service.getAllEmployee( "BLR")).thenReturn(empList);
	  this.mockMvc.perform( get("/employee/place/BLR/")).andDo(print()).andExpect(status().isOk())
	  .andExpect(content().string(containsString("BLR")));
	  
	}
	
	@Test
	public void testGetRangeofSalaryForTitle() throws Exception {
	  when(service.getRangeofSalaryForTitle("VP")).thenReturn("10000-20000");
	  this.mockMvc.perform( get("/employee/salary/title/VP/")).andDo(print()).andExpect(status().isOk())
	  .andExpect(content().string(containsString("10000-20000")));
	  
	}
	
	
	private static List<Employee> createEmployeeList(){
		List<Employee> empList = new ArrayList<>();
		
		Employee emp = new Employee("1", "Pranav", "totle", "businessUnit", 
				"BLR", "0", "java", 100);
		
		empList.add(emp);
		return empList;
		
	}

}



