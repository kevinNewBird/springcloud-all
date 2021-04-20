package com.mashibing.eureka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SpringBootTest
class EurekaServerApplicationTests {

    public static final String REPORT_EDIT_KEY = "1";
    public static final String REPORT_DEL_KEY = "2";
    public static final String REPORT_DOC_KEY = "3";

    @Test
    void contextLoads() {
    }

    public static List<String> keyContainer = Collections.unmodifiableList(Arrays.asList(REPORT_EDIT_KEY, REPORT_DEL_KEY
            , REPORT_DOC_KEY));

    @Test
    public void test() {
        // 选题权限的key容器
        System.out.println(keyContainer.stream()
                .filter(key -> !REPORT_EDIT_KEY.equals(key) && !REPORT_DEL_KEY.equals(key)).collect(Collectors.toList()));
        System.out.println(keyContainer);

    }


    @Test
    public void test00() {
        StringJoiner idJoiner = new StringJoiner(";","手动归档[ID=","]");
        StringJoiner titleJoiner = new StringJoiner(";","[TITLE=","]");
        for (int i = 0; i < 10; i++) {
            idJoiner.add(String.valueOf(i));
            titleJoiner.add("标题" + i);
        }
        String merge = idJoiner.toString() + titleJoiner.toString();
        System.out.println(merge.toString());
    }

    /**
     * 排除重复的数据
     * @title findDuplicateData
     * @author dengyouxu
     * @since 2021/4/20 11:09:24:512
     * @param list 原始数据
     * @param keyExtractor 重复的值判断
     * @return java.util.List<T>
     */
    public static <T, V> List<T> distinctDuplicateData(List<T> list,
                                                       Function<T, V> keyExtractor) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().filter(distinctByKey(keyExtractor, true)).collect(Collectors.toList());
    }

    /**
     * 找出重复的数据
     * @title findDuplicateData
     * @author dengyouxu
     * @since 2021/4/20 11:27:11:331
     * @param list
     * @param keyExtractor
     * @return java.util.List<T>
     */
    public static <T, V> List<T> findDuplicateData(List<T> list,
                                                   Function<T, V> keyExtractor) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().filter(distinctByKey(keyExtractor, false)).collect(Collectors.toList());
    }

    /**
     * 根据key 找出重复的key
     * @title distinctByKey
     * @author dengyouxu
     * @since 2021/4/20 11:14:21:369
     * @param keyExtractor
     * @param isDistinct 排除这些重复的key或者保留
     * @return java.util.function.Predicate<T>
     */
    public static <T, V> Predicate<T> distinctByKey(Function<T, V> keyExtractor,
                                                    boolean isDistinct) {

        Map<V, Boolean> seen = new ConcurrentHashMap<>();

        // key不存在则put进map中，并返回null。若key存在，则直接返回key所对应的value值
        return isDistinct
                ? t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null
                : t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) != null;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

}
