package com.example.springaichat.streams;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Comprehensive Java 8 Streams demonstration class for interview preparation.
 * Covers all major stream operations, functional interfaces, and best practices.
 */
public class Java8StreamsDemo {

    public static void main(String[] args) {
        System.out.println("=== Java 8 Streams Complete Demo ===\n");
        
        // Sample data
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve", "Frank");
        List<Employee> employees = Arrays.asList(
            new Employee("John", 25, 50000, "IT"),
            new Employee("Jane", 30, 60000, "HR"),
            new Employee("Bob", 28, 55000, "IT"),
            new Employee("Alice", 35, 70000, "Finance"),
            new Employee("Charlie", 22, 45000, "IT")
        );

        // 1. BASIC STREAM OPERATIONS
        demonstrateBasicOperations(numbers, names);
        
        // 2. INTERMEDIATE OPERATIONS
        demonstrateIntermediateOperations(numbers, names, employees);
        
        // 3. TERMINAL OPERATIONS
        demonstrateTerminalOperations(numbers, names, employees);
        
        // 4. COLLECTORS
        demonstrateCollectors(employees);
        
        // 5. OPTIONAL
        demonstrateOptional();
        
        // 6. FUNCTIONAL INTERFACES
        demonstrateFunctionalInterfaces();
        
        // 7. METHOD REFERENCES
        demonstrateMethodReferences(names);
        
        // 8. PARALLEL STREAMS
        demonstrateParallelStreams(numbers);
        
        // 9. ADVANCED OPERATIONS
        demonstrateAdvancedOperations(employees);
    }

    // ========== 1. BASIC STREAM OPERATIONS ==========
    private static void demonstrateBasicOperations(List<Integer> numbers, List<String> names) {
        System.out.println("1. BASIC STREAM OPERATIONS");
        System.out.println("-------------------------");
        
        // filter() - Filter elements based on predicate
        System.out.println("filter() - Even numbers:");
        numbers.stream()
            .filter(n -> n % 2 == 0)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // map() - Transform each element
        System.out.println("map() - Square of numbers:");
        numbers.stream()
            .map(n -> n * n)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // mapToInt(), mapToLong(), mapToDouble() - Primitive streams
        System.out.println("mapToInt() - Sum of squares:");
        int sum = numbers.stream()
            .mapToInt(n -> n * n)
            .sum();
        System.out.println("Sum: " + sum + "\n");
        
        // flatMap() - Flatten nested collections
        System.out.println("flatMap() - Flatten nested lists:");
        List<List<Integer>> nestedList = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5, 6),
            Arrays.asList(7, 8, 9)
        );
        nestedList.stream()
            .flatMap(List::stream)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // distinct() - Remove duplicates
        System.out.println("distinct() - Unique numbers:");
        Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4).stream()
            .distinct()
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // sorted() - Sort elements
        System.out.println("sorted() - Sorted names:");
        names.stream()
            .sorted()
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // sorted() with Comparator
        System.out.println("sorted() with Comparator - Reverse order:");
        names.stream()
            .sorted(Comparator.reverseOrder())
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n\n");
    }

    // ========== 2. INTERMEDIATE OPERATIONS ==========
    private static void demonstrateIntermediateOperations(
            List<Integer> numbers, List<String> names, List<Employee> employees) {
        System.out.println("2. INTERMEDIATE OPERATIONS");
        System.out.println("-------------------------");
        
        // limit() - Limit the number of elements
        System.out.println("limit() - First 5 numbers:");
        numbers.stream()
            .limit(5)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // skip() - Skip first n elements
        System.out.println("skip() - Skip first 3 numbers:");
        numbers.stream()
            .skip(3)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // peek() - Perform side effects without modifying stream
        System.out.println("peek() - Debug stream:");
        numbers.stream()
            .filter(n -> n > 5)
            .peek(n -> System.out.println("Filtered: " + n))
            .map(n -> n * 2)
            .peek(n -> System.out.println("Mapped: " + n))
            .forEach(n -> System.out.println("Final: " + n));
        System.out.println();
        
        // takeWhile() - Take elements while predicate is true (Java 9+)
        System.out.println("takeWhile() - Take while < 5:");
        numbers.stream()
            .takeWhile(n -> n < 5)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // dropWhile() - Drop elements while predicate is true (Java 9+)
        System.out.println("dropWhile() - Drop while < 5:");
        numbers.stream()
            .dropWhile(n -> n < 5)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n\n");
    }

    // ========== 3. TERMINAL OPERATIONS ==========
    private static void demonstrateTerminalOperations(
            List<Integer> numbers, List<String> names, List<Employee> employees) {
        System.out.println("3. TERMINAL OPERATIONS");
        System.out.println("----------------------");
        
        // forEach() - Iterate over elements
        System.out.println("forEach() - Print all numbers:");
        numbers.stream().forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // forEachOrdered() - Maintain order in parallel streams
        System.out.println("forEachOrdered() - Maintain order:");
        numbers.parallelStream()
            .forEachOrdered(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // collect() - Collect to collection
        System.out.println("collect() - Collect to List:");
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println(evenNumbers + "\n");
        
        // reduce() - Reduce to single value
        System.out.println("reduce() - Sum of numbers:");
        int sum = numbers.stream()
            .reduce(0, Integer::sum);
        System.out.println("Sum: " + sum + "\n");
        
        // reduce() with identity and accumulator
        System.out.println("reduce() - Product of numbers:");
        int product = numbers.stream()
            .reduce(1, (a, b) -> a * b);
        System.out.println("Product: " + product + "\n");
        
        // reduce() with combiner (for parallel streams)
        System.out.println("reduce() - Max value:");
        Optional<Integer> max = numbers.stream()
            .reduce(Integer::max);
        System.out.println("Max: " + max.orElse(0) + "\n");
        
        // count() - Count elements
        System.out.println("count() - Count even numbers:");
        long count = numbers.stream()
            .filter(n -> n % 2 == 0)
            .count();
        System.out.println("Count: " + count + "\n");
        
        // anyMatch() - Check if any element matches
        System.out.println("anyMatch() - Any number > 5:");
        boolean anyMatch = numbers.stream()
            .anyMatch(n -> n > 5);
        System.out.println("Result: " + anyMatch + "\n");
        
        // allMatch() - Check if all elements match
        System.out.println("allMatch() - All numbers > 0:");
        boolean allMatch = numbers.stream()
            .allMatch(n -> n > 0);
        System.out.println("Result: " + allMatch + "\n");
        
        // noneMatch() - Check if no elements match
        System.out.println("noneMatch() - No number > 10:");
        boolean noneMatch = numbers.stream()
            .noneMatch(n -> n > 10);
        System.out.println("Result: " + noneMatch + "\n");
        
        // findFirst() - Get first element
        System.out.println("findFirst() - First even number:");
        Optional<Integer> first = numbers.stream()
            .filter(n -> n % 2 == 0)
            .findFirst();
        System.out.println("First: " + first.orElse(-1) + "\n");
        
        // findAny() - Get any element (useful in parallel)
        System.out.println("findAny() - Any even number:");
        Optional<Integer> any = numbers.stream()
            .filter(n -> n % 2 == 0)
            .findAny();
        System.out.println("Any: " + any.orElse(-1) + "\n");
        
        // min() - Find minimum
        System.out.println("min() - Minimum number:");
        Optional<Integer> min = numbers.stream()
            .min(Integer::compareTo);
        System.out.println("Min: " + min.orElse(-1) + "\n");
        
        // max() - Find maximum
        System.out.println("max() - Maximum number:");
        Optional<Integer> max = numbers.stream()
            .max(Integer::compareTo);
        System.out.println("Max: " + max.orElse(-1) + "\n");
        
        // sum() - Sum of elements (IntStream, LongStream, DoubleStream)
        System.out.println("sum() - Sum of numbers:");
        int sum2 = numbers.stream()
            .mapToInt(Integer::intValue)
            .sum();
        System.out.println("Sum: " + sum2 + "\n");
        
        // average() - Average of elements
        System.out.println("average() - Average of numbers:");
        OptionalDouble avg = numbers.stream()
            .mapToInt(Integer::intValue)
            .average();
        System.out.println("Average: " + avg.orElse(0.0) + "\n");
        
        // summaryStatistics() - Get statistics
        System.out.println("summaryStatistics() - Statistics:");
        IntSummaryStatistics stats = numbers.stream()
            .mapToInt(Integer::intValue)
            .summaryStatistics();
        System.out.println("Count: " + stats.getCount());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Max: " + stats.getMax());
        System.out.println("Average: " + stats.getAverage() + "\n");
    }

    // ========== 4. COLLECTORS ==========
    private static void demonstrateCollectors(List<Employee> employees) {
        System.out.println("4. COLLECTORS");
        System.out.println("-------------");
        
        // toList()
        System.out.println("toList() - Collect to List:");
        List<String> names = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.toList());
        System.out.println(names + "\n");
        
        // toSet()
        System.out.println("toSet() - Collect to Set:");
        Set<String> departments = employees.stream()
            .map(Employee::getDepartment)
            .collect(Collectors.toSet());
        System.out.println(departments + "\n");
        
        // toMap()
        System.out.println("toMap() - Collect to Map:");
        Map<String, Integer> nameToAge = employees.stream()
            .collect(Collectors.toMap(Employee::getName, Employee::getAge));
        System.out.println(nameToAge + "\n");
        
        // groupingBy() - Group by department
        System.out.println("groupingBy() - Group by department:");
        Map<String, List<Employee>> byDept = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment));
        byDept.forEach((dept, emps) -> 
            System.out.println(dept + ": " + emps));
        System.out.println();
        
        // groupingBy() with counting
        System.out.println("groupingBy() with counting:");
        Map<String, Long> deptCount = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        System.out.println(deptCount + "\n");
        
        // partitioningBy() - Partition by predicate
        System.out.println("partitioningBy() - Partition by age >= 30:");
        Map<Boolean, List<Employee>> partitioned = employees.stream()
            .collect(Collectors.partitioningBy(e -> e.getAge() >= 30));
        System.out.println("Age >= 30: " + partitioned.get(true));
        System.out.println("Age < 30: " + partitioned.get(false) + "\n");
        
        // joining() - Join strings
        System.out.println("joining() - Join names:");
        String joined = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.joining(", "));
        System.out.println(joined + "\n");
        
        // averagingInt() - Average salary
        System.out.println("averagingInt() - Average salary:");
        Double avgSalary = employees.stream()
            .collect(Collectors.averagingInt(Employee::getSalary));
        System.out.println("Average: " + avgSalary + "\n");
        
        // summingInt() - Sum of salaries
        System.out.println("summingInt() - Sum of salaries:");
        Integer totalSalary = employees.stream()
            .collect(Collectors.summingInt(Employee::getSalary));
        System.out.println("Total: " + totalSalary + "\n");
        
        // maxBy() - Employee with max salary
        System.out.println("maxBy() - Employee with max salary:");
        Optional<Employee> maxSalary = employees.stream()
            .collect(Collectors.maxBy(Comparator.comparing(Employee::getSalary)));
        maxSalary.ifPresent(e -> System.out.println(e.getName() + "\n"));
        
        // minBy() - Employee with min salary
        System.out.println("minBy() - Employee with min salary:");
        Optional<Employee> minSalary = employees.stream()
            .collect(Collectors.minBy(Comparator.comparing(Employee::getSalary)));
        minSalary.ifPresent(e -> System.out.println(e.getName() + "\n"));
        
        // collectingAndThen() - Collect and transform
        System.out.println("collectingAndThen() - Collect and get count:");
        Long count = employees.stream()
            .collect(Collectors.collectingAndThen(Collectors.counting(), c -> c * 2));
        System.out.println("Count * 2: " + count + "\n");
    }

    // ========== 5. OPTIONAL ==========
    private static void demonstrateOptional() {
        System.out.println("5. OPTIONAL");
        System.out.println("-----------");
        
        // Creating Optional
        Optional<String> empty = Optional.empty();
        Optional<String> of = Optional.of("Hello");
        Optional<String> ofNullable = Optional.ofNullable(null);
        
        // isPresent() and isEmpty()
        System.out.println("isPresent() - Check if value exists:");
        System.out.println("empty.isPresent(): " + empty.isPresent());
        System.out.println("of.isPresent(): " + of.isPresent() + "\n");
        
        // get() - Get value (unsafe)
        System.out.println("get() - Get value:");
        if (of.isPresent()) {
            System.out.println("Value: " + of.get() + "\n");
        }
        
        // orElse() - Default value
        System.out.println("orElse() - Default value:");
        String value = empty.orElse("Default");
        System.out.println("Value: " + value + "\n");
        
        // orElseGet() - Default from supplier
        System.out.println("orElseGet() - Default from supplier:");
        String value2 = empty.orElseGet(() -> "Generated Default");
        System.out.println("Value: " + value2 + "\n");
        
        // orElseThrow() - Throw exception if empty
        System.out.println("orElseThrow() - Throw if empty:");
        try {
            String value3 = empty.orElseThrow(() -> new RuntimeException("No value"));
        } catch (RuntimeException e) {
            System.out.println("Exception: " + e.getMessage() + "\n");
        }
        
        // ifPresent() - Execute if present
        System.out.println("ifPresent() - Execute if present:");
        of.ifPresent(v -> System.out.println("Value: " + v + "\n"));
        
        // ifPresentOrElse() - Execute if present, else execute other
        System.out.println("ifPresentOrElse() - Execute if present or else:");
        empty.ifPresentOrElse(
            v -> System.out.println("Value: " + v),
            () -> System.out.println("No value\n")
        );
        
        // map() - Transform value
        System.out.println("map() - Transform value:");
        Optional<String> upper = of.map(String::toUpperCase);
        upper.ifPresent(v -> System.out.println("Upper: " + v + "\n"));
        
        // flatMap() - Flatten nested Optional
        System.out.println("flatMap() - Flatten nested Optional:");
        Optional<String> flatMapped = of.flatMap(s -> Optional.of(s + " World"));
        flatMapped.ifPresent(v -> System.out.println("FlatMapped: " + v + "\n"));
        
        // filter() - Filter value
        System.out.println("filter() - Filter value:");
        Optional<String> filtered = of.filter(s -> s.length() > 3);
        filtered.ifPresent(v -> System.out.println("Filtered: " + v + "\n"));
    }

    // ========== 6. FUNCTIONAL INTERFACES ==========
    private static void demonstrateFunctionalInterfaces() {
        System.out.println("6. FUNCTIONAL INTERFACES");
        System.out.println("------------------------");
        
        // Predicate<T> - Returns boolean
        System.out.println("Predicate<T> - Test condition:");
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("Is 4 even? " + isEven.test(4));
        System.out.println("Is 5 even? " + isEven.test(5));
        System.out.println("Negate: " + isEven.negate().test(4));
        System.out.println("And: " + isEven.and(n -> n > 5).test(6));
        System.out.println("Or: " + isEven.or(n -> n < 3).test(1) + "\n");
        
        // Function<T, R> - Transform input to output
        System.out.println("Function<T, R> - Transform:");
        Function<Integer, Integer> square = n -> n * n;
        Function<Integer, Integer> addOne = n -> n + 1;
        System.out.println("Square of 5: " + square.apply(5));
        System.out.println("Compose (add then square): " + square.compose(addOne).apply(4));
        System.out.println("AndThen (square then add): " + square.andThen(addOne).apply(4) + "\n");
        
        // Consumer<T> - Accept input, no return
        System.out.println("Consumer<T> - Consume value:");
        Consumer<String> print = System.out::println;
        Consumer<String> printUpper = s -> System.out.println(s.toUpperCase());
        print.andThen(printUpper).accept("Hello\n");
        
        // Supplier<T> - Supply value, no input
        System.out.println("Supplier<T> - Supply value:");
        Supplier<Double> random = Math::random;
        System.out.println("Random: " + random.get() + "\n");
        
        // BiFunction<T, U, R> - Two inputs, one output
        System.out.println("BiFunction<T, U, R> - Two inputs:");
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("Add 5 and 3: " + add.apply(5, 3) + "\n");
        
        // BinaryOperator<T> - Two inputs of same type, same output type
        System.out.println("BinaryOperator<T> - Binary operation:");
        BinaryOperator<Integer> multiply = (a, b) -> a * b;
        System.out.println("Multiply 4 and 5: " + multiply.apply(4, 5) + "\n");
        
        // UnaryOperator<T> - One input, same type output
        System.out.println("UnaryOperator<T> - Unary operation:");
        UnaryOperator<Integer> doubleValue = n -> n * 2;
        System.out.println("Double 7: " + doubleValue.apply(7) + "\n");
    }

    // ========== 7. METHOD REFERENCES ==========
    private static void demonstrateMethodReferences(List<String> names) {
        System.out.println("7. METHOD REFERENCES");
        System.out.println("---------------------");
        
        // Static method reference
        System.out.println("Static method reference - Integer.parseInt:");
        List<String> numbers = Arrays.asList("1", "2", "3", "4", "5");
        List<Integer> parsed = numbers.stream()
            .map(Integer::parseInt)
            .collect(Collectors.toList());
        System.out.println(parsed + "\n");
        
        // Instance method reference on specific object
        System.out.println("Instance method reference - System.out.println:");
        names.forEach(System.out::println);
        System.out.println();
        
        // Instance method reference on arbitrary object
        System.out.println("Instance method reference - String.toUpperCase:");
        List<String> upper = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println(upper + "\n");
        
        // Constructor reference
        System.out.println("Constructor reference - String::new:");
        List<String> copied = names.stream()
            .map(String::new)
            .collect(Collectors.toList());
        System.out.println(copied + "\n");
    }

    // ========== 8. PARALLEL STREAMS ==========
    private static void demonstrateParallelStreams(List<Integer> numbers) {
        System.out.println("8. PARALLEL STREAMS");
        System.out.println("--------------------");
        
        // parallel() - Convert to parallel stream
        System.out.println("parallel() - Parallel processing:");
        long start = System.currentTimeMillis();
        int sum = numbers.parallelStream()
            .mapToInt(n -> {
                // Simulate some work
                try { Thread.sleep(10); } catch (InterruptedException e) {}
                return n * 2;
            })
            .sum();
        long end = System.currentTimeMillis();
        System.out.println("Sum: " + sum + ", Time: " + (end - start) + "ms\n");
        
        // sequential() - Convert to sequential stream
        System.out.println("sequential() - Sequential processing:");
        List<Integer> sequential = numbers.stream()
            .sequential()
            .map(n -> n * 2)
            .collect(Collectors.toList());
        System.out.println(sequential + "\n");
        
        // isParallel() - Check if stream is parallel
        System.out.println("isParallel() - Check stream type:");
        System.out.println("Parallel: " + numbers.parallelStream().isParallel());
        System.out.println("Sequential: " + numbers.stream().isParallel() + "\n");
    }

    // ========== 9. ADVANCED OPERATIONS ==========
    private static void demonstrateAdvancedOperations(List<Employee> employees) {
        System.out.println("9. ADVANCED OPERATIONS");
        System.out.println("----------------------");
        
        // Chaining multiple operations
        System.out.println("Chaining - IT employees with salary > 50000:");
        List<String> itEmployees = employees.stream()
            .filter(e -> "IT".equals(e.getDepartment()))
            .filter(e -> e.getSalary() > 50000)
            .map(Employee::getName)
            .sorted()
            .collect(Collectors.toList());
        System.out.println(itEmployees + "\n");
        
        // Custom Collector
        System.out.println("Custom Collector - Collect to custom structure:");
        List<String> custom = employees.stream()
            .collect(Collector.of(
                ArrayList::new,
                (list, emp) -> list.add(emp.getName()),
                (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                },
                Collector.Characteristics.IDENTITY_FINISH
            ));
        System.out.println(custom + "\n");
        
        // Stream of primitives
        System.out.println("Primitive Streams - IntStream operations:");
        IntStream.range(1, 10)
            .filter(n -> n % 2 == 0)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // Stream.generate() - Infinite stream
        System.out.println("Stream.generate() - Generate random numbers:");
        Stream.generate(() -> (int)(Math.random() * 100))
            .limit(5)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // Stream.iterate() - Iterate with seed
        System.out.println("Stream.iterate() - Fibonacci-like sequence:");
        Stream.iterate(0, n -> n + 2)
            .limit(10)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // Stream.concat() - Concatenate streams
        System.out.println("Stream.concat() - Concatenate two streams:");
        Stream<Integer> stream1 = Stream.of(1, 2, 3);
        Stream<Integer> stream2 = Stream.of(4, 5, 6);
        Stream.concat(stream1, stream2)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
    }

    // Employee class for examples
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
            return name + "(" + age + ", " + salary + ", " + department + ")";
        }
    }
}

