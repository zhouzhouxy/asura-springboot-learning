package com.asura.function.example;

import java.util.function.BiFunction;

/**
 * BiFunction<T, U, R>：接受两个输入参数，并返回一个结果。
 * @author zzyx 2024/1/21
 */
public class BiFunctionExample {

    public static void main(String[] args) {
        // 示例：使用BiFunction计算两个数的和
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        int sum = add.apply(5, 3);
        System.out.println(sum);
    }
}
