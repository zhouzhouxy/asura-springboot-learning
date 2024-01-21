package com.asura.function.example;

import java.util.function.Function;

/**
 * Function<T, R>：接受一个输入参数，并返回一个结果。
 * @author zzyx 2024/1/21
 */
public class FunctionExample {

    public static void main(String[] args) {
        Function<String,String> toUppercase= String::toUpperCase;
        System.out.println(toUppercase.apply("hello"));

        // 定义一个Function，将两个整数相加
        Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;

        // 部分应用，固定第一个参数为5
        Function<Integer, Integer> add5 = add.apply(5);

        int result = add5.apply(3); // 相当于 5 + 3
        System.out.println(result); // 输出 8

    }

}
