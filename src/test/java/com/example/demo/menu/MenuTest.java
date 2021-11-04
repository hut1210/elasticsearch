package com.example.demo.menu;

import com.example.demo.menu.domain.Menu;
import com.example.demo.util.BeanUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;

import java.util.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/10/20 17:26
 */
@Slf4j
public class MenuTest {

    private static Gson GSON;

    static {
        GSON = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().create();
    }

    //private static final String topMenu = "BTOP001,BTOP002,BTOP003,BTOP004,BTOP005,BTOP006,BTOP007,BTOP008,BTOP009,BTOP010,BTOP011,BTOP012,BTOP019,BTOP021";
    private static final String topMenu = "BTOP001";
    //BTOP013,BTOP014,BTOP015,BTOP016,BTOP017,BTOP018,
    private static final String topJumpMenu = "BTOP101,BTOP019";
    //,BTOP1002,BTOP1003,BTOP1004,BTOP1005,BTOP1006
    private static final String topChildMenu = "BTOP1001";

    private static final String topMenu1 = "CPTOP001";
    //BTOP013,BTOP014,BTOP015,BTOP016,BTOP017,BTOP018,
    private static final String topJumpMenu1 = "CPTOP101";
    private static final String topChildMenu1 = "CPTOP1001,CPTOP1002,CPTOP1003,CPTOP1004,CPTOP1005,CPTOP1006";

    public static void main(String[] args) {
        test2();
    }

    public static void test1(){
        /**
         * {"id":2,"name":"云物流平台-商家端","code":"clps_wly_b","level":1,"seq":0,"type":0}
         * {"id":105453,"name":"京东数智","code":"BTOP101","url":"","parentId":2,"level":1,"seq":101,"type":0}
         * {"id":105438,"name":"数智中心","code":"BTOP099","url":"https://discc-b.jdl.cn","parentId":105453,"level":1,"seq":1,"type":0}
         * {"id":104575,"name":"云仓大屏","code":"BTOP100","url":"https://reportp.jclps.com/monitor/blarge/gotoNewLargeMonitorPage.do","parentId":105453,"level":1,"seq":2,"type":0}
         */
        Menu menu0 = new Menu();
        menu0.setId(2L);
        menu0.setCode("clps_wly_b");
        menu0.setName("云物流平台-商家端");
        menu0.setLevel(1);
        menu0.setSeq(0);
        menu0.setType(0);

        Menu menu1 = new Menu();
        menu1.setId(105453L);
        menu1.setCode("BTOP101");
        menu1.setName("京东数智");
        menu1.setParentId(2L);
        menu1.setLevel(1);
        menu1.setSeq(101);
        menu1.setType(0);

        Menu menu102 = new Menu();
        menu102.setId(105454L);
        menu102.setCode("BTOP019");
        menu102.setName("青职大屏");
        menu102.setParentId(2L);
        menu102.setLevel(1);
        menu102.setSeq(19);
        menu102.setType(0);

        Menu menu2 = new Menu();
        menu2.setId(105453L);
        menu2.setCode("BTOP099");
        menu2.setName("数智中心");
        menu2.setUrl("https://discc-b.jdl.cn");
        menu2.setParentId(105453L);
        menu2.setLevel(1);
        menu2.setSeq(1);
        menu2.setType(0);

        Menu menu3 = new Menu();
        menu3.setId(105453L);
        menu3.setCode("BTOP100");
        menu3.setName("云仓大屏");
        menu3.setUrl("https://reportp.jclps.com/monitor/blarge/gotoNewLargeMonitorPage.do");
        menu3.setParentId(105453L);
        menu3.setLevel(1);
        menu3.setSeq(2);
        menu3.setType(0);

        List<Menu> menusLevel = new ArrayList<>();
        menusLevel.add(menu0);
        menusLevel.add(menu102);
        menusLevel.add(menu1);
        menusLevel.add(menu2);
        menusLevel.add(menu3);

        System.out.println(GSON.toJson(menusLevel));
        List<Menu> menusList = new ArrayList<>();
        List<Menu> children0 = new ArrayList<>();

        Menu menuP0 = new Menu();
        BeanUtils.copyProperties(menu0, menuP0);
        menuP0.setChildren(children0);
        List<Menu> children1 = new ArrayList<>();

        children1.add(menu2);
        children1.add(menu3);

        Menu menuP1 = new Menu();
        BeanUtils.copyProperties(menu1, menuP1);
        menuP1.setChildren(children1);

        Menu menuP102 = new Menu();
        BeanUtils.copyProperties(menu102, menuP102);

        children0.add(menuP1);
        children0.add(menuP102);

        menusList.add(menuP0);
        System.out.println(GSON.toJson(menusList));
        //getSellerTopMenu(menusLevel, menusList);

        //合作伙伴端
        getParenterTopMenu(menusLevel, menusList);
    }

    public static void test2(){
        Menu menu0 = new Menu();
        menu0.setId(2L);
        menu0.setCode("clps_wly_b");
        menu0.setName("云物流平台-商家端");
        menu0.setLevel(1);
        menu0.setSeq(0);
        menu0.setType(0);

        Menu menu1001 = new Menu();
        menu1001.setId(105454L);
        menu1001.setCode("BTOP1001");
        menu1001.setName("测试菜单");
        menu1001.setParentId(2L);
        menu1001.setLevel(1);
        menu1001.setSeq(1);
        menu1001.setType(0);

        List<Menu> menusLevel = new ArrayList<>();
        menusLevel.add(menu0);
        menusLevel.add(menu1001);

        System.out.println(GSON.toJson(menusLevel));
        List<Menu> menusList = new ArrayList<>();
        List<Menu> children0 = new ArrayList<>();

        Menu menuP0 = new Menu();
        BeanUtils.copyProperties(menu0, menuP0);
        menuP0.setChildren(children0);

        Menu menuP1 = new Menu();
        BeanUtils.copyProperties(menu1001, menuP1);
        children0.add(menuP1);

        menusList.add(menuP0);
        System.out.println(GSON.toJson(menusList));

        getSellerTopMenu(menusLevel,menusList);
    }

    private static void getSellerTopMenu(List<Menu> menusLevel, List<Menu> menusList) {
        if (StringUtils.isNoneBlank(topMenu, topChildMenu, topJumpMenu)) {
            String[] topMenus = topMenu.split(",");
            String[] topJumpMenus = topJumpMenu.split(",");
            String[] topChildMenus = topChildMenu.split(",");
//            String[] topChildMenus = {"TZZX"};
//            String[] topJumpMenus = {"clps_help"};
            Map<String, Integer> topMap = Maps.newHashMapWithExpectedSize(topMenus.length + topJumpMenus.length + topChildMenus.length);
            for (int i = 0; i < topMenus.length; i++) {
                topMap.put(topMenus[i], 0);
            }
            for (int i = 0; i < topJumpMenus.length; i++) {
                topMap.put(topJumpMenus[i], 1);
            }
            for (int i = 0; i < topChildMenus.length; i++) {
                topMap.put(topChildMenus[i], 2);
            }
            //将弹窗菜单复制到头部菜单数组中
            topMenus = joinArrays(topMenus, topJumpMenus);
            List<Menu> topList = Lists.newArrayListWithCapacity(topMenus.length + topJumpMenus.length);
            for (int i = 0; i < topMenus.length; i++) {
                try {
                    String topMenusCode = topMenus[i];
                    for (Iterator<Menu> it = menusLevel.iterator(); it.hasNext(); ) {
                        Menu menu = it.next();
                        if (menu.getCode().equals(topMenusCode)) {
                            it.remove();
                        }
                    }
                    for (Iterator<Menu> it = menusList.get(0).getChildren().iterator(); it.hasNext(); ) {
                        Menu menu = it.next();
                        if (menu.getCode().equals(topMenusCode)) {
                            getMenuChildren(menu, topMap);
                            menu.setType(topMap.get(menu.getCode()));
                            topList.add(menu);
                            it.remove();
                            break;
                        } else {
                            if (menu.getChildren() != null && menu.getChildren().size() > 0) {
                                if (getChildren(menu, topMenusCode, topList, topMap)) {
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("获取顶部菜单失败", e);
                }
            }
            sortTopMenu(topList);
            System.out.println(topList);
        }
    }

    private static void getParenterTopMenu(List<Menu> menusLevel, List<Menu> menusList) {
        if (StringUtils.isNotBlank(topMenu)) {
            String[] topMenus = topMenu.split(",");
//            topMenus = new String[]{"EM_SJGL","EM_KCGL"};
            String[] topJumpMenus = topJumpMenu.split(",");
//            topJumpMenus = new String[]{"EM_YYJK"};
            Map<String, Integer> topMap = Maps.newHashMapWithExpectedSize(topMenus.length + topJumpMenus.length);
            for (int i = 0; i < topMenus.length; i++) {
                topMap.put(topMenus[i], 0);
            }
            for (int i = 0; i < topJumpMenus.length; i++) {
                topMap.put(topJumpMenus[i], 1);
            }
            //将弹窗菜单复制到头部菜单数组中
            topMenus = joinArrays(topMenus, topJumpMenus);
            List<Menu> topList = Lists.newArrayListWithCapacity(topMenus.length);
            for (int i = 0; i < topMenus.length; i++) {
                try {
                    String topMenusCode = topMenus[i];
                    for (Iterator<Menu> it = menusLevel.iterator(); it.hasNext(); ) {
                        Menu menu = it.next();
                        if (menu.getCode().equals(topMenusCode)) {
                            it.remove();
                        }
                    }
                    if (menusList != null && menusList.size() != 0) {
                        for (Iterator<Menu> it = menusList.get(0).getChildren().iterator(); it.hasNext(); ) {
                            Menu menu = it.next();
                            if (menu.getCode().equals(topMenusCode)) {
                                getMenuChildren(menu);
                                menu.setType(topMap.get(menu.getCode()));
                                topList.add(menu);
                                it.remove();
                                break;
                            } else {
                                if (menu.getChildren() != null && menu.getChildren().size() > 0) {
                                    if (getChildren(menu, topMenusCode, topList, topMap)) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("获取顶部菜单失败", e);
                }
            }
            sortTopMenu(topList);
            System.out.println(topList);
        }
    }

    private static String[] joinArrays(String[] topMenus, String[] topJumpMenus) {
        String[] allTopMenu = new String[topMenus.length + topJumpMenus.length];
        System.arraycopy(topMenus, 0, allTopMenu, 0, topMenus.length);
        System.arraycopy(topJumpMenus, 0, allTopMenu, topMenus.length, topJumpMenus.length);
        return allTopMenu;
    }

    private static boolean getChildren(Menu menu, String topMenusCode, List<Menu> topList, Map<String, Integer> topMap) {
        boolean result = false;
        for (Iterator<Menu> it = menu.getChildren().iterator(); it.hasNext(); ) {
            Menu m = it.next();
            if (m.getCode().equals(topMenusCode)) {
                getMenuChildren(m, topMap);
                menu.setType(topMap.get(menu.getCode()));
                topList.add(m);
                it.remove();
                result = true;
                break;
            } else if (m.getChildren() != null && m.getChildren().size() > 0) {
                result = getChildren(m, topMenusCode, topList, topMap);
                if (result) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 获取子孙叶子节点
     *
     * @param menu
     */
    private static void getMenuChildren(Menu menu, Map<String, Integer> topMap) {
        List<Menu> children = new ArrayList<Menu>();
        if (menu.getChildren() != null && menu.getChildren().size() > 0) {
            for (Iterator<Menu> it = menu.getChildren().iterator(); it.hasNext(); ) {
                Menu menuChild = it.next();
                if (menuChild.getChildren() != null && menuChild.getChildren().size() > 0) {
                    it.remove();
                    children.addAll(menuChild.getChildren());
                }
            }
            menu.getChildren().addAll(children);
            for (Menu cMenu : menu.getChildren()) {
                cMenu.setType(topMap.get(cMenu.getCode()) == null ? 0 : topMap.get(cMenu.getCode()));
            }
        }
    }

    /**
     * 获取子孙叶子节点
     *
     * @param menu
     */
    private static void getMenuChildren(Menu menu) {
        List<Menu> children = new ArrayList<Menu>();
        if (menu.getChildren() != null && menu.getChildren().size() > 0) {
            for (Iterator<Menu> it = menu.getChildren().iterator(); it.hasNext(); ) {
                Menu menuChild = it.next();
                if (menuChild.getChildren() != null && menuChild.getChildren().size() > 0) {
                    it.remove();
                    children.addAll(menuChild.getChildren());
                }
            }
            menu.getChildren().addAll(children);
        }
    }

    private static void sortTopMenu(List<Menu> menuList) {
        Collections.sort(menuList, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if (o1.getSeq() >= o2.getSeq()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }
}
