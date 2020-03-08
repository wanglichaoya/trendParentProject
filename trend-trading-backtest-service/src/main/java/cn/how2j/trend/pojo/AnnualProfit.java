package cn.how2j.trend.pojo;

/**
 * describe:每年收益实体类
 *
 * @author 王立朝
 * @date 2020/03/07
 */
public class AnnualProfit {

    //年份
    private int year;
    //指数收益
    private float indexIncome;
    //趋势收益
    private float trendIncome;

    @Override
    public String toString() {
        return "AnnualProfit{" +
                "year=" + year +
                ", indexIncome=" + indexIncome +
                ", trendIncome=" + trendIncome +
                '}';
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getIndexIncome() {
        return indexIncome;
    }

    public void setIndexIncome(float indexIncome) {
        this.indexIncome = indexIncome;
    }

    public float getTrendIncome() {
        return trendIncome;
    }

    public void setTrendIncome(float trendIncome) {
        this.trendIncome = trendIncome;
    }
}
