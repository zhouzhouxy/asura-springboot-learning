package com.asura.function.example;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Predicate<T>：接受一个输入参数并返回一个布尔值结果，通常用于过滤操作。
 * @author zzyx 2024/1/21
 */
public class PredicateExample {
    public static void main(String[] args) {
        // 示例：使用Predicate过滤集合中的偶数
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> evenNumbers = numbers.stream().filter(num -> num % 2 == 0).collect(Collectors.toList());

//2. 组合条件
        // 定义两个Predicate条件
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isPositive = n -> n > 0;
        // 组合条件，判断是否为正偶数
        Predicate<Integer> isPositiveEven = isEven.and(isPositive);
        System.out.println(isPositiveEven.test(4)); // 输出 true
        System.out.println(isPositiveEven.test(-2)); // 输出 false
//3. 部分应用条件
        // 定义一个Predicate，判断是否为正数
        Predicate<Integer> isPositive2 = n -> n > 0;

        // 部分应用条件，固定条件为正数
        Predicate<Integer> isNegative = isPositive2.negate();

        System.out.println(isNegative.test(4)); // 输出 false
        System.out.println(isNegative.test(-2)); // 输出 true

//        4. 自定义条件
        // 自定义Predicate，判断字符串是否包含特定子字符串
        Predicate<String> containsSubstring = s -> s.contains("Java");

        System.out.println(containsSubstring.test("Java programming")); // 输出 true
        System.out.println(containsSubstring.test("Python programming")); // 输出 false


    }
}
