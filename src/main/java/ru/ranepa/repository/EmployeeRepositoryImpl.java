package ru.ranepa.repository;

import ru.ranepa.model.Employee;
import java.util.*;

// Реализация хранения репозитория сотрудников
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final Map<Long, Employee> employees = new HashMap<>();
    private long nextId = 1;

    // Сохранение данных о сотруднике в хранилище
    @Override
    public Employee save(Employee employee) {
        employee.setId(nextId);
        employees.put(nextId, employee);
        nextId++;
        return employee;
    }
// Поиск сотрудника по ID
    @Override
    public Optional<Employee> findById(long id) {
        return Optional.ofNullable(employees.get(id));
    }
// Получение данных о всех сотрудниках
    @Override
    public Iterable<Employee> findAll() {
        return employees.values();
    }

// Удаление сотрудника по ID
    @Override
    public String delete(long id) {
        Employee removed = employees.remove(id);
        if (removed != null) {
            return "Сотрудник " + id + " удален";
        }
        return "Сотрудник с ID " + id + " не найден";
    }
}