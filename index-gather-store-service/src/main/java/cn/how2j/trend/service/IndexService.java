package cn.how2j.trend.service;

import cn.how2j.trend.pojo.Index;
import cn.how2j.trend.util.SpringContextUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 王立朝
 * @date 2019/11/30
 * @description: 服务类
 * @
 */
@Service
@CacheConfig(cacheNames = "indexes")
public class IndexService {

    @Autowired
    RestTemplate restTemplate;

    private List<Index> indexList;

    @HystrixCommand(fallbackMethod = "thirdPartNotConnected")
    public List<Index> fresh() {
        //获取第三方数据
        indexList = fetchIndexexFromThirdPart();
        System.out.println("第三方的数据大小为：" + indexList.size());
        IndexService indexService = SpringContextUtil.getBean(IndexService.class);
        //从redis中删除数据
        indexService.remove();
        //保存到redis中数据
        return indexService.store();
    }

    /**
     * redis中删除数据的方法
     */
    @CacheEvict(allEntries = true)
    public void remove() {
    }

    /**
     * 保存到redis中的方法
     **/
    @Cacheable(key = "'all_codes'")
    public List<Index> store() {
        return indexList;
    }

    /**
     * 从redis中获取数据的方法
     **/
    @Cacheable(key = "'all_codes'")
    public List<Index> get() {
        return CollUtil.toList();
    }

    /**
     * @HystrixCommand(fallbackMethod = "thirdPartNotConnected"):
     * 表示如果fetch_indexes_from_third_part获取失败了，就自动调用 third_part_not_connected
     * @Cacheable(key = "all_codes"):表示保存到 redis 用的 key 就会使 all_codes.
     **/
    /*@HystrixCommand(fallbackMethod = "thirdPartNotConnected")
    @Cacheable(key = "'all_codes'")*/
    public List<Index> fetchIndexexFromThirdPart() {
        //从第三方获取数据
        //RestTemplate: 是Spring提供的RestTemplate类，用于在应用中调用Rest服务
        List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json", List.class);
        return map2Index(temp);
    }

    public List<Index> thirdPartNotConnected() {
        System.out.println("thirdPartNotConnected");
        Index index = new Index();
        index.setName("无效的指数代码");
        index.setCode("000000");
        return CollectionUtil.toList(index);
    }

    private List<Index> map2Index(List<Map> temp) {
        List<Index> indexes = new ArrayList<>();
        for (Map map : temp) {
            String code = map.get("code").toString();
            String name = map.get("name").toString();
            Index index = new Index();
            index.setCode(code);
            index.setName(name);
            indexes.add(index);
        }
        return indexes;
    }


}
