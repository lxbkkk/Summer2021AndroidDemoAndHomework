package com.example.hw2_2.recycler;

import java.util.ArrayList;
import java.util.List;

public class TestDataSet {

    public static List<TestData> getData(){
        List<TestData> result = new ArrayList();
        result.add(new TestData("让人忘记其他猫猫的大胖", "520.6w"));
        result.add(new TestData("大胖退役", "433.6w"));
        result.add(new TestData("大胖：你在教我做事？", "357.8w"));
        result.add(new TestData("投身大学教育的大胖", "333.6w"));
        result.add(new TestData("大胖的暑期嘉年华", "285.6w"));
        result.add(new TestData("2020年大胖睡觉日有360天", "183.2w"));
        result.add(new TestData("会跟学生合照的大胖", "139.4w"));
        result.add(new TestData("杭州暴雨，救救大胖", "75.6w"));
        result.add(new TestData("粮价上涨，大胖肚子饿", "65.0w"));
        result.add(new TestData("猫的第六感有多强", "63.0w"));
        result.add(new TestData("大胖去哪儿","55.5w"));
        result.add(new TestData("大胖今天吃什么", "45.0w"));
        result.add(new TestData("大胖：睡觉是一种习惯", "43.2w"));
        result.add(new TestData("一只大胖正在醒来","25.5w"));
        return result;
    }
}
