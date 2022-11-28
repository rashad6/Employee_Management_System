package com.rg.employee_management_system.service;

import com.rg.employee_management_system.shared.dto.EmployeeDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService extends UserDetailsService{
    EmployeeDto createEmployee(EmployeeDto employee);
    EmployeeDto getEmployeeByUserId(String userId);
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(Long id);

}