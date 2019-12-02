package cn.how2j.trend.web;

import cn.how2j.trend.pojo.Index;
import cn.how2j.trend.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王立朝
 * @date 2019/12/2
 * @description:指数类
 */
@RestController
public class IndexController {
    @Autowired
    IndexService indexService;

    @GetMapping("/getCodes")
    public List<Index> get() throws Exception {
        return indexService.fetchIndexexFromThirdPart();
    }
}