package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

// Слой Бизнес-логики
public class HRMService {
    private final EmployeeRepository repository;

    public HRMService(EmployeeRepository repository) {
        this.repository = repository;
    }

// Добавление нового сотрудника с проверкой
    public Employee addEmployee(String name, String position, double salary, LocalDate hireDate) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        Employee employee = new Employee(name, position, salary, hireDate);
        repository.save(employee);
        return employee;
    }

// Поиск сотрудника по ID
    public Optional<Employee> getEmployeeById(long id) {
        return repository.findById(id);
    }

// Получение всех сотрудников
    public List<Employee> getAllEmployees() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

// Удаление сотрудника по ID
    public boolean removeEmployee(long id) {
        String result = repository.delete(id);
        return result.contains("удален");
    }

// Расчет средней зарплаты для всех сотрудников
    public BigDecimal calculateAverageSalary() {
        List<Employee> employees = getAllEmployees();
        if (employees.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSalary.divide(
                BigDecimal.valueOf(employees.size()),
                2,
                java.math.RoundingMode.HALF_UP
        );
    }

// Поиск сотрудника с max зарплатой
    public Optional<Employee> findHighestPaidEmployee() {
        return getAllEmployees().stream()
                .max((e1, e2) -> e1.getSalary().compareTo(e2.getSalary()));
    }

// Фильтрация сотрудников по должности
    public List<Employee> findByPosition(String position) {
        return getAllEmployees().stream()
                .filter(e -> e.getPosition().equalsIgnoreCase(position))
                .toList();
    }
}