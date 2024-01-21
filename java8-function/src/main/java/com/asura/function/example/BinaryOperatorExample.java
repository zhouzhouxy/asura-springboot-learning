package com.asura.function.example;

import java.util.function.BinaryOperator;

/**
 * BinaryOperator<T>：是BiFunction<T, T, T>的一个特殊情况，接受两个输入参数并返回相同类型的结果。
 * @author zzyx 2024/1/21
 */
public class BinaryOperatorExample {
    public static void main(String[] args) {
        // 示例：使用BinaryOperator计算两个数的最大值
        BinaryOperator<Integer> max = (a, b) -> Math.max(a, b);
        int maxValue = max.apply(7, 9);
        System.out.println(maxValue);


    }
}
