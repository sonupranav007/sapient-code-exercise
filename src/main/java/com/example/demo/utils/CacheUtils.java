package com.example.demo.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.example.demo.model.Employee;

@Component
public class CacheUtils {

	@Value("${csv.file.name}")
	private String csvFileName;

	@Value("${cache.file.name}")
	private String cacheFile;

	@Autowired
	ResourceLoader resourceLoader;

	public List<Employee> readEmployeeFromCache() {
		List<Employee> employees = new ArrayList<>();
		Resource cacheResource = resourceLoader.getResource("classpath:" + cacheFile);

		if (cacheResource.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cacheResource.getFile()))) {
				employees = (List<Employee>) ois.readObject();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			Resource resource = resourceLoader.getResource("classpath:" + csvFileName);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {

				String line = br.readLine();

				while (line != null) {

					String[] attributes = line.split(",");
					Employee employee = createEmployee(attributes);
					employees.add(employee);

					line = br.readLine();
				}

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return employees;
	}

	private Employee createEmployee(String[] metadata) {
		String id = metadata[0];
		String name = metadata[1];
		String title = metadata[2];
		String businessUnit = metadata[3];
		String place = metadata[4];
		String supervisorId = metadata[5];
		String competencies = metadata[6];
		double salary = 0.0;
		try {
			salary = Double.parseDouble(metadata[7]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return new Employee(id, name, title, businessUnit, place, supervisorId, competencies, salary);
	}

	public void writeToCache(List<Employee> employeeList) {
		Resource cacheResource = resourceLoader.getResource("classpath:" + cacheFile);

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cacheResource.getFile()))) {
			oos.writeObject(employeeList);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

}
