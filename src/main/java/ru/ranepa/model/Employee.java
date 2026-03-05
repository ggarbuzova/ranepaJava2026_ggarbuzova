package ru.ranepa.model;

import java.math.BigDecimal;
import java.time.LocalDate;

// Модель сотрудник
public class Employee {
    private Long id;
    private String name;
    private String position;
    private BigDecimal salary;
    private LocalDate hireDate;

    // Конструктор для заполнения карточки сотрудник
    public Employee(String name, String position, double salary, LocalDate hireDate) {
        this.name = name;
        this.position = position;
        this.salary = BigDecimal.valueOf(salary);
        this.hireDate = hireDate;
    }
    // Геттеры/сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    // Вывод в консоль
    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', position='" + position +
                "', salary=" + salary + ", hireDate=" + hireDate + "}";
    }
}