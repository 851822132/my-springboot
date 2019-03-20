package com.test.myspringboot.steam;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: ysh
 * @date: 2019/3/18 14:40
 * @Description:java8 steam测试
 */
public class SteamTest {
    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("a","","v","f","2","3","c","b");
        long start = System.currentTimeMillis();
        String result = stringList.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(","));
//        String result2 = stringList.stream().filter(string -> !string.isEmpty()).map((s) -> s+="333").collect(Collectors.joining(","));
        String result2 = stringList.stream().filter(string -> !string.isEmpty()).map((s) -> {s+="111";s+="333"; return s;}).collect(Collectors.joining(","));
        System.out.println(result);
        System.out.println(result2);
        System.out.println(System.currentTimeMillis()-start);
        long start1 = System.currentTimeMillis();
        String result1 = stringList.parallelStream().filter(string -> !string.isEmpty()).collect(Collectors.joining(","));
        System.out.println(result1);
        System.out.println(System.currentTimeMillis()-start1);


        List<Integer> integers = Arrays.asList(4,1,3,9,4,2);
        List<Integer> result3 = integers.stream().sorted().collect(Collectors.toList());
        result3.forEach(System.out::println);
        result3.forEach(integer -> System.out.print(integer + ", "));
        System.out.println();
        result3.forEach(integer -> {
            if (integer == 4) {
                System.out.print(integer + ", ");
            }
        });
//        int result3 = integers.stream().sorted((x,y) -> y - x).mapToInt((z) -> z).sum();
//        System.out.println(result3);

        IntSummaryStatistics stats = integers.stream().mapToInt((x) ->x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
    }
}
