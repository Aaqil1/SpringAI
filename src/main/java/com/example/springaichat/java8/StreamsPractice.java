package com.example.springaichat.java8;

import java.util.*;
import java.util.stream.*;

/**
 * Java 8 Streams Practice Template
 * 
 * This file contains sample data and comments for practice.
 * Complete the TODO sections by writing the stream operations.
 * 
 * Instructions:
 * 1. Read each comment carefully
 * 2. Write the stream operation in the TODO section
 * 3. Test your implementation
 * 4. Compare with solutions if needed
 */
public class StreamsPractice {

    public static void main(String[] args) {
        System.out.println("=== Java 8 Streams Practice ===\n");
        
        // Sample data - DO NOT MODIFY
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace");
        List<Employee> employees = Arrays.asList(
            new Employee("John", 25, 50000, "IT"),
            new Employee("Jane", 30, 60000, "HR"),
            new Employee("Bob", 28, 55000, "IT"),
            new Employee("Alice", 35, 70000, "Finance"),
            new Employee("Charlie", 22, 45000, "IT"),
            new Employee("Diana", 32, 65000, "HR"),
            new Employee("Eve", 29, 58000, "Finance")
        );
        
        List<List<Integer>> nestedLists = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5, 6),
            Arrays.asList(7, 8, 9, 10)
        );
        
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
        List<Integer> numbersWithDuplicates = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5);
        
        // ========== PRACTICE PROBLEMS ==========
        
        practice1_FilterEvenNumbers(numbers);
        practice2_MapToSquares(numbers);
        practice3_FilterNamesByLength(names);
        practice4_ConvertToUpperCase(names);
        practice5_GetEmployeeNames(employees);
        practice6_FilterITEmployees(employees);
        practice7_GetEmployeeSalaries(employees);
        practice8_FilterHighSalaryEmployees(employees);
        practice9_GetDistinctNumbers(numbersWithDuplicates);
        practice10_SortNamesAlphabetically(names);
        practice11_SortEmployeesByAge(employees);
        practice12_SortEmployeesBySalaryDescending(employees);
        practice13_LimitFirstFive(numbers);
        practice14_SkipFirstThree(numbers);
        practice15_FlatMapNestedLists(nestedLists);
        practice16_CountITEmployees(employees);
        practice17_SumAllNumbers(numbers);
        practice18_FindMaxNumber(numbers);
        practice19_FindMinNumber(numbers);
        practice20_AverageSalary(employees);
        practice21_CheckAnyEmployeeOver30(employees);
        practice22_CheckAllEmployeesOver20(employees);
        practice23_CheckNoEmployeeOver50(employees);
        practice24_FindFirstITEmployee(employees);
        practice25_FindAnyITEmployee(employees);
        practice26_CollectToList(numbers);
        practice27_CollectToSet(numbersWithDuplicates);
        practice28_CollectToMap(employees);
        practice29_GroupByDepartment(employees);
        practice30_GroupByDepartmentWithCount(employees);
        practice31_PartitionByAge(employees);
        practice32_JoinNames(names);
        practice33_JoinNamesWithSeparator(names);
        practice34_SumSalariesByDepartment(employees);
        practice35_FindMaxSalaryEmployee(employees);
        practice36_FindMinSalaryEmployee(employees);
        practice37_GetTop3Salaries(employees);
        practice38_GetEmployeesWithNameStartingWith(employees, "J");
        practice39_GetUniqueDepartments(employees);
        practice40_GetTotalSalary(employees);
    }

    // ========== BASIC OPERATIONS ==========
    
    /**
     * Practice 1: Filter even numbers from the list
     * Expected output: [2, 4, 6, 8, 10]
     */
    private static void practice1_FilterEvenNumbers(List<Integer> numbers) {
        System.out.println("Practice 1: Filter even numbers");
        // TODO: Write stream operation to filter even numbers and print them
        // Hint: Use filter() with lambda expression
        
        
        System.out.println();
    }
    
    /**
     * Practice 2: Map each number to its square
     * Expected output: [1, 4, 9, 16, 25, 36, 49, 64, 81, 100]
     */
    private static void practice2_MapToSquares(List<Integer> numbers) {
        System.out.println("Practice 2: Map numbers to squares");
        // TODO: Write stream operation to map each number to its square and print
        
        
        System.out.println();
    }
    
    /**
     * Practice 3: Filter names that have length greater than 4
     * Expected output: [Alice, Charlie, David, Frank, Grace]
     */
    private static void practice3_FilterNamesByLength(List<String> names) {
        System.out.println("Practice 3: Filter names with length > 4");
        // TODO: Write stream operation to filter names by length
        
        
        System.out.println();
    }
    
    /**
     * Practice 4: Convert all names to uppercase
     * Expected output: [ALICE, BOB, CHARLIE, DAVID, EVE, FRANK, GRACE]
     */
    private static void practice4_ConvertToUpperCase(List<String> names) {
        System.out.println("Practice 4: Convert names to uppercase");
        // TODO: Write stream operation to convert names to uppercase
        
        
        System.out.println();
    }
    
    /**
     * Practice 5: Get all employee names
     * Expected output: [John, Jane, Bob, Alice, Charlie, Diana, Eve]
     */
    private static void practice5_GetEmployeeNames(List<Employee> employees) {
        System.out.println("Practice 5: Get all employee names");
        // TODO: Write stream operation to extract employee names
        // Hint: Use map() with method reference Employee::getName
        
        
        System.out.println();
    }
    
    /**
     * Practice 6: Filter employees from IT department
     * Expected output: [John(IT), Bob(IT), Charlie(IT)]
     */
    private static void practice6_FilterITEmployees(List<Employee> employees) {
        System.out.println("Practice 6: Filter IT employees");
        // TODO: Write stream operation to filter IT department employees
        
        
        System.out.println();
    }
    
    /**
     * Practice 7: Get all employee salaries
     * Expected output: [50000, 60000, 55000, 70000, 45000, 65000, 58000]
     */
    private static void practice7_GetEmployeeSalaries(List<Employee> employees) {
        System.out.println("Practice 7: Get all employee salaries");
        // TODO: Write stream operation to extract salaries
        
        
        System.out.println();
    }
    
    /**
     * Practice 8: Filter employees with salary greater than 55000
     * Expected output: [Jane(60000), Alice(70000), Diana(65000), Eve(58000)]
     */
    private static void practice8_FilterHighSalaryEmployees(List<Employee> employees) {
        System.out.println("Practice 8: Filter high salary employees (>55000)");
        // TODO: Write stream operation to filter employees with salary > 55000
        
        
        System.out.println();
    }
    
    // ========== DISTINCT AND SORTING ==========
    
    /**
     * Practice 9: Get distinct numbers (remove duplicates)
     * Expected output: [1, 2, 3, 4, 5]
     */
    private static void practice9_GetDistinctNumbers(List<Integer> numbersWithDuplicates) {
        System.out.println("Practice 9: Get distinct numbers");
        // TODO: Write stream operation to get unique numbers
        
        
        System.out.println();
    }
    
    /**
     * Practice 10: Sort names alphabetically
     * Expected output: [Alice, Bob, Charlie, David, Eve, Frank, Grace]
     */
    private static void practice10_SortNamesAlphabetically(List<String> names) {
        System.out.println("Practice 10: Sort names alphabetically");
        // TODO: Write stream operation to sort names
        
        
        System.out.println();
    }
    
    /**
     * Practice 11: Sort employees by age (ascending)
     * Expected output: [Charlie(22), John(25), Bob(28), Eve(29), Jane(30), Diana(32), Alice(35)]
     */
    private static void practice11_SortEmployeesByAge(List<Employee> employees) {
        System.out.println("Practice 11: Sort employees by age");
        // TODO: Write stream operation to sort employees by age
        // Hint: Use sorted() with Comparator.comparing()
        
        
        System.out.println();
    }
    
    /**
     * Practice 12: Sort employees by salary (descending)
     * Expected output: [Alice(70000), Diana(65000), Jane(60000), Eve(58000), Bob(55000), John(50000), Charlie(45000)]
     */
    private static void practice12_SortEmployeesBySalaryDescending(List<Employee> employees) {
        System.out.println("Practice 12: Sort employees by salary (descending)");
        // TODO: Write stream operation to sort employees by salary in descending order
        // Hint: Use Comparator.comparing().reversed()
        
        
        System.out.println();
    }
    
    // ========== LIMIT AND SKIP ==========
    
    /**
     * Practice 13: Get first 5 numbers
     * Expected output: [1, 2, 3, 4, 5]
     */
    private static void practice13_LimitFirstFive(List<Integer> numbers) {
        System.out.println("Practice 13: Get first 5 numbers");
        // TODO: Write stream operation to limit to first 5 numbers
        
        
        System.out.println();
    }
    
    /**
     * Practice 14: Skip first 3 numbers
     * Expected output: [4, 5, 6, 7, 8, 9, 10]
     */
    private static void practice14_SkipFirstThree(List<Integer> numbers) {
        System.out.println("Practice 14: Skip first 3 numbers");
        // TODO: Write stream operation to skip first 3 numbers
        
        
        System.out.println();
    }
    
    // ========== FLATMAP ==========
    
    /**
     * Practice 15: Flatten nested lists into a single list
     * Expected output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
     */
    private static void practice15_FlatMapNestedLists(List<List<Integer>> nestedLists) {
        System.out.println("Practice 15: Flatten nested lists");
        // TODO: Write stream operation to flatten nested lists
        // Hint: Use flatMap() with List::stream
        
        
        System.out.println();
    }
    
    // ========== TERMINAL OPERATIONS - COUNT, SUM, MIN, MAX ==========
    
    /**
     * Practice 16: Count IT employees
     * Expected output: 3
     */
    private static void practice16_CountITEmployees(List<Employee> employees) {
        System.out.println("Practice 16: Count IT employees");
        // TODO: Write stream operation to count IT employees
        // Hint: Use filter() then count()
        long count = 0;
        // TODO: Write your code here
        
        System.out.println("Count: " + count + "\n");
    }
    
    /**
     * Practice 17: Sum all numbers
     * Expected output: 55
     */
    private static void practice17_SumAllNumbers(List<Integer> numbers) {
        System.out.println("Practice 17: Sum all numbers");
        // TODO: Write stream operation to sum all numbers
        // Hint: Use reduce() or mapToInt().sum()
        int sum = 0;
        // TODO: Write your code here
        
        System.out.println("Sum: " + sum + "\n");
    }
    
    /**
     * Practice 18: Find maximum number
     * Expected output: 10
     */
    private static void practice18_FindMaxNumber(List<Integer> numbers) {
        System.out.println("Practice 18: Find maximum number");
        // TODO: Write stream operation to find maximum number
        // Hint: Use max() or reduce()
        Optional<Integer> max = Optional.empty();
        // TODO: Write your code here
        
        max.ifPresent(m -> System.out.println("Max: " + m + "\n"));
    }
    
    /**
     * Practice 19: Find minimum number
     * Expected output: 1
     */
    private static void practice19_FindMinNumber(List<Integer> numbers) {
        System.out.println("Practice 19: Find minimum number");
        // TODO: Write stream operation to find minimum number
        Optional<Integer> min = Optional.empty();
        // TODO: Write your code here
        
        min.ifPresent(m -> System.out.println("Min: " + m + "\n"));
    }
    
    /**
     * Practice 20: Calculate average salary
     * Expected output: ~57571.43
     */
    private static void practice20_AverageSalary(List<Employee> employees) {
        System.out.println("Practice 20: Calculate average salary");
        // TODO: Write stream operation to calculate average salary
        // Hint: Use mapToInt() then average()
        OptionalDouble average = OptionalDouble.empty();
        // TODO: Write your code here
        
        average.ifPresent(avg -> System.out.println("Average: " + avg + "\n"));
    }
    
    // ========== MATCH OPERATIONS ==========
    
    /**
     * Practice 21: Check if any employee is over 30 years old
     * Expected output: true
     */
    private static void practice21_CheckAnyEmployeeOver30(List<Employee> employees) {
        System.out.println("Practice 21: Check if any employee is over 30");
        // TODO: Write stream operation to check if any employee age > 30
        boolean result = false;
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    /**
     * Practice 22: Check if all employees are over 20 years old
     * Expected output: true
     */
    private static void practice22_CheckAllEmployeesOver20(List<Employee> employees) {
        System.out.println("Practice 22: Check if all employees are over 20");
        // TODO: Write stream operation to check if all employees age > 20
        boolean result = false;
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    /**
     * Practice 23: Check if no employee is over 50 years old
     * Expected output: true
     */
    private static void practice23_CheckNoEmployeeOver50(List<Employee> employees) {
        System.out.println("Practice 23: Check if no employee is over 50");
        // TODO: Write stream operation to check if no employee age > 50
        boolean result = false;
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    // ========== FIND OPERATIONS ==========
    
    /**
     * Practice 24: Find first IT employee
     * Expected output: John(IT)
     */
    private static void practice24_FindFirstITEmployee(List<Employee> employees) {
        System.out.println("Practice 24: Find first IT employee");
        // TODO: Write stream operation to find first IT employee
        Optional<Employee> first = Optional.empty();
        // TODO: Write your code here
        
        first.ifPresent(emp -> System.out.println("First IT: " + emp + "\n"));
    }
    
    /**
     * Practice 25: Find any IT employee
     * Expected output: Any IT employee
     */
    private static void practice25_FindAnyITEmployee(List<Employee> employees) {
        System.out.println("Practice 25: Find any IT employee");
        // TODO: Write stream operation to find any IT employee
        Optional<Employee> any = Optional.empty();
        // TODO: Write your code here
        
        any.ifPresent(emp -> System.out.println("Any IT: " + emp + "\n"));
    }
    
    // ========== COLLECT OPERATIONS ==========
    
    /**
     * Practice 26: Collect filtered numbers to a new list
     * Filter even numbers and collect to list
     * Expected output: [2, 4, 6, 8, 10]
     */
    private static void practice26_CollectToList(List<Integer> numbers) {
        System.out.println("Practice 26: Collect even numbers to list");
        // TODO: Write stream operation to filter even numbers and collect to list
        List<Integer> result = new ArrayList<>();
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    /**
     * Practice 27: Collect numbers to a set (removes duplicates)
     * Expected output: [1, 2, 3, 4, 5]
     */
    private static void practice27_CollectToSet(List<Integer> numbersWithDuplicates) {
        System.out.println("Practice 27: Collect numbers to set");
        // TODO: Write stream operation to collect to set
        Set<Integer> result = new HashSet<>();
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    /**
     * Practice 28: Collect employees to a map (name -> salary)
     * Expected output: {John=50000, Jane=60000, Bob=55000, ...}
     */
    private static void practice28_CollectToMap(List<Employee> employees) {
        System.out.println("Practice 28: Collect employees to map (name -> salary)");
        // TODO: Write stream operation to collect to map
        // Hint: Use Collectors.toMap(Employee::getName, Employee::getSalary)
        Map<String, Integer> result = new HashMap<>();
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    /**
     * Practice 29: Group employees by department
     * Expected output: {IT=[John, Bob, Charlie], HR=[Jane, Diana], Finance=[Alice, Eve]}
     */
    private static void practice29_GroupByDepartment(List<Employee> employees) {
        System.out.println("Practice 29: Group employees by department");
        // TODO: Write stream operation to group by department
        // Hint: Use Collectors.groupingBy(Employee::getDepartment)
        Map<String, List<Employee>> result = new HashMap<>();
        // TODO: Write your code here
        
        result.forEach((dept, emps) -> 
            System.out.println(dept + ": " + emps));
        System.out.println();
    }
    
    /**
     * Practice 30: Group by department and count employees in each
     * Expected output: {IT=3, HR=2, Finance=2}
     */
    private static void practice30_GroupByDepartmentWithCount(List<Employee> employees) {
        System.out.println("Practice 30: Group by department with count");
        // TODO: Write stream operation to group by department and count
        // Hint: Use Collectors.groupingBy() with Collectors.counting()
        Map<String, Long> result = new HashMap<>();
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    /**
     * Practice 31: Partition employees by age (>= 30)
     * Expected output: {true=[Jane, Alice, Diana], false=[John, Bob, Charlie, Eve]}
     */
    private static void practice31_PartitionByAge(List<Employee> employees) {
        System.out.println("Practice 31: Partition employees by age (>= 30)");
        // TODO: Write stream operation to partition by age >= 30
        // Hint: Use Collectors.partitioningBy()
        Map<Boolean, List<Employee>> result = new HashMap<>();
        // TODO: Write your code here
        
        System.out.println("Age >= 30: " + result.get(true));
        System.out.println("Age < 30: " + result.get(false) + "\n");
    }
    
    /**
     * Practice 32: Join names with comma
     * Expected output: Alice,Bob,Charlie,David,Eve,Frank,Grace
     */
    private static void practice32_JoinNames(List<String> names) {
        System.out.println("Practice 32: Join names with comma");
        // TODO: Write stream operation to join names
        // Hint: Use Collectors.joining(",")
        String result = "";
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    /**
     * Practice 33: Join names with custom separator and prefix/suffix
     * Expected output: [Alice, Bob, Charlie, David, Eve, Frank, Grace]
     */
    private static void practice33_JoinNamesWithSeparator(List<String> names) {
        System.out.println("Practice 33: Join names with prefix and suffix");
        // TODO: Write stream operation to join with "[", ", ", "]"
        // Hint: Use Collectors.joining(", ", "[", "]")
        String result = "";
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    // ========== ADVANCED OPERATIONS ==========
    
    /**
     * Practice 34: Sum salaries by department
     * Expected output: {IT=150000, HR=125000, Finance=128000}
     */
    private static void practice34_SumSalariesByDepartment(List<Employee> employees) {
        System.out.println("Practice 34: Sum salaries by department");
        // TODO: Write stream operation to group by department and sum salaries
        // Hint: Use Collectors.groupingBy() with Collectors.summingInt()
        Map<String, Integer> result = new HashMap<>();
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    /**
     * Practice 35: Find employee with maximum salary
     * Expected output: Alice(70000)
     */
    private static void practice35_FindMaxSalaryEmployee(List<Employee> employees) {
        System.out.println("Practice 35: Find employee with maximum salary");
        // TODO: Write stream operation to find employee with max salary
        // Hint: Use max() with Comparator.comparing(Employee::getSalary)
        Optional<Employee> result = Optional.empty();
        // TODO: Write your code here
        
        result.ifPresent(emp -> System.out.println("Max salary: " + emp + "\n"));
    }
    
    /**
     * Practice 36: Find employee with minimum salary
     * Expected output: Charlie(45000)
     */
    private static void practice36_FindMinSalaryEmployee(List<Employee> employees) {
        System.out.println("Practice 36: Find employee with minimum salary");
        // TODO: Write stream operation to find employee with min salary
        Optional<Employee> result = Optional.empty();
        // TODO: Write your code here
        
        result.ifPresent(emp -> System.out.println("Min salary: " + emp + "\n"));
    }
    
    /**
     * Practice 37: Get top 3 highest salaries
     * Expected output: [70000, 65000, 60000]
     */
    private static void practice37_GetTop3Salaries(List<Employee> employees) {
        System.out.println("Practice 37: Get top 3 highest salaries");
        // TODO: Write stream operation to get top 3 salaries
        // Hint: Sort by salary descending, limit to 3, extract salaries
        List<Integer> result = new ArrayList<>();
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    /**
     * Practice 38: Get employees whose name starts with given letter
     * Expected output: [John, Jane]
     */
    private static void practice38_GetEmployeesWithNameStartingWith(List<Employee> employees, String prefix) {
        System.out.println("Practice 38: Get employees with name starting with '" + prefix + "'");
        // TODO: Write stream operation to filter employees by name prefix
        // Hint: Use filter() with startsWith()
        List<Employee> result = new ArrayList<>();
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    /**
     * Practice 39: Get unique departments
     * Expected output: [IT, HR, Finance]
     */
    private static void practice39_GetUniqueDepartments(List<Employee> employees) {
        System.out.println("Practice 39: Get unique departments");
        // TODO: Write stream operation to get unique departments
        // Hint: Map to department, use distinct(), collect to list
        List<String> result = new ArrayList<>();
        // TODO: Write your code here
        
        System.out.println("Result: " + result + "\n");
    }
    
    /**
     * Practice 40: Get total salary of all employees
     * Expected output: 403000
     */
    private static void practice40_GetTotalSalary(List<Employee> employees) {
        System.out.println("Practice 40: Get total salary of all employees");
        // TODO: Write stream operation to sum all salaries
        // Hint: Map to salary, use sum() or reduce()
        int total = 0;
        // TODO: Write your code here
        
        System.out.println("Total: " + total + "\n");
    }
    
    // Employee class for practice
    static class Employee {
        private String name;
        private int age;
        private int salary;
        private String department;

        public Employee(String name, int age, int salary, String department) {
            this.name = name;
            this.age = age;
            this.salary = salary;
            this.department = department;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
        public int getSalary() { return salary; }
        public String getDepartment() { return department; }

        @Override
        public String toString() {
            return name + "(" + salary + ", " + department + ")";
        }
    }
}

