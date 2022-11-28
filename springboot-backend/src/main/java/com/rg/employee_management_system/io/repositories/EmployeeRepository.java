package com.rg.employee_management_system.io.repositories;

import com.rg.employee_management_system.io.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    EmployeeEntity findByEmail(String email);
    EmployeeEntity findByUserId(String userId);
}
