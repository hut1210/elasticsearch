package com.example.demo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hut
 * @date 2023/2/15 2:00 下午
 */
public class StreamTest {
    /*private static List<Student> computerClub = Arrays.asList(
            new Student("2015134001", "小明", 15, "1501"),
            new Student("2015134003", "小王", 14, "1503"),
            new Student("2015134006", "小张", 15, "1501"),
            new Student("2015134008", "小梁", 17, "1505")
    );
    private static List<Student> basketballClub = Arrays.asList(
            new Student("2015134012", "小c", 13, "1503"),
            new Student("2015134013", "小s", 14, "1503"),
            new Student("2015134015", "小d", 15, "1504"),
            new Student("2015134018", "小y", 16, "1505")
    );
    private static List<Student> pingpongClub = Arrays.asList(
            new Student("2015134022", "小u", 16, "1502"),
            new Student("2015134021", "小i", 14, "1502"),
            new Student("2015134026", "小m", 17, "1504"),
            new Student("2015134027", "小n", 16, "1504")
    );

    private static List<List<Student>> allClubStu = new ArrayList<>();
    public static void main(String[] args) {
        //map 一对一 映射处理
        collect.stream().map(student -> {
            StudentDTO studentDTO = StudentDTO.builder().age(student.getAge())
                    .classNum(student.getClassNum())
                    .name(student.getName())
                    .studentNum(student.getStudentNum()).build();
            return studentDTO;
        }).collect(Collectors.toList()).forEach(System.out::println);

        allClubStu.add(computerClub);
        allClubStu.add(basketballClub);
        allClubStu.add(pingpongClub);
        //flatMap  一对多映射处理，深入到多个stream内部去处理子元素，统一输出
        List<Student> studentList = allClubStu.stream().flatMap(e -> e.stream().
                filter(student -> student.getAge() > 15)).collect(Collectors.toList());
        studentList.forEach(System.out::println);
    }*/

    public static void main(String[] args) {

        List<Integer> list0 = Arrays.asList(1,2,3,4,5,6);

        //map的方式
        List<Integer> collect1 = list0.stream()
                .map(t -> t+1)
                .collect(Collectors.toList());
        System.out.println(collect1);

        List<Integer> list1 = Arrays.asList(1,2,3,4,5,6);
        List<Integer> list2 = Arrays.asList(1,2,3,4,5,6);
        List<List<Integer>> list = new ArrayList<>();
        list.add(list1);
        list.add(list2);

        List<Integer> collect = list.stream()
                .flatMap(t -> t.stream().map(a -> a + 2))
                .collect(Collectors.toList());
        System.out.println(collect);


        BigDecimal bigDecimal = new BigDecimal("0");
        System.out.println(bigDecimal.compareTo(BigDecimal.ZERO)>0);
    }

    /*private void sendMsg(PrintEvent event) {
        //定义参数
        Map<String, Object> params = new HashMap<>();
        params.put("zth", event.getPrintInfo().getZth());
        params.put("name", event.getPrintInfo().getName());
        params.put("url", event.getPrintInfo().getUrl());
        params.put("ztErrorList", event.getPrintInfo().getZtErrorList());
        //定义headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);

        long start = System.currentTimeMillis();
        logger.info("开始远程调用");
        //执行HTTP请求
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            JSONObject jsonObject = JSONObject.parseObject(response.getBody());
            //json对象转Map
            Map<String, Object> map = (Map<String, Object>) jsonObject;
            logger.info("map ----> " + map.toString());
        } catch (RestClientException e) {
            logger.error(e.getMessage());
        } finally {
            long end = System.currentTimeMillis();
            logger.info("==sendHttpPost 调用远程方法结束==");
            logger.info("==sendHttpPost cost==" + (end - start));
        }
    }*/



}
