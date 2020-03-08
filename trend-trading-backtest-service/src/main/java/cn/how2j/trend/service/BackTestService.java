package cn.how2j.trend.service;

import cn.how2j.trend.client.IndexDateClient;
import cn.how2j.trend.pojo.AnnualProfit;
import cn.how2j.trend.pojo.IndexData;
import cn.how2j.trend.pojo.Profit;
import cn.how2j.trend.pojo.Trade;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * describe:用于提供所有模拟回测数据的微服务
 *
 * @author 王立朝
 * @date 2020/02/29
 */
@Service
public class BackTestService {
    //@Autowired
    @Resource
    IndexDateClient indexDateClient;

    public List<IndexData> listIndexDate(String code) {
        List<IndexData> indexDataList = indexDateClient.getIndexData(code);
        CollectionUtil.reverse(indexDataList);
        /*for (IndexData indexData : indexDataList) {
            System.out.println(indexData.getDate());
        }*/
        return indexDataList;
    }

    /**
     * simulate
     * 参数含义：
     * ma: 均线（20天的所有值相加，除以20）     ;sellRate:   ; buyRate:    ;serviceCharge: 手续费   ; indexDataList:     ;
     **/
    public Map<String, Object> simulate(int ma, float sellRate, float buyRate,
                                        float serviceCharge, List<IndexData> indexDataList) {
        List<Profit> profits = new ArrayList<>();
        //收益集合
        List<Trade> trades = new ArrayList<>();
        //initCash: 初始金额
        float initCash = 1000;
        //cash:金额
        float cash = initCash;
        //share： 份额，如果份额=0，表示没有买；如果份额 不等于 0，表示没有卖，说明手里面还有股票。
        float share = 0;
        float value = 0;
        float init = 0;

        //盈利的次数
        int winCount = 0;
        //总盈利率
        float totalWinRate = 0;
        //平均盈利率
        float avgWinRate = 0;
        //总亏损次数
        int lossCount = 0;
        //总亏损率
        float totalLossRate = 0;
        //平均亏损率
        float avgLossRate;

        //如果 indexDataList  不为空，就设置init  为当日的收盘价；  isEmpty() 如果为空： 返回true
        if (!indexDataList.isEmpty()) {
            //init: 当前指数数据里面的第一条值
            init = indexDataList.get(0).getClosePoint();
        }
        for (int i = 0; i < indexDataList.size(); i++) {
            IndexData indexData = indexDataList.get(i);

            //收盘价
            float closePoint = indexData.getClosePoint();
            //MA均值
            float avg = getMA(i, ma, indexDataList);
            //找出这20天的最大值
            float max = getMax(i, ma, indexDataList);
            //increase_rate: 当前的增长率是多少
            float increase_rate = closePoint / avg;
            //decrease_rate: 当前亏了多少
            float decrease_rate = closePoint / max;

            // avg=0 ,表示最最开始没有值，刚开始，或者说还没有拿到数据，或者说这只基金刚开始开放
            //下面的不懂

            //均值不等于0，说明前20天是可以拿到值的
            if (avg != 0) {
                //买超过了均线
                if (increase_rate > buyRate) {
                    //如果没买
                    if (0 == share) {
                        //可以买了，份额 等于  金钱除以 收盘价，全部买了，那么本金就为0。
                        share = cash / closePoint;
                        cash = 0;

                        //交易明细
                        Trade trade = new Trade();
                        trade.setBuyDate(indexData.getDate());
                        trade.setBuyClosePoint(indexData.getClosePoint());
                        trade.setSellDate("n/a");
                        trade.setSellClosePoint(0);
                        trades.add(trade);
                    }
                }
                //sell 低于卖点
                else if (decrease_rate < sellRate) {
                    //如果没卖，这个时候已经满足卖的条件，可以卖出了
                    if (0 != share) {
                        //最终到手的金钱= 当前的收盘价 * 份额* - 份额* 服务费用
                        cash = closePoint * share * (1 - serviceCharge);
                        share = 0;

                        Trade trade = trades.get(trades.size() - 1);
                        trade.setSellDate(indexData.getDate());
                        trade.setSellClosePoint(indexData.getClosePoint());
                        //收益率
                        float rate = cash / initCash;
                        trade.setRate(rate);

                        //计算平均盈利或者亏损利率
                        //卖点 > 买点， 盈利；否则亏损
                        if (trade.getSellClosePoint() - trade.getBuyClosePoint() > 0) {
                            totalWinRate += (trade.getSellClosePoint() - trade.getBuyClosePoint()) / trade.getBuyClosePoint();
                            winCount++;
                        } else {
                            totalLossRate += (trade.getSellClosePoint() - trade.getBuyClosePoint()) / trade.getBuyClosePoint();
                            lossCount++;
                        }

                    }
                } else {
                    //什么都不做
                }
            }
            //如果份额不等于0 ，那么就说明，当前拥有理财产品，所拥有的  价值 = 收盘价 * 份额。
            //否则如果份额  =0 ，说明 没有拥有理财产品，拥有现金。
            if (share != 0) {
                value = closePoint * share;
            } else {
                value = cash;
            }
            //赚到的比率  = 价值 / 初始金额
            float rate = value / initCash;

            Profit profit = new Profit();
            profit.setDate(indexData.getDate());
            //  赚到的比率 *  价值， 就可以知道到底赚了多少钱。
            profit.setValue(rate * value);
            System.out.println("profit.value= " + profit.getValue() + "日期" + profit.getDate());
            profits.add(profit);
        }
        //平均亏损率
        avgLossRate = totalLossRate / lossCount;
        //平均盈利率
        avgWinRate = totalWinRate / winCount;

        //每年的收益
        List<AnnualProfit> annualProfits = caculateAnnualProfits(indexDataList, profits);

        Map<String, Object> map = new HashMap<>();
        map.put("profits", profits);
        map.put("trades", trades);

        map.put("winCount", winCount);
        map.put("lossCount", lossCount);
        map.put("avgWinRate", avgWinRate);
        map.put("avgLossRate", avgLossRate);

        map.put("annualProfits", annualProfits);
        return map;
    }

    /**
     * @Param day: 天数，向前推多少天。
     * @Param i:  当前的值。
     **/
    private static float getMax(int i, int day, List<IndexData> list) {
        //往前推 day天
        int start = i - 1 - day;
        if (start < 0) {
            start = 0;
        }
        int now = i - 1;
        //感觉这里 有问题，待会再试一下
        if (start < 0) {
            return 0;
        }
        float max = 0;
        for (int j = start; j < now; j++) {
            IndexData bean = list.get(j);
            if (bean.getClosePoint() > max) {
                max = bean.getClosePoint();
            }
        }
        return max;
    }

    private static float getMA(int i, int ma, List<IndexData> list) {
        int start = i - 1 - ma;
        int now = i - 1;

        if (start < 0)
            return 0;

        float sum = 0;
        float avg = 0;
        for (int j = start; j < now; j++) {
            IndexData bean = list.get(j);
            sum += bean.getClosePoint();
        }
        avg = sum / (now - start);
        return avg;
    }

    /**
     * 用于计算当前的时间范围是多少年。
     **/
    public float getYear(List<IndexData> allIndexDataList) {
        float year;
        String sStartDate = allIndexDataList.get(0).getDate();
        String sEndDate = allIndexDataList.get(allIndexDataList.size() - 1).getDate();
        Date dateStart = DateUtil.parse(sStartDate);
        Date dateEnd = DateUtil.parse(sEndDate);
        long days = DateUtil.between(dateStart, dateEnd, DateUnit.DAY);
        year = days / 365f;
        return year;
    }

    /**
     * 根据日期获取日期里面的年份
     **/
    private int getYear(String date) {
        String year = StrUtil.subBefore(date, '-', false);
        return Convert.toInt(year);
    }

    /**
     * 计算某一年的指数收益
     **/
/*    private float getIndexIncome(int year, List<IndexData> indexDates) {
        IndexData first = null;
        IndexData last = null;
        for (IndexData indexData : indexDates) {
            String strDate = indexData.getDate();
            int currentYear = getYear(strDate);
            if (currentYear == year) {
                if (null == first) {
                    first = indexData;
                    last = indexData;
                }
            }
        }
        return (last.getClosePoint() - first.getClosePoint()) / first.getClosePoint();
    }*/

    /**
     * 计算某一年的趋势投资收益
     **/
/*    private float getTrendIncome(int year, List<Profit> profitList) {
        Profit first = null;
        Profit last = null;
        for (Profit profit : profitList) {
            String strDate = profit.getDate();
            int currentYear = getYear(strDate);
            if (currentYear == year) {
                if (null == first) {
                    first = profit;
                    last = profit;
                }
            }
            if (currentYear > year) {
                break;
            }
        }
        return (last.getValue() - first.getValue()) / first.getValue();
    }*/

    private float getIndexIncome(int year, List<IndexData> indexDatas) {
        IndexData first=null;
        IndexData last=null;

        for (IndexData indexData : indexDatas) {
            String strDate = indexData.getDate();
//          Date date = DateUtil.parse(strDate);
            int currentYear = getYear(strDate);

            if(currentYear == year) {
                if(null==first)
                    first = indexData;
                last = indexData;
            }
        }
        return (last.getClosePoint() - first.getClosePoint()) / first.getClosePoint();
    }
    private float getTrendIncome(int year, List<Profit> profits) {
        Profit first=null;
        Profit last=null;

        for (Profit profit : profits) {
            String strDate = profit.getDate();
            int currentYear = getYear(strDate);

            if(currentYear == year) {
                if(null==first)
                    first = profit;
                last = profit;
            }
            if(currentYear > year)
                break;
        }
        return (last.getValue() - first.getValue()) / first.getValue();
    }

    /**
     * 计算完整时间范围内，每一年的指数投资收益和趋势投资收益
     **/
    private List<AnnualProfit> caculateAnnualProfits(List<IndexData> indexDatas, List<Profit> profitList) {
        List<AnnualProfit> result = new ArrayList<>();

        String strStartDate = indexDatas.get(0).getDate();
        String strEndDate = indexDatas.get(indexDatas.size() - 1).getDate();
        Date startDate = DateUtil.parse(strStartDate);
        Date endDate = DateUtil.parse(strEndDate);

        int startYear = DateUtil.year(startDate);
        int endYear = DateUtil.year(endDate);

        for (int year = startYear; year <= endYear; year++) {
            AnnualProfit annualProfit = new AnnualProfit();

            //指数收益
            float indexIncome = getIndexIncome(year, indexDatas);
            //趋势投资收益
            float trendIncome = getTrendIncome(year, profitList);

            annualProfit.setYear(year);
            annualProfit.setIndexIncome(indexIncome);
            annualProfit.setTrendIncome(trendIncome);
            result.add(annualProfit);

        }
        return result;

    }


}
