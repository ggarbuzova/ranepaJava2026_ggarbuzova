package ru.ranepa.repository;

import ru.ranepa.model.Employee;
import java.util.Optional;

public interface EmployeeRepository {
    Employee save(Employee employee);
    Optional<Employee> findById(long id);
    Iterable<Employee> findAll();
    String delete(long id);
}