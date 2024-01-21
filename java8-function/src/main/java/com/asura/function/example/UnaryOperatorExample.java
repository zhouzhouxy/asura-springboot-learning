package com.asura.function.example;

import java.util.function.UnaryOperator;

/**
 * UnaryOperator<T>：是Function<T, T>的一个特殊情况，接受一个输入参数并返回相同类型的结果。
 * @author zzyx 2024/1/21
 */
public class UnaryOperatorExample {

    public static void main(String[] args) {
        // 示例：使用UnaryOperator对整数进行平方操作
        UnaryOperator<Integer> square = num -> num * num;
        int squaredValue = square.apply(4);

        System.out.println(squaredValue);

        updateObject();

    }

    private static void updateObject(){
        //定义一个包含状态的对象
        StatefulObject obj = new StatefulObject("initial state");
        //使用UnaryOperator来更新对象的状态
        UnaryOperator<StatefulObject> updateState=o->{
            o.setState("new state");
            return o;
        };
        StatefulObject apply = updateState.apply(obj);
        System.out.println(apply.getState());
    }

    static class StatefulObject{
        private String state;
        public StatefulObject(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
