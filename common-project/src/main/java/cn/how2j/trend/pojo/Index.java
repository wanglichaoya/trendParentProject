package cn.how2j.trend.pojo;

import java.io.Serializable;

/**
 * describe:指数类
 *
 * @author 王立朝
 * @date 2020/02/18
 */
public class Index implements Serializable {

    String code ;
    String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Index(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Index() {
    }
}
