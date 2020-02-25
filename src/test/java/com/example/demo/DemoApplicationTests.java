package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controllers.EmployeeController;

@SpringBootTest
class DemoApplicationTests {
	
	@Autowired
	private EmployeeController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
