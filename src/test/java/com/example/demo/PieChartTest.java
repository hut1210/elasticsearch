package com.example.demo;

import com.example.demo.dto.PieChart;
import com.example.demo.dto.PieChartCollect;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/28 13:59
 */
public class PieChartTest {
    public static void main(String[] args) {
        Map map = new HashMap();
        List<PieChartCollect> pieChartCollectList = new ArrayList<>();

        PieChart pieChart = new PieChart();
        pieChart.setTitle("已签收");
        pieChart.setNum(20);


        PieChart pieChart2 = new PieChart();
        pieChart2.setTitle("未签收");
        pieChart2.setNum(10);

        List<PieChart> list = new ArrayList<>();
        list.add(pieChart);
        list.add(pieChart2);

        PieChartCollect pieChartCollect =  new PieChartCollect();
        pieChartCollect.setTitle("配送状态占比");
        pieChartCollect.setList(list);

        pieChartCollectList.add(pieChartCollect);
        //***********************************************
        PieChart pieChart3 = new PieChart();
        pieChart3.setTitle("京东自营");
        pieChart3.setNum(5945);


        PieChart pieChart4 = new PieChart();
        pieChart4.setTitle("终端共配");
        pieChart4.setNum(10);

        List<PieChart> list2 = new ArrayList<>();
        list2.add(pieChart3);
        list2.add(pieChart4);

        PieChartCollect pieChartCollect2 =  new PieChartCollect();
        pieChartCollect2.setTitle("业务类型占比");
        pieChartCollect2.setTotalAmount(10000);
        pieChartCollect2.setList(list2);

        pieChartCollectList.add(pieChartCollect2);

        System.out.println(pieChartCollectList.toString());
        test();

    }

    public static void test(){
        Map peisong = new HashMap();
        List<Map> ps=new ArrayList<>();
        Map map1 = new HashMap();
        map1.put("subTitle","已签收");
        map1.put("num",10450);

        Map map2 = new HashMap();
        map2.put("subTitle","未签收");
        map2.put("num",34);

        ps.add(map1);
        ps.add(map2);

        peisong.put("title","配送状态占比");
        peisong.put("data",ps);

        Map ywlx = new HashMap();
        List<Map> yw = new ArrayList<>();
        Map map3 = new HashMap();
        map3.put("subTitle","京东自营");
        map3.put("num",5945);

        Map map4 = new HashMap();
        map4.put("subTitle","终端共配");
        map4.put("num",4055);

        yw.add(map3);
        yw.add(map4);
        ywlx.put("title","业务类型占比");
        ywlx.put("total",10000);
        ywlx.put("data",yw);


        Map gply = new HashMap();
        List<Map> gp = new ArrayList<>();
        Map map5 = new HashMap();
        map5.put("subTitle","圆通");
        map5.put("num",40213);
        map5.put("percent","14.57%");

        Map map6 = new HashMap();
        map6.put("subTitle","顺丰");
        map6.put("num",40213);
        map6.put("percent","14.57%");

        Map map7 = new HashMap();
        map7.put("subTitle","中通");
        map7.put("num",40213);
        map7.put("percent","14.57%");

        Map map8 = new HashMap();
        map8.put("subTitle","韵达");
        map8.put("num",40213);
        map8.put("percent","14.57%");

        Map map9 = new HashMap();
        map9.put("subTitle","申通");
        map9.put("num",40213);
        map9.put("percent","14.57%");

        gp.add(map5);
        gp.add(map6);
        gp.add(map7);
        gp.add(map8);
        gp.add(map9);
        gply.put("title","业务类型占比");
        gply.put("data",gp);


        Map result = new HashMap();
        result.put("distributionStatusPieChat",peisong);
        result.put("businessTypePieChat",ywlx);
        result.put("commonDistributionSourcePieChat",gply);
        System.out.println(result);
        System.out.println(JSONObject.toJSONString(result));

    }
}
