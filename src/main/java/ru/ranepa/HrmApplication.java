package ru.ranepa;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.HRMService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

// Точка входа в приложение
public class HrmApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // Главный метод  - запуск синициальзация
    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepositoryImpl();
        HRMService service = new HRMService(repository);

        System.out.println("=== HRM System Started ===");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            // Обрабодчик выбора пользователя
            switch (choice) {
                case "1" -> showAllEmployees(service);
                case "2" -> addEmployee(service);
                case "3" -> deleteEmployee(service);
                case "4" -> findEmployee(service);
                case "5" -> showStatistics(service);
                case "6" -> {
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
            System.out.println();
        }
        scanner.close();
    }

    // Вывод меню в приложение
    private static void printMenu() {
        System.out.println("=== HRM System Menu ===");
        System.out.println("1. Show all employees");
        System.out.println("2. Add employee");
        System.out.println("3. Delete employee by ID");
        System.out.println("4. Find employee by ID");
        System.out.println("5. Show statistics");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    // Отображение списка всех сотрудников
    private static void showAllEmployees(HRMService service) {
        List<Employee> employees = service.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.println("\n--- Employee List ---");
        employees.forEach(System.out::println);
    }

    // Ввод данных о сотруднике через консоль
    private static void addEmployee(HRMService service) {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter position: ");
            String position = scanner.nextLine();
            System.out.print("Enter salary: ");
            double salary = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter hire date (dd.MM.yyyy): ");
            String dateInput = scanner.nextLine();
            LocalDate hireDate = LocalDate.parse(dateInput, DATE_FORMAT);

            Employee added = service.addEmployee(name, position, salary, hireDate);
            System.out.println("Employee added successfully with ID: " + added.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Удаление сотрудника по ID
    private static void deleteEmployee(HRMService service) {
        try {
            System.out.print("Enter employee ID to delete: ");
            long id = Long.parseLong(scanner.nextLine());
            boolean removed = service.removeEmployee(id);
            System.out.println(removed ? "Employee deleted." : "Employee not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Поиск сотрудника по ID c использованием Optional
    private static void findEmployee(HRMService service) {
        try {
            System.out.print("Enter employee ID to find: ");
            long id = Long.parseLong(scanner.nextLine());
            Optional<Employee> found = service.getEmployeeById(id);
            found.ifPresentOrElse(
                    emp -> System.out.println("Found: " + emp),
                    () -> System.out.println("Employee not found.")
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Вывод статистики (Средняя ЗП + самыый высокооплачиваемый сотрудник)
    private static void showStatistics(HRMService service) {
        List<Employee> employees = service.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees to analyze.");
            return;
        }
        System.out.println("Average salary: " + service.calculateAverageSalary() + " RUB");
        service.findHighestPaidEmployee().ifPresent(emp ->
                System.out.println("Highest paid: " + emp.getName() + " - " + emp.getSalary() + " RUB")
        );
    }
}