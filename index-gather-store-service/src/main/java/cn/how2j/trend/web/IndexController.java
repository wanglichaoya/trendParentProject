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

    /*@GetMapping("/get")
    public List<Index> get() throws Exception {
        return indexService.fetchIndexexFromThirdPart();
    }*/

    /**
     * 刷新
     **/
    @GetMapping("/freshCodes")
    public List<Index> fresh() {
        return indexService.fresh();
    }

    /**
     * 获取
     **/
    @GetMapping("/getCodes")
    public List<Index> getCodes() {
        return indexService.get();
    }

    /**
     * 删除
     **/
    @GetMapping("/removeCodes")
    public String remove() {
        indexService.remove();
        return "从redis中删除数据成功！";
    }


}