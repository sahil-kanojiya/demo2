package com.hello.advanced.multithreading;

import java.util.concurrent.*;

public class ExecutorServiceDemo {

    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        for (int i = 0; i < 10; i++) {
//            executorService.submit(() -> {
//                for (int j = 0; j < 10; j++) {
//                    System.out.println(Thread.currentThread().getName() + " j=" + j);
//                }
//            });
//        }

//        Callable<String> task = () -> {
//            throw new Exception("Checked Exception in Callable");
//        };
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Future<String> future = executor.submit(task);
//        try {
//            String result = future.get(); // This will throw ExecutionException
////            no way to handle ex
//        } catch (ExecutionException e) {
//            System.out.println("Caught from Callable: " + e.getCause());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            executor.shutdown();
//        }

//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Future<String> future = executor.submit(() -> {
//            Thread.sleep(2000);
//            return "Result from Future";
//        });
//        try {
//            String result = future.get();
//            System.out.println("Future Result: " + result);
//            future.cancel(false);
//        } catch (CancellationException e) {
//            System.out.println("Task was cancelled before completion.");
//        }() -> {
////            Thread.sleep(2000);
////            return "Result from Future";
////        }) catch (ExecutionException e) {
//            System.out.println("Task threw an exception: " + e.getCause());
//        } catch (InterruptedException e) {
//            System.out.println("Thread was interrupted while waiting for result.");
//            Thread.currentThread().interrupt();
//        } finally {
//            executor.shutdown();
//        }
//        executor.shutdown();

//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Future<String> future = executor.submit(() -> {
//            Thread.sleep(2000);
//            throw new RuntimeException("Exception occurred in the task!");
//        });
//        try {
//            String result = future.get();
//            System.out.println("Future Result: " + result);
//        } catch (CancellationException e) {
//            System.out.println("Task was cancelled before completion.");
//        } catch (ExecutionException e) {
//            System.out.println("Task threw an exception: " + e.getCause());
//        } catch (InterruptedException e) {
//            System.out.println("Thread was interrupted while waiting for result.");
//            Thread.currentThread().interrupt();
//        } finally {
//            executor.shutdown();
//        }

//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            return "Task result from custom executor";
//        }, executor);
//        completableFuture
//                .thenApply(result -> "Processed: " + result)
//                .thenAccept(System.out::println)
//                .join();
//        executor.shutdown();

//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            return "Task result from custom executor";
//        }, executor);
//        completableFuture
//                .thenApply(result -> "Processed: " + result)
//                .exceptionally(err -> {
//                    System.out.println(err);
//                    return "Error occurred";
//                })
//                .thenAccept(System.out::println)
//                .join();Transform result
//        executor.shutdown();

        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "Result from Task 1";
        }, executor);
//        create new completable future

//        apply function to value only
        CompletableFuture<String> task2 = task1.thenApply(result -> {
            // Transform result
            return result + " + Processed by Task 2";
        });

//        chain    another  task with it       to retur another value   wrapped up in completable future
        CompletableFuture<String> task3 = task2.thenCompose(result -> {
            // Chain another async task using the result
            return CompletableFuture.supplyAsync(() -> {
                sleep(500);
                return result + " -> Chained in Task 3";
            }, executor);
        });

//        combine two     futures   into one to get in    one Bifunction
        CompletableFuture<String> task4 = task3.thenCombine(
                CompletableFuture.supplyAsync(() -> {
                    sleep(800);
                    return "Parallel Task B";
                }, executor),
                (resultFrom3, parallelResult) -> {
                    return resultFrom3 + " & Combined with " + parallelResult;
                });


        CompletableFuture<Void> finalTask = task4
//                apply on values only
                .thenApply(result -> {
                    if (result.contains("Error")) {
                        throw new RuntimeException("Forced error!");
                    }
                    return result.toUpperCase();
                })

//                handle ex also and return default value and hanlde     non ex values also
                .handle((result, ex) -> {
                    if (ex != null) {
                        System.out.println("Handled in handle(): " + ex);
                        return "Default result from handle()";
                    } else {
                        return result;
                    }
                })

//                for logging etc   purpose   get both  value and ex   but can not modify them
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.out.println("Logged in whenComplete: " + ex.getMessage());
                    } else {
                        System.out.println("Final result before accept: " + result);
                    }
                })

//                consume the final result
                .thenAccept(result -> System.out.println("Accepted result: " + result))

//                after all     run clean up logic   that dosent take param and dont return any val     Runnable(I) input
                .thenRun(() -> System.out.println("Final thenRun: Clean-up or notification logic"))

//                handle ex values only
                .exceptionally(ex -> {
                    System.out.println("Exceptionally block: " + ex.getMessage());
                    return null;
                });

        // Combine multiple tasks
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3, task4, finalTask);
        allTasks.join(); // Wait for all to complete

        // anyOf usage
//        any of complete then    consume result
        CompletableFuture<Object> anyTask = CompletableFuture.anyOf(task1, task4);
        anyTask.thenAccept(result -> System.out.println("First completed: " + result));

        executor.shutdown();


    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
