package cn.how2j.trend.web;

import cn.how2j.trend.config.IpConfiguration;
import cn.how2j.trend.pojo.IndexData;
import cn.how2j.trend.service.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * describe:
 *
 * @author 王立朝
 * @date 2020/02/28
 */
@RestController
public class IndexDataController {
    @Autowired
    IndexDataService indexDataService;
    @Autowired
    IpConfiguration ipConfiguration;

    @GetMapping("/data/{code}")
    public List<IndexData> get(@PathVariable("code") String code){
      return indexDataService.get(code);
    }
}
