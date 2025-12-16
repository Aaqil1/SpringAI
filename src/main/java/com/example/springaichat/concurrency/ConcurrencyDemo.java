package com.example.springaichat.concurrency;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Comprehensive Java Concurrency demonstration for interview preparation.
 * Covers Threads, ExecutorService, Future, and CompletableFuture with practical examples.
 */
public class ConcurrencyDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Java Concurrency Complete Demo ===\n");
        
        // 1. BASIC THREADS
        demonstrateBasicThreads();
        
        // 2. THREAD SYNCHRONIZATION
        demonstrateThreadSynchronization();
        
        // 3. EXECUTOR SERVICE
        demonstrateExecutorService();
        
        // 4. FUTURE
        demonstrateFuture();
        
        // 5. COMPLETABLE FUTURE
        demonstrateCompletableFuture();
        
        // 6. ADVANCED PATTERNS
        demonstrateAdvancedPatterns();
    }

    // ========== 1. BASIC THREADS ==========
    private static void demonstrateBasicThreads() {
        System.out.println("1. BASIC THREADS");
        System.out.println("----------------");
        
        // Extending Thread class
        System.out.println("1.1 Extending Thread class:");
        Thread thread1 = new MyThread("Thread-1");
        thread1.start();
        try {
            thread1.join(); // Wait for thread to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
        
        // Implementing Runnable interface
        System.out.println("1.2 Implementing Runnable:");
        Thread thread2 = new Thread(new MyRunnable("Thread-2"));
        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
        
        // Using Lambda expression
        System.out.println("1.3 Using Lambda:");
        Thread thread3 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Lambda Thread: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread3.start();
        try {
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
        
        // Thread states
        System.out.println("1.4 Thread States:");
        Thread thread4 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        System.out.println("Before start: " + thread4.getState()); // NEW
        thread4.start();
        System.out.println("After start: " + thread4.getState()); // RUNNABLE
        try {
            Thread.sleep(100);
            System.out.println("While running: " + thread4.getState()); // TIMED_WAITING
            thread4.join();
            System.out.println("After completion: " + thread4.getState()); // TERMINATED
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("\n");
    }

    // ========== 2. THREAD SYNCHRONIZATION ==========
    private static void demonstrateThreadSynchronization() {
        System.out.println("2. THREAD SYNCHRONIZATION");
        System.out.println("-------------------------");
        
        // Synchronized method
        System.out.println("2.1 Synchronized Method:");
        Counter counter1 = new Counter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter1.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter1.increment();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Final count: " + counter1.getCount() + "\n");
        
        // Synchronized block
        System.out.println("2.2 Synchronized Block:");
        Counter counter2 = new Counter();
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter2.incrementBlock();
            }
        });
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter2.incrementBlock();
            }
        });
        t3.start();
        t4.start();
        try {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Final count: " + counter2.getCount() + "\n");
        
        // ReentrantLock
        System.out.println("2.3 ReentrantLock:");
        Counter counter3 = new Counter();
        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter3.incrementLock();
            }
        });
        Thread t6 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter3.incrementLock();
            }
        });
        t5.start();
        t6.start();
        try {
            t5.join();
            t6.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Final count: " + counter3.getCount() + "\n");
        
        // Wait and Notify
        System.out.println("2.4 Wait and Notify:");
        Message message = new Message();
        Thread producer = new Thread(() -> {
            try {
                message.produce("Hello from Producer");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                message.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();
        producer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
    }

    // ========== 3. EXECUTOR SERVICE ==========
    private static void demonstrateExecutorService() {
        System.out.println("3. EXECUTOR SERVICE");
        System.out.println("-------------------");
        
        // Single Thread Executor
        System.out.println("3.1 Single Thread Executor:");
        ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
        singleExecutor.submit(() -> System.out.println("Task 1"));
        singleExecutor.submit(() -> System.out.println("Task 2"));
        singleExecutor.submit(() -> System.out.println("Task 3"));
        singleExecutor.shutdown();
        try {
            singleExecutor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
        
        // Fixed Thread Pool
        System.out.println("3.2 Fixed Thread Pool (3 threads):");
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            fixedPool.submit(() -> {
                System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        fixedPool.shutdown();
        try {
            fixedPool.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
        
        // Cached Thread Pool
        System.out.println("3.3 Cached Thread Pool:");
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            cachedPool.submit(() -> {
                System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName());
            });
        }
        cachedPool.shutdown();
        try {
            cachedPool.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
        
        // Scheduled Executor Service
        System.out.println("3.4 Scheduled Executor Service:");
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
        scheduledExecutor.schedule(() -> System.out.println("Delayed task executed"), 1, TimeUnit.SECONDS);
        scheduledExecutor.scheduleAtFixedRate(() -> System.out.println("Fixed rate task"), 0, 500, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        scheduledExecutor.shutdown();
        System.out.println();
    }

    // ========== 4. FUTURE ==========
    private static void demonstrateFuture() throws Exception {
        System.out.println("4. FUTURE");
        System.out.println("---------");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Basic Future
        System.out.println("4.1 Basic Future:");
        Future<String> future1 = executor.submit(() -> {
            Thread.sleep(1000);
            return "Task completed";
        });
        System.out.println("Future submitted, doing other work...");
        String result1 = future1.get(); // Blocks until result is available
        System.out.println("Result: " + result1 + "\n");
        
        // Future with timeout
        System.out.println("4.2 Future with Timeout:");
        Future<String> future2 = executor.submit(() -> {
            Thread.sleep(2000);
            return "Long running task";
        });
        try {
            String result2 = future2.get(1, TimeUnit.SECONDS);
            System.out.println("Result: " + result2);
        } catch (TimeoutException e) {
            System.out.println("Task timed out");
            future2.cancel(true);
        }
        System.out.println();
        
        // Multiple Futures
        System.out.println("4.3 Multiple Futures:");
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            Future<Integer> future = executor.submit(() -> {
                Thread.sleep(500);
                return taskId * 10;
            });
            futures.add(future);
        }
        
        // Collect results
        for (Future<Integer> future : futures) {
            System.out.println("Result: " + future.get());
        }
        System.out.println();
        
        // Check if done
        System.out.println("4.4 Checking Future Status:");
        Future<String> future3 = executor.submit(() -> {
            Thread.sleep(500);
            return "Done";
        });
        System.out.println("Is done: " + future3.isDone());
        System.out.println("Is cancelled: " + future3.isCancelled());
        future3.get();
        System.out.println("Is done: " + future3.isDone() + "\n");
        
        executor.shutdown();
    }

    // ========== 5. COMPLETABLE FUTURE ==========
    private static void demonstrateCompletableFuture() throws Exception {
        System.out.println("5. COMPLETABLE FUTURE");
        System.out.println("--------------------");
        
        // Basic CompletableFuture
        System.out.println("5.1 Basic CompletableFuture:");
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Hello";
        });
        System.out.println("Result: " + cf1.get() + "\n");
        
        // ThenApply - Transform result
        System.out.println("5.2 ThenApply (Transform):");
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "World")
            .thenApply(s -> s.toUpperCase())
            .thenApply(s -> "Hello " + s);
        System.out.println("Result: " + cf2.get() + "\n");
        
        // ThenCompose - Chain CompletableFutures
        System.out.println("5.3 ThenCompose (Chain):");
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> "First")
            .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " Second"));
        System.out.println("Result: " + cf3.get() + "\n");
        
        // ThenCombine - Combine two futures
        System.out.println("5.4 ThenCombine (Combine):");
        CompletableFuture<String> cf4 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> cf5 = CompletableFuture.supplyAsync(() -> "World");
        CompletableFuture<String> combined = cf4.thenCombine(cf5, (s1, s2) -> s1 + " " + s2);
        System.out.println("Result: " + combined.get() + "\n");
        
        // AllOf - Wait for all
        System.out.println("5.5 AllOf (Wait for all):");
        List<CompletableFuture<String>> futures = Arrays.asList(
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(300); } catch (InterruptedException e) {}
                return "Task 1";
            }),
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(200); } catch (InterruptedException e) {}
                return "Task 2";
            }),
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                return "Task 3";
            })
        );
        CompletableFuture<Void> allOf = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0])
        );
        allOf.thenRun(() -> {
            futures.forEach(f -> {
                try {
                    System.out.println("Completed: " + f.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        allOf.get();
        System.out.println();
        
        // AnyOf - Wait for any
        System.out.println("5.6 AnyOf (Wait for any):");
        CompletableFuture<String> anyOf = CompletableFuture.anyOf(
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(500); } catch (InterruptedException e) {}
                return "Slow";
            }),
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                return "Fast";
            })
        ).thenApply(result -> (String) result);
        System.out.println("First completed: " + anyOf.get() + "\n");
        
        // Exception handling
        System.out.println("5.7 Exception Handling:");
        CompletableFuture<String> cf6 = CompletableFuture.supplyAsync(() -> {
            if (true) throw new RuntimeException("Error occurred");
            return "Success";
        }).handle((result, throwable) -> {
            if (throwable != null) {
                return "Handled: " + throwable.getMessage();
            }
            return result;
        });
        System.out.println("Result: " + cf6.get() + "\n");
        
        // ThenAccept - Consume result
        System.out.println("5.8 ThenAccept (Consume):");
        CompletableFuture.supplyAsync(() -> "Data")
            .thenAccept(data -> System.out.println("Processing: " + data))
            .get();
        System.out.println();
        
        // ThenRun - Run after completion
        System.out.println("5.9 ThenRun (Run after):");
        CompletableFuture.supplyAsync(() -> "Task")
            .thenRun(() -> System.out.println("Task completed"))
            .get();
        System.out.println();
    }

    // ========== 6. ADVANCED PATTERNS ==========
    private static void demonstrateAdvancedPatterns() throws Exception {
        System.out.println("6. ADVANCED PATTERNS");
        System.out.println("--------------------");
        
        // Parallel processing with CompletableFuture
        System.out.println("6.1 Parallel Processing:");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<CompletableFuture<Integer>> futures = numbers.stream()
            .map(num -> CompletableFuture.supplyAsync(() -> num * num))
            .toList();
        
        CompletableFuture<List<Integer>> allResults = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0])
        ).thenApply(v -> futures.stream()
            .map(CompletableFuture::join)
            .toList());
        
        System.out.println("Squares: " + allResults.get() + "\n");
        
        // Producer-Consumer with ExecutorService
        System.out.println("6.2 Producer-Consumer Pattern:");
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Producer
        executor.submit(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    queue.put("Item " + i);
                    System.out.println("Produced: Item " + i);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        // Consumer
        executor.submit(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    String item = queue.take();
                    System.out.println("Consumed: " + item);
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        Thread.sleep(3000);
        executor.shutdown();
        System.out.println();
        
        // CountDownLatch example
        System.out.println("6.3 CountDownLatch:");
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executor2 = Executors.newFixedThreadPool(3);
        
        for (int i = 1; i <= 3; i++) {
            final int taskId = i;
            executor2.submit(() -> {
                try {
                    Thread.sleep(500);
                    System.out.println("Task " + taskId + " completed");
                    latch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        latch.await();
        System.out.println("All tasks completed\n");
        executor2.shutdown();
        
        // CyclicBarrier example
        System.out.println("6.4 CyclicBarrier:");
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All threads reached barrier"));
        ExecutorService executor3 = Executors.newFixedThreadPool(3);
        
        for (int i = 1; i <= 3; i++) {
            final int taskId = i;
            executor3.submit(() -> {
                try {
                    System.out.println("Task " + taskId + " waiting at barrier");
                    barrier.await();
                    System.out.println("Task " + taskId + " passed barrier");
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        Thread.sleep(1000);
        executor3.shutdown();
        System.out.println();
    }

    // Helper Classes
    static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }
        
        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                System.out.println(getName() + ": " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    static class MyRunnable implements Runnable {
        private final String name;
        
        MyRunnable(String name) {
            this.name = name;
        }
        
        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                System.out.println(name + ": " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    static class Counter {
        private int count = 0;
        private final ReentrantLock lock = new ReentrantLock();
        
        public synchronized void increment() {
            count++;
        }
        
        public void incrementBlock() {
            synchronized (this) {
                count++;
            }
        }
        
        public void incrementLock() {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }
        
        public int getCount() {
            return count;
        }
    }
    
    static class Message {
        private String message;
        private boolean hasMessage = false;
        
        public synchronized void produce(String msg) throws InterruptedException {
            while (hasMessage) {
                wait();
            }
            this.message = msg;
            this.hasMessage = true;
            System.out.println("Produced: " + msg);
            notify();
        }
        
        public synchronized void consume() throws InterruptedException {
            while (!hasMessage) {
                wait();
            }
            System.out.println("Consumed: " + message);
            hasMessage = false;
            notify();
        }
    }
}

