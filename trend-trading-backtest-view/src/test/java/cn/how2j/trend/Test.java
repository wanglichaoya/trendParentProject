package cn.how2j.trend;

import java.util.ArrayList;
import java.util.List;

/**
 * describe:
 *
 * @author 王立朝
 * @date 2020/03/04
 */
public class Test {
    public static void main(String[] args){
        List list = new ArrayList<>();
        list.add(11);
        list.add(221);
        list.add(32);
        list.add(87);
        list.add(9);
        list.add(36);
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        list.subList(0,list.size());
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}
