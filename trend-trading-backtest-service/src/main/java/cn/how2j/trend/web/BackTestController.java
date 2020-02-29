package cn.how2j.trend.web;

import cn.how2j.trend.pojo.IndexData;
import cn.how2j.trend.service.BackTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/simulate/{code}")
    //@CrossOrigin跨域
    @CrossOrigin
    public Map<String, Object> backTest(@PathVariable("code") String code) {
        List<IndexData> indexDataList = backTestService.listIndexDate(code);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("indexDatas", indexDataList);
        return result;

    }
}
