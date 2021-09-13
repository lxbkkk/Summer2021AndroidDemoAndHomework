package com.example.chapter3.homework;

import java.util.ArrayList;
import java.util.List;

public class TestDataSet {

    public static List<TestData> getData(){
        List<TestData> result = new ArrayList();
        result.add(new TestData("  kk", "刚刚", "  今天的老师和助教gg都好可爱啊!!!!!!!!。","a"));
        result.add(new TestData("  大胖", "刚刚", "  今天肚子好饱。","a"));
        result.add(new TestData("  笨蛋", "20:52", "  好想要那个帆布袋。","b"));
        result.add(new TestData("  话很多", "19:58", "  喵喵喵喵喵喵喵喵喵","c"));
        result.add(new TestData("  三花花", "19:55", "  zzZ","d"));
        result.add(new TestData("  还没起名", "16:30","  饿饿，饭饭，呜呜T T","e"));
        result.add(new TestData("  有点呆", "16:21", "  抓抓(*Φ皿Φ*)","f"));
        result.add(new TestData("  想不到了", "15:58", "  呜呜呜，不会换图片","c"));
        result.add(new TestData("  起名好难", "15:55", "  救救，好想回家","d"));
        result.add(new TestData("  今天吃了麦当劳", "13:30","  所以肚子很饱","e"));
        result.add(new TestData("  所以肚子很饱", "13:21", "  (*Φ皿Φ*)","f"));
        result.add(new TestData("  十点了", "12:30","  头像到底怎么换呜呜","e"));
        result.add(new TestData("  最后一条", "11:21", "  老师晚安安","f"));
        result.add(new TestData("  讲谢谢", "11:15", "  谢谢老师下午教我!!!您人好好呜呜呜!!","f"));
        return result;
    }
}
