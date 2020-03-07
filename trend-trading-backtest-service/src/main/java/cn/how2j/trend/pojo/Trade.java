package cn.how2j.trend.pojo;

/**
 * describe:交易类
 * 交易类用于记录交易的购买日期，出售日期，购买盘点，出售盘点，收益
 *
 * @author 王立朝
 * @date 2020/03/04
 */
public class Trade {
    //购买日期
    private String buyDate;
    //出售日期
    private String sellDate;
    //购买盘点
    private float buyClosePoint;
    //出售盘点
    private float sellClosePoint;
    //收益
    private float rate;

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public float getBuyClosePoint() {
        return buyClosePoint;
    }

    public void setBuyClosePoint(float buyClosePoint) {
        this.buyClosePoint = buyClosePoint;
    }

    public float getSellClosePoint() {
        return sellClosePoint;
    }

    public void setSellClosePoint(float sellClosePoint) {
        this.sellClosePoint = sellClosePoint;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
