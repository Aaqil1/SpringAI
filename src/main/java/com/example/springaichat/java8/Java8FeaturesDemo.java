package com.example.springaichat.java8;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Comprehensive Java 8+ Features Demonstration
 * Covers: Lambda expressions, Streams API, Optional, Functional interfaces,
 * Method references, and Default methods in interfaces.
 */
public class Java8FeaturesDemo {

    public static void main(String[] args) {
        System.out.println("=== Java 8+ Features Complete Demo ===\n");
        
        // Sample data
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, "IT"),
            new Person("Bob", 30, "HR"),
            new Person("Charlie", 28, "IT"),
            new Person("David", 35, "Finance"),
            new Person("Eve", 22, "IT"),
            new Person("Frank", 40, "Finance")
        );
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        // 1. LAMBDA EXPRESSIONS
        demonstrateLambdaExpressions(people, numbers);
        
        // 2. STREAMS API
        demonstrateStreamsAPI(people, numbers, names);
        
        // 3. OPTIONAL
        demonstrateOptional();
        
        // 4. FUNCTIONAL INTERFACES
        demonstrateFunctionalInterfaces(numbers, names);
        
        // 5. METHOD REFERENCES
        demonstrateMethodReferences(people, names, numbers);
        
        // 6. DEFAULT METHODS IN INTERFACES
        demonstrateDefaultMethods();
    }

    // ========== 1. LAMBDA EXPRESSIONS ==========
    private static void demonstrateLambdaExpressions(List<Person> people, List<Integer> numbers) {
        System.out.println("1. LAMBDA EXPRESSIONS");
        System.out.println("----------------------");
        
        // Before Java 8: Anonymous inner class
        System.out.println("1.1 Before Java 8 (Anonymous Inner Class):");
        Collections.sort(people, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getAge() - p2.getAge();
            }
        });
        System.out.println("Sorted by age: " + people + "\n");
        
        // Java 8: Lambda expression
        System.out.println("1.2 Java 8 Lambda Expression:");
        Collections.sort(people, (p1, p2) -> p1.getAge() - p2.getAge());
        System.out.println("Sorted by age: " + people + "\n");
        
        // Lambda with multiple statements
        System.out.println("1.3 Lambda with Multiple Statements:");
        numbers.forEach(n -> {
            int square = n * n;
            System.out.println("Number: " + n + ", Square: " + square);
        });
        System.out.println();
        
        // Lambda with no parameters
        System.out.println("1.4 Lambda with No Parameters:");
        Runnable runnable = () -> System.out.println("Hello from Lambda!");
        new Thread(runnable).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
        
        // Lambda with single parameter (parentheses optional)
        System.out.println("1.5 Lambda with Single Parameter:");
        numbers.forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // Lambda with type inference
        System.out.println("1.6 Lambda with Type Inference:");
        BinaryOperator<Integer> add = (a, b) -> a + b;
        System.out.println("Sum: " + add.apply(5, 3) + "\n");
        
        // Lambda in method parameters
        System.out.println("1.7 Lambda in Method Parameters:");
        processNumbers(numbers, n -> n % 2 == 0, n -> n * 2);
        System.out.println();
    }

    // ========== 2. STREAMS API ==========
    private static void demonstrateStreamsAPI(List<Person> people, List<Integer> numbers, List<String> names) {
        System.out.println("2. STREAMS API");
        System.out.println("--------------");
        
        // Creating streams
        System.out.println("2.1 Creating Streams:");
        Stream<Integer> stream1 = numbers.stream();
        Stream<String> stream2 = Stream.of("A", "B", "C");
        Stream<Integer> stream3 = Stream.iterate(0, n -> n + 2).limit(5);
        System.out.println("Stream from list: " + stream1.count() + " elements");
        System.out.println("Stream from values: " + stream2.count() + " elements");
        System.out.println("Stream from iterate: " + stream3.count() + " elements\n");
        
        // Filter
        System.out.println("2.2 Filter - Even numbers:");
        numbers.stream()
            .filter(n -> n % 2 == 0)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // Map
        System.out.println("2.3 Map - Square of numbers:");
        numbers.stream()
            .map(n -> n * n)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // FlatMap
        System.out.println("2.4 FlatMap - Flatten nested lists:");
        List<List<Integer>> nested = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5, 6),
            Arrays.asList(7, 8, 9)
        );
        nested.stream()
            .flatMap(List::stream)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // Distinct
        System.out.println("2.5 Distinct - Unique values:");
        Arrays.asList(1, 2, 2, 3, 3, 3, 4).stream()
            .distinct()
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // Sorted
        System.out.println("2.6 Sorted - Sort by age:");
        people.stream()
            .sorted(Comparator.comparing(Person::getAge))
            .forEach(p -> System.out.print(p.getName() + "(" + p.getAge() + ") "));
        System.out.println("\n");
        
        // Limit and Skip
        System.out.println("2.7 Limit and Skip:");
        numbers.stream()
            .skip(2)
            .limit(5)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");
        
        // Collect
        System.out.println("2.8 Collect - To List:");
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("Even numbers: " + evenNumbers + "\n");
        
        // Collect to Map
        System.out.println("2.9 Collect - To Map:");
        Map<String, Integer> nameToAge = people.stream()
            .collect(Collectors.toMap(Person::getName, Person::getAge));
        System.out.println("Name to Age: " + nameToAge + "\n");
        
        // Grouping
        System.out.println("2.10 Grouping - Group by department:");
        Map<String, List<Person>> byDept = people.stream()
            .collect(Collectors.groupingBy(Person::getDepartment));
        byDept.forEach((dept, persons) -> 
            System.out.println(dept + ": " + persons));
        System.out.println();
        
        // Partitioning
        System.out.println("2.11 Partitioning - Age >= 30:");
        Map<Boolean, List<Person>> partitioned = people.stream()
            .collect(Collectors.partitioningBy(p -> p.getAge() >= 30));
        System.out.println("Age >= 30: " + partitioned.get(true));
        System.out.println("Age < 30: " + partitioned.get(false) + "\n");
        
        // Reduce
        System.out.println("2.12 Reduce - Sum of numbers:");
        int sum = numbers.stream()
            .reduce(0, Integer::sum);
        System.out.println("Sum: " + sum + "\n");
        
        // Min and Max
        System.out.println("2.13 Min and Max:");
        Optional<Person> oldest = people.stream()
            .max(Comparator.comparing(Person::getAge));
        Optional<Person> youngest = people.stream()
            .min(Comparator.comparing(Person::getAge));
        oldest.ifPresent(p -> System.out.println("Oldest: " + p.getName()));
        youngest.ifPresent(p -> System.out.println("Youngest: " + p.getName() + "\n"));
        
        // Count
        System.out.println("2.14 Count - IT employees:");
        long itCount = people.stream()
            .filter(p -> "IT".equals(p.getDepartment()))
            .count();
        System.out.println("IT employees: " + itCount + "\n");
        
        // AnyMatch, AllMatch, NoneMatch
        System.out.println("2.15 Match Operations:");
        boolean anyOver30 = people.stream().anyMatch(p -> p.getAge() > 30);
        boolean allOver20 = people.stream().allMatch(p -> p.getAge() > 20);
        boolean noneOver50 = people.stream().noneMatch(p -> p.getAge() > 50);
        System.out.println("Any over 30: " + anyOver30);
        System.out.println("All over 20: " + allOver20);
        System.out.println("None over 50: " + noneOver50 + "\n");
        
        // FindFirst and FindAny
        System.out.println("2.16 FindFirst and FindAny:");
        Optional<Person> firstIT = people.stream()
            .filter(p -> "IT".equals(p.getDepartment()))
            .findFirst();
        Optional<Person> anyIT = people.stream()
            .filter(p -> "IT".equals(p.getDepartment()))
            .findAny();
        firstIT.ifPresent(p -> System.out.println("First IT: " + p.getName()));
        anyIT.ifPresent(p -> System.out.println("Any IT: " + p.getName() + "\n"));
    }

    // ========== 3. OPTIONAL ==========
    private static void demonstrateOptional() {
        System.out.println("3. OPTIONAL");
        System.out.println("-----------");
        
        // Creating Optional
        System.out.println("3.1 Creating Optional:");
        Optional<String> empty = Optional.empty();
        Optional<String> of = Optional.of("Hello");
        Optional<String> ofNullable = Optional.ofNullable(null);
        System.out.println("Empty: " + empty.isPresent());
        System.out.println("Of: " + of.isPresent());
        System.out.println("OfNullable: " + ofNullable.isPresent() + "\n");
        
        // isPresent and isEmpty
        System.out.println("3.2 isPresent and isEmpty:");
        Optional<String> opt = Optional.of("World");
        System.out.println("isPresent: " + opt.isPresent());
        System.out.println("isEmpty: " + opt.isEmpty() + "\n");
        
        // get() - Unsafe
        System.out.println("3.3 get() - Get value:");
        if (opt.isPresent()) {
            System.out.println("Value: " + opt.get() + "\n");
        }
        
        // orElse - Default value
        System.out.println("3.4 orElse - Default value:");
        String value1 = empty.orElse("Default");
        System.out.println("Value: " + value1 + "\n");
        
        // orElseGet - Default from supplier
        System.out.println("3.5 orElseGet - Default from supplier:");
        String value2 = empty.orElseGet(() -> "Generated Default");
        System.out.println("Value: " + value2 + "\n");
        
        // orElseThrow - Throw exception
        System.out.println("3.6 orElseThrow - Throw if empty:");
        try {
            String value3 = empty.orElseThrow(() -> new RuntimeException("No value"));
        } catch (RuntimeException e) {
            System.out.println("Exception: " + e.getMessage() + "\n");
        }
        
        // ifPresent - Execute if present
        System.out.println("3.7 ifPresent - Execute if present:");
        opt.ifPresent(v -> System.out.println("Value: " + v + "\n"));
        
        // ifPresentOrElse - Execute if present or else
        System.out.println("3.8 ifPresentOrElse:");
        empty.ifPresentOrElse(
            v -> System.out.println("Value: " + v),
            () -> System.out.println("No value\n")
        );
        
        // map - Transform value
        System.out.println("3.9 map - Transform value:");
        Optional<String> upper = opt.map(String::toUpperCase);
        upper.ifPresent(v -> System.out.println("Upper: " + v + "\n"));
        
        // flatMap - Flatten nested Optional
        System.out.println("3.10 flatMap - Flatten nested Optional:");
        Optional<String> flatMapped = opt.flatMap(s -> Optional.of(s + " Extended"));
        flatMapped.ifPresent(v -> System.out.println("FlatMapped: " + v + "\n"));
        
        // filter - Filter value
        System.out.println("3.11 filter - Filter value:");
        Optional<String> filtered = opt.filter(s -> s.length() > 3);
        filtered.ifPresent(v -> System.out.println("Filtered: " + v + "\n"));
        
        // Optional with Stream
        System.out.println("3.12 Optional with Stream:");
        Optional<String> opt2 = Optional.of("Stream");
        opt2.stream()
            .map(String::toUpperCase)
            .forEach(System.out::println);
        System.out.println();
    }

    // ========== 4. FUNCTIONAL INTERFACES ==========
    private static void demonstrateFunctionalInterfaces(List<Integer> numbers, List<String> names) {
        System.out.println("4. FUNCTIONAL INTERFACES");
        System.out.println("-----------------------");
        
        // Predicate<T> - Returns boolean
        System.out.println("4.1 Predicate<T> - Test condition:");
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isGreaterThan5 = n -> n > 5;
        System.out.println("Is 4 even? " + isEven.test(4));
        System.out.println("Is 4 > 5? " + isGreaterThan5.test(4));
        System.out.println("Is 4 even AND > 5? " + isEven.and(isGreaterThan5).test(4));
        System.out.println("Is 4 even OR > 5? " + isEven.or(isGreaterThan5).test(4));
        System.out.println("Is 4 NOT even? " + isEven.negate().test(4) + "\n");
        
        // Function<T, R> - Transform input to output
        System.out.println("4.2 Function<T, R> - Transform:");
        Function<Integer, Integer> square = n -> n * n;
        Function<Integer, Integer> addOne = n -> n + 1;
        System.out.println("Square of 5: " + square.apply(5));
        System.out.println("Compose (add then square): " + square.compose(addOne).apply(4));
        System.out.println("AndThen (square then add): " + square.andThen(addOne).apply(4) + "\n");
        
        // Consumer<T> - Accept input, no return
        System.out.println("4.3 Consumer<T> - Consume value:");
        Consumer<String> print = System.out::println;
        Consumer<String> printUpper = s -> System.out.println(s.toUpperCase());
        print.andThen(printUpper).accept("Hello\n");
        
        // Supplier<T> - Supply value, no input
        System.out.println("4.4 Supplier<T> - Supply value:");
        Supplier<Double> random = Math::random;
        Supplier<String> greeting = () -> "Hello World";
        System.out.println("Random: " + random.get());
        System.out.println("Greeting: " + greeting.get() + "\n");
        
        // BiFunction<T, U, R> - Two inputs, one output
        System.out.println("4.5 BiFunction<T, U, R> - Two inputs:");
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        BiFunction<String, String, String> concat = (a, b) -> a + " " + b;
        System.out.println("Add 5 and 3: " + add.apply(5, 3));
        System.out.println("Concat: " + concat.apply("Hello", "World") + "\n");
        
        // BinaryOperator<T> - Two inputs of same type, same output type
        System.out.println("4.6 BinaryOperator<T> - Binary operation:");
        BinaryOperator<Integer> multiply = (a, b) -> a * b;
        BinaryOperator<String> longer = (a, b) -> a.length() > b.length() ? a : b;
        System.out.println("Multiply 4 and 5: " + multiply.apply(4, 5));
        System.out.println("Longer string: " + longer.apply("Hello", "World") + "\n");
        
        // UnaryOperator<T> - One input, same type output
        System.out.println("4.7 UnaryOperator<T> - Unary operation:");
        UnaryOperator<Integer> doubleValue = n -> n * 2;
        UnaryOperator<String> toUpper = String::toUpperCase;
        System.out.println("Double 7: " + doubleValue.apply(7));
        System.out.println("To Upper: " + toUpper.apply("hello") + "\n");
        
        // BiPredicate<T, U> - Two inputs, boolean output
        System.out.println("4.8 BiPredicate<T, U> - Two inputs, boolean:");
        BiPredicate<Integer, Integer> isGreater = (a, b) -> a > b;
        System.out.println("Is 5 > 3? " + isGreater.test(5, 3) + "\n");
        
        // BiConsumer<T, U> - Two inputs, no output
        System.out.println("4.9 BiConsumer<T, U> - Two inputs, no output:");
        BiConsumer<String, Integer> printNameAge = (name, age) -> 
            System.out.println(name + " is " + age + " years old");
        printNameAge.accept("Alice", 25);
        System.out.println();
        
        // IntFunction, LongFunction, DoubleFunction
        System.out.println("4.10 Primitive Functional Interfaces:");
        IntFunction<String> intToString = i -> "Number: " + i;
        System.out.println(intToString.apply(42) + "\n");
        
        // ToIntFunction, ToLongFunction, ToDoubleFunction
        System.out.println("4.11 To Primitive Functions:");
        ToIntFunction<String> stringLength = String::length;
        System.out.println("Length of 'Hello': " + stringLength.applyAsInt("Hello") + "\n");
    }

    // ========== 5. METHOD REFERENCES ==========
    private static void demonstrateMethodReferences(List<Person> people, List<String> names, List<Integer> numbers) {
        System.out.println("5. METHOD REFERENCES");
        System.out.println("--------------------");
        
        // Static method reference
        System.out.println("5.1 Static Method Reference - Integer.parseInt:");
        List<String> numberStrings = Arrays.asList("1", "2", "3", "4", "5");
        List<Integer> parsed = numberStrings.stream()
            .map(Integer::parseInt)
            .collect(Collectors.toList());
        System.out.println("Parsed: " + parsed + "\n");
        
        // Instance method reference on specific object
        System.out.println("5.2 Instance Method Reference - System.out.println:");
        names.forEach(System.out::println);
        System.out.println();
        
        // Instance method reference on arbitrary object
        System.out.println("5.3 Instance Method Reference - String.toUpperCase:");
        List<String> upper = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Upper: " + upper + "\n");
        
        // Constructor reference
        System.out.println("5.4 Constructor Reference - String::new:");
        List<String> copied = names.stream()
            .map(String::new)
            .collect(Collectors.toList());
        System.out.println("Copied: " + copied + "\n");
        
        // Method reference in Comparator
        System.out.println("5.5 Method Reference in Comparator:");
        people.stream()
            .sorted(Comparator.comparing(Person::getAge))
            .forEach(p -> System.out.print(p.getName() + "(" + p.getAge() + ") "));
        System.out.println("\n");
        
        // Method reference with multiple parameters
        System.out.println("5.6 Method Reference - Person constructor:");
        List<Person> newPeople = Arrays.asList("Alice", "Bob", "Charlie").stream()
            .map(name -> new Person(name, 25, "IT"))
            .collect(Collectors.toList());
        System.out.println("New people: " + newPeople + "\n");
        
        // Method reference vs Lambda
        System.out.println("5.7 Method Reference vs Lambda:");
        // Lambda
        names.stream().map(s -> s.toUpperCase()).forEach(s -> System.out.print(s + " "));
        System.out.println();
        // Method reference (more concise)
        names.stream().map(String::toUpperCase).forEach(System.out::print);
        System.out.println("\n");
    }

    // ========== 6. DEFAULT METHODS IN INTERFACES ==========
    private static void demonstrateDefaultMethods() {
        System.out.println("6. DEFAULT METHODS IN INTERFACES");
        System.out.println("--------------------------------");
        
        // Interface with default method
        System.out.println("6.1 Default Method Example:");
        Calculator calculator = new BasicCalculator();
        System.out.println("Add: " + calculator.add(5, 3));
        System.out.println("Subtract: " + calculator.subtract(5, 3));
        System.out.println("Multiply (default): " + calculator.multiply(5, 3));
        System.out.println("Divide (default): " + calculator.divide(10, 2) + "\n");
        
        // Multiple interfaces with default methods
        System.out.println("6.2 Multiple Interfaces:");
        MultiInterfaceImpl impl = new MultiInterfaceImpl();
        impl.method1();
        impl.method2();
        impl.commonMethod();
        System.out.println();
        
        // Overriding default method
        System.out.println("6.3 Overriding Default Method:");
        Calculator advanced = new AdvancedCalculator();
        System.out.println("Multiply (overridden): " + advanced.multiply(5, 3) + "\n");
        
        // Static methods in interfaces
        System.out.println("6.4 Static Methods in Interfaces:");
        int result = CalculatorUtility.add(10, 20);
        System.out.println("Static method result: " + result + "\n");
    }

    // Helper method for lambda demonstration
    private static void processNumbers(List<Integer> numbers, Predicate<Integer> filter, Function<Integer, Integer> mapper) {
        numbers.stream()
            .filter(filter)
            .map(mapper)
            .forEach(n -> System.out.print(n + " "));
        System.out.println();
    }

    // Person class for examples
    static class Person {
        private String name;
        private int age;
        private String department;

        public Person(String name, int age, String department) {
            this.name = name;
            this.age = age;
            this.department = department;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
        public String getDepartment() { return department; }

        @Override
        public String toString() {
            return name + "(" + age + ", " + department + ")";
        }
    }

    // Interfaces for default methods demonstration
    interface Calculator {
        int add(int a, int b);
        int subtract(int a, int b);
        
        // Default method
        default int multiply(int a, int b) {
            return a * b;
        }
        
        default int divide(int a, int b) {
            if (b == 0) throw new IllegalArgumentException("Division by zero");
            return a / b;
        }
    }

    interface Interface1 {
        default void method1() {
            System.out.println("Interface1 method1");
        }
        
        default void commonMethod() {
            System.out.println("Interface1 commonMethod");
        }
    }

    interface Interface2 {
        default void method2() {
            System.out.println("Interface2 method2");
        }
        
        default void commonMethod() {
            System.out.println("Interface2 commonMethod");
        }
    }

    interface CalculatorUtility {
        static int add(int a, int b) {
            return a + b;
        }
    }

    static class BasicCalculator implements Calculator {
        @Override
        public int add(int a, int b) {
            return a + b;
        }

        @Override
        public int subtract(int a, int b) {
            return a - b;
        }
    }

    static class AdvancedCalculator implements Calculator {
        @Override
        public int add(int a, int b) {
            return a + b;
        }

        @Override
        public int subtract(int a, int b) {
            return a - b;
        }

        @Override
        public int multiply(int a, int b) {
            // Override default method
            return a * b * 2; // Custom implementation
        }
    }

    static class MultiInterfaceImpl implements Interface1, Interface2 {
        @Override
        public void commonMethod() {
            // Must override when both interfaces have same default method
            Interface1.super.commonMethod();
            Interface2.super.commonMethod();
            System.out.println("MultiInterfaceImpl commonMethod");
        }
    }
}

