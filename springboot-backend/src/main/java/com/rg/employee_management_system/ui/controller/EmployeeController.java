package com.rg.employee_management_system.ui.controller;

import com.rg.employee_management_system.io.entity.EmployeeEntity;
import com.rg.employee_management_system.io.repositories.EmployeeRepository;
import com.rg.employee_management_system.model.Employee;
import com.rg.employee_management_system.model.request.EmployeeDetailsRequest;
import com.rg.employee_management_system.model.response.EmployeeResp;
import com.rg.employee_management_system.service.EmployeeService;
import com.rg.employee_management_system.shared.dto.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employee_api/v1")
public class EmployeeController {

    private List<Employee> theEmployees;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<EmployeeEntity> showAllEmployee(){
      return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public EmployeeResp createEmployee(@RequestBody EmployeeDetailsRequest employeeDetailsRequest){
       EmployeeResp returnValue = new EmployeeResp();

        ModelMapper model = new ModelMapper();
        EmployeeDto employeeDto = model.map(employeeDetailsRequest,EmployeeDto.class);
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);

        returnValue = model.map(createdEmployee,EmployeeResp.class);
        return returnValue;
    }

    @PutMapping("/employees/{id}")
    public EmployeeResp updateEmployee(@PathVariable Long id, @RequestBody EmployeeDetailsRequest employeeDetailsRequest){
        EmployeeResp returnValue = new EmployeeResp();
        ModelMapper model = new ModelMapper();

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto = model.map(employeeDetailsRequest,EmployeeDto.class);
        EmployeeDto updatedEmployee = new EmployeeDto();
        updatedEmployee=employeeService.updateEmployee(id, employeeDto);
        returnValue= model.map(updatedEmployee,EmployeeResp.class);

        return returnValue;
    }
    @GetMapping("/employees/{id}")
    public EmployeeResp getEmployee(@PathVariable Long id){
        EmployeeResp returnValue = new EmployeeResp();
        ModelMapper model = new ModelMapper();

        EmployeeDto findEmployee = employeeService.getEmployeeById(id);
        returnValue = model.map(findEmployee,EmployeeResp.class);
        return returnValue;
    }
}
