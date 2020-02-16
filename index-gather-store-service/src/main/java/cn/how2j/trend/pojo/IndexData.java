package cn.how2j.trend.pojo;
/**
 * 指数数据的实体类
 * **/
public class IndexData {

    String date;
    float closePoint;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getClosePoint() {
        return closePoint;
    }

    public void setClosePoint(float closePoint) {
        this.closePoint = closePoint;
    }

}