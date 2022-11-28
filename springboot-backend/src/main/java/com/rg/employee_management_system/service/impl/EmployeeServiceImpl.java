package com.rg.employee_management_system.service.impl;

import com.rg.employee_management_system.io.entity.EmployeeEntity;
import com.rg.employee_management_system.io.repositories.EmployeeRepository;
import com.rg.employee_management_system.model.response.EmployeeResp;
import com.rg.employee_management_system.service.EmployeeService;
import com.rg.employee_management_system.shared.Utils;
import com.rg.employee_management_system.shared.dto.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employee) {

        if(employeeRepository.findByEmail(employee.getEmail())!=null)
            throw new RuntimeException("Record already exists!!");

        String publicUserId = utils.generateUserId(30);
        EmployeeEntity employeeEntity = new EmployeeEntity();
        ModelMapper modelMapper = new ModelMapper();

        employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
        employeeEntity.setUserId(publicUserId);

        EmployeeEntity storedEmployee = employeeRepository.save(employeeEntity);
        EmployeeDto returnValue = modelMapper.map(storedEmployee,EmployeeDto.class);

        return returnValue;
    }

    @Override
    public EmployeeDto getEmployeeByUserId(String userId) {
        EmployeeEntity findedEmployee = new EmployeeEntity();
        ModelMapper model = new ModelMapper();
        findedEmployee = employeeRepository.findByUserId(userId);
        if(findedEmployee==null)
            throw new UsernameNotFoundException("employee has not find");
        EmployeeDto findEmployee = new EmployeeDto();
        findEmployee = model.map(findedEmployee,EmployeeDto.class);
        return findEmployee;
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        EmployeeDto returnValue = new EmployeeDto();
        ModelMapper model = new ModelMapper();

        EmployeeEntity employeeEntity = new EmployeeEntity();
        Optional<EmployeeEntity> findedEmployee =
                Optional.ofNullable(employeeRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("Employee not found")));

        employeeEntity = findedEmployee.get();
        employeeEntity.setFirstName(employeeDto.getFirstName());
        employeeEntity.setLastName(employeeDto.getLastName());
        employeeEntity.setEmail(employeeDto.getEmail());


        EmployeeEntity updatedEmployee = new EmployeeEntity();
        updatedEmployee=employeeRepository.save(employeeEntity);
        returnValue = model.map(updatedEmployee,EmployeeDto.class);

        return returnValue;
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        EmployeeEntity returnValue = new EmployeeEntity();
        ModelMapper model = new ModelMapper();

        Optional<EmployeeEntity> findedEmployee =
                Optional.ofNullable(employeeRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("Employee not found")));

        returnValue = findedEmployee.get();
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto = model.map(returnValue, EmployeeDto.class);

        return employeeDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
