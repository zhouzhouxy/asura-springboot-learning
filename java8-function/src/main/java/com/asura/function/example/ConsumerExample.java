package com.asura.function.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Consumer<T>：接受一个输入参数并且不返回任何结果。
 * @author zzyx 2024/1/20
 */
public class ConsumerExample {

    public static void main(String[] args) {
//        consumerMethod();
//将一个Consumer作为参数方法传递给方法
//        processData("Hello,World",data-> System.out.println("Process data: "+data));
//        方法引用
        processData("Hello,World", System.out::println);
//链式操作，按顺序执行多个Consumer
        Consumer<String> printUpperCase = str -> System.out.println("Uppercase: " + str.toUpperCase());
        Consumer<String> printLength = str -> System.out.println("Length: " + str.length());
        processAndPrint(printUpperCase.andThen(printLength));


        ArrayList<String> l1 = new ArrayList<>();
        ArrayList<String> l2 = new ArrayList<>();
        Consumer<ArrayList<String>> l= l1::addAll;
        Optional.ofNullable(l2).ifPresent(l);
//        批量数据处理
        List<String> dataItems = new ArrayList<>();
        dataItems.add("Data Item 1");
        dataItems.add("Data Item 2");
        dataItems.add("Data Item 3");
        //使用Consumer批量处理数据项
        processBatchData(dataItems, item -> System.out.println("Process data: " + item));

        //异常处理：有时候，在处理数据时可能会出现异常情况，你可以使用 Consumer 来处理异常情况，例如记录日志或进行回退操作。
        String data = "Invalid Data";
        //处理可能抛出异常的操作
        handlePossibleException(data, item -> {
                    //可能会抛出异常的操作
                    if (item.equals("Invalid Data")) {
                        throw new IllegalArgumentException("Invalid data detected");
                    }
                    System.out.println("Processing data: " + item);
                },
                exception -> {
                    //异常处理
                    System.out.println("Error occurred: " + exception.getMessage());
                    //可以进行回退操作或记录日志
                });


//        在需要打开和关闭资源（如文件、数据库连接）的情况下，可以使用 Consumer 来确保资源在使用后正确关闭。
        // 读取文件内容并处理
        processFile("data.txt",
                reader -> {
                    try {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println("Read line: " + line);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("Error reading file", e);
                    }
                },
                resource -> {
                    try {
                        resource.close();
                    } catch (IOException e) {
                        System.err.println("Error closing resource: " + e.getMessage());
                    }
                }
        );
    }

    public static void processFile(String filename, Consumer<BufferedReader> dataConsumer, Consumer<BufferedReader> resourceCleanup) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            dataConsumer.accept(reader);
        } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
        } finally {
            resourceCleanup.accept(null);
        }
    }



    private static void handlePossibleException(String data, Consumer<String> dataConsumer, Consumer<Exception> exceptionConsumer) {
        try {
            dataConsumer.accept(data);
        } catch (Exception e) {
            exceptionConsumer.accept(e);
        }
    }

    private static void processBatchData(List<String> dataItems, Consumer<String> dataConsumer) {
        for (String item : dataItems) {
            dataConsumer.accept(item);
        }
    }

    private static void processAndPrint(Consumer<String> dataConsumer) {
        dataConsumer.accept("HelloWorld");
    }

    //    Consumer<T>：接受一个输入参数并且不返回任何结果
    public static void consumerMethod() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charline");
        names.forEach(System.out::println);
    }

    public static void processData(String data, Consumer<String> dataConsumer) {
        dataConsumer.accept(data);
    }

}
