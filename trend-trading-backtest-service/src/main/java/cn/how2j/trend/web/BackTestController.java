package cn.how2j.trend.web;

import cn.how2j.trend.pojo.IndexData;
import cn.how2j.trend.service.BackTestService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * describe:模拟回测控制器
 *
 * @author 王立朝
 * @date 2020/02/29
 */
@RestController
public class BackTestController {
    @Autowired
    BackTestService backTestService;

    @GetMapping("/simulate/{code}/{startDate}/{endDate}")
    //@CrossOrigin跨域
    @CrossOrigin
    public Map<String, Object> backTest(@PathVariable("code") String code,
                                        @PathVariable("startDate") String strStartDate,
                                        @PathVariable("endDate") String strEndDate) {

        List<IndexData> allIndexDatas = backTestService.listIndexDate(code);
        //获取所有数据的第一条数据的开始时间
        String indexStartDate = allIndexDatas.get(0).getDate();
        String indexEndDate = allIndexDatas.get(allIndexDatas.size() - 1).getDate();
        allIndexDatas = filterByDateRange(allIndexDatas, strStartDate, strEndDate);
        Map<String, Object> result = new HashMap<>();
        result.put("indexDatas", allIndexDatas);
        result.put("indexStartDate", indexStartDate);
        result.put("indexEndDate", indexEndDate);
        return result;

    }

    //对指定的日期的数据进行过滤
    private List<IndexData> filterByDateRange(List<IndexData> allIndexDatas, String strStartDate, String strEndDate) {
        //如果开始时间和结束时间都为空，则返回所有的数据
        if (StrUtil.isBlankOrUndefined(strStartDate) || StrUtil.isBlankOrUndefined(strEndDate)) {
            return allIndexDatas;
        } else {
            List<IndexData> indexDataList = new ArrayList<>();
            //把日期类型的字符串转换为日期对象
            Date startDate = DateUtil.parse(strStartDate);
            Date endDate = DateUtil.parse(strEndDate);
            for (IndexData indexData : allIndexDatas) {
                Date date = DateUtil.parse(indexData.getDate());
                //判断日期是否在开始和结束时间之间，如果是就放入到List集合中
                if (date.getTime() >= startDate.getTime() && date.getTime() <= endDate.getTime()) {
                    indexDataList.add(indexData);
                }
            }

            return indexDataList;
        }


    }
}
