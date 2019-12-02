package cn.how2j.trend.pojo;
import java.io.Serializable;
/**
 * @author 王立朝
 * @date 2019/12/2
 * @description:指数类
 */
public class Index implements Serializable {

     String code;
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
}
