package ru.ranepa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class HRMServiceTest {

    private HRMService service;

    @BeforeEach
    void setUp() {
        EmployeeRepository repository = new EmployeeRepositoryImpl();
        service = new HRMService(repository);
    }

    // Тест 1: расчет средней зп + проверка вычисления и округления
    @Test
    void shouldCalculateAverageSalary() {
        service.addEmployee("Anna", "QA", 100.0, LocalDate.now());
        service.addEmployee("Bob", "Dev", 200.0, LocalDate.now());
        service.addEmployee("Carl", "Manager", 300.0, LocalDate.now());

        BigDecimal average = service.calculateAverageSalary();

        assertEquals(0, average.compareTo(new BigDecimal("200.00")),
                "Средняя зарплата должна быть 200.00");
    }

    // Тест 2: Поиск самого высокооплачиваемого сотрудника
    @Test
    void shouldFindHighestPaidEmployee() {
        service.addEmployee("Anna", "QA", 100.0, LocalDate.now());
        service.addEmployee("Bob", "Dev", 300.0, LocalDate.now());
        service.addEmployee("Carl", "Manager", 200.0, LocalDate.now());

        Optional<Employee> topEarner = service.findHighestPaidEmployee();

        assertTrue(topEarner.isPresent(), "Должен быть найден сотрудник");
        Employee bestEmployee = topEarner.get();
        assertEquals("Bob", bestEmployee.getName(), "Самый высокооплачиваемый — Bob");
        assertEquals(0, bestEmployee.getSalary().compareTo(new BigDecimal("300.0")),
                "Зарплата должна быть 300.0");
    }

    // Текст 3: Расчет средней зп для пустого сотрудника
    @Test
    void shouldReturnZeroForEmptySalaryList() {
        BigDecimal average = service.calculateAverageSalary();
        assertEquals(BigDecimal.ZERO, average, "Средняя зарплата при пустом списке = 0");
    }
}