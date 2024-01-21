package com.asura.function.example;

import java.util.*;
import java.util.function.Supplier;

/**
 * Supplier<T>：不接受任何参数但返回一个结果。
 * @author zzyx 2024/1/21
 */
public class SupplierExample {
    public static void main(String[] args) {
        Supplier<String> stringSupplier=()->"hello World";
        System.out.println(stringSupplier.get());

        //延迟加载  Supplier 可以用于延迟计算的场景，即在需要的时候才计算值。
        lazyComputation();
        //构造复杂对象  使用 Supplier 来构造复杂的或成本较高的对象，这可以与单例模式或工厂模式结合使用。
        ComplexObjectSingleton.ComplexObject instance = ComplexObjectSingleton.getInstance();
        ComplexObjectSingleton.ComplexObject instance1 = ComplexObjectSingleton.getInstance();
        System.out.println(instance);
        System.out.println(instance1);
        Supplier<ComplexObjectFactory.ComplexObject> complexObjectCreator = ComplexObjectFactory.getComplexObjectCreator();
        Supplier<ComplexObjectFactory.ComplexObject> complexObjectCreator1 = ComplexObjectFactory.getComplexObjectCreator();
        System.out.println(complexObjectCreator.get());
        System.out.println(complexObjectCreator1.get());
        //
        Optional<String> empty = Optional.empty();
        String s = empty.orElseGet(() -> "默认值");
        System.out.println(s);
    }


    private static void lazyComputation(){
        Supplier<String> delayedComputation= ()->{
            //模拟一个耗时的计算过程
            simulateHeavyComputation();
            return "计算结果";
        };

        //在这里计算还没有开始
        System.out.println("计算尚未开始");
        //当需要计算结果时
        String result = delayedComputation.get();
        System.out.println("计算结果: "+result);
    }
    private static void simulateHeavyComputation(){
        try {
            Thread.sleep(3000);
            System.out.println("进行计算");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("计算被中断",e);
        }
    }


    static class ComplexObjectSingleton{
        private static ComplexObject instance=null;

        //私有化构造器，防止外部直接创建实例
        public static ComplexObject getInstance(){
            if(instance==null){
                instance=createComplexObject.get();
            }
            return instance;
        }

        //supplier 用于延迟创建复杂对象
        private static Supplier<ComplexObject> createComplexObject= ()->{
            //假设这是一个成本较高的创建过程
            System.out.println("创建复杂对象");
            //模拟耗时操作
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return new ComplexObject();
        };

        private static class ComplexObject {
            // 复杂对象的构造器和方法

            public ComplexObject() {
            }
        }
    }

    static class ComplexObjectFactory{
        //工厂方法，返回一个Supplier<ComplexObject>
        public static Supplier<ComplexObject> getComplexObjectCreator(){
            return ()->{
                System.out.println("创建复杂对象");
                //模拟耗时操作
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
                return new ComplexObject();
            };
        }

        // 代表一个复杂对象的内部类
        private static class ComplexObject {
            // 复杂对象的构造器和方法
        }
    }

    static class Cache<K,V>{
        private final Map<K,V> cache=new HashMap<>();

        //获取缓存中的值，如果不存在则使用Supplier计算并存储
        public V get(K key,Supplier<V> valueSupplier){
            return cache.computeIfAbsent(key,k-> valueSupplier.get());
        }

        public static void main(String[] args) {
            Cache<String, String> cache = new Cache<>();
            //使用Lambda表达式作为Supplier
            String value = cache.get("key1", () -> computeExpensiveValue("key1"));
            System.out.println("value for key1: "+value);
            // 从缓存中获取值，避免再次计算
            String cachedValue = cache.get("key1", () -> computeExpensiveValue("key1"));
            System.out.println("Cached value for key1: " + cachedValue);
        }
        public static String computeExpensiveValue(String key){
            System.out.println("Computing value for "+key);
            //模拟计算过程
            return "Value for "+key;
        }
    }
}
