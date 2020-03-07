package cn.how2j.trend.pojo;

/**
 * describe:利润类
 * 有日期和数值两个属性，方便与 IndexData 做对比。
 *
 * @author 王立朝
 * @date 2020/03/04
 */
public class Profit {
    String date;
    float value;

    public Profit(String date, float value) {
        this.date = date;
        this.value = value;
    }

    public Profit() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
