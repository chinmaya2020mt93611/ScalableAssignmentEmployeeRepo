package com.scalable.assignment;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssignmentController {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@GetMapping("/employee/getDetails")
	public String index() {
		String sql = "SELECT * FROM EMPLOYEE";
        
		List<Map<String, Object>> employees = jdbcTemplate.queryForList(sql);
		StringBuilder sb = new StringBuilder();
		
		if (employees!=null && !employees.isEmpty()) {
			
			for (Map<String, Object> employee : employees) {
				
				for (Iterator<Map.Entry<String, Object>> it = employee.entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, Object> entry = it.next();
					String key = entry.getKey();
					Object value = entry.getValue();
					sb.append(key + " = " + value + "<br>");
				}
				sb.append("<br>");
				sb.append("------------------------------");
				sb.append("<br>");
			}
			
		}
		return sb.toString();
	}
	
	
	  @GetMapping("/createTable") public void tableCreate() { 
		  String sql = "CREATE TABLE EMPLOYEE(EmployeeId TEXT PRIMARY KEY NOT NULL, EmployeeName TEXT NOT NULL, EmployeeDOB TEXT NOT NULL, EmployeeDepartment TEXT NOT NULL)";
	  
		  jdbcTemplate.execute(sql); 
	  }
	 
	
	@PostMapping("/add/employeeDetails")
	public void addPatient(@RequestBody EmployeeRecord request, HttpServletResponse response) {
		String sql = "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, request.employeeId, request.employeeName, request.employeeDOB, request.employeeDepartment);
	}

}