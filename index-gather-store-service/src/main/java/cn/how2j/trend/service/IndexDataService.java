package cn.how2j.trend.service;

import cn.how2j.trend.pojo.IndexData;
import cn.how2j.trend.util.SpringContextUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * describe:获取指数数据服务类
 *
 * @author 王立朝
 * @date 2020/02/16
 */
@Service
@CacheConfig(cacheNames = "index_datas")
public class IndexDataService {
        private Map<String, List<IndexData>> stringListMap = new HashMap<>();
    @Autowired
    RestTemplate restTemplate;
    /**刷新**/
    @HystrixCommand(fallbackMethod = "thirdPartNotConnected")
    public List<IndexData> fresh(String code){
        List<IndexData> indexData = fetchIndexsFromThirdPart(code);
        stringListMap.put(code,indexData);
        System.out.println("code"+code);
        System.out.println("indexData"+stringListMap.get(code).size());
        IndexDataService indexDataService = SpringContextUtil.getBean(IndexDataService.class);
        indexDataService.remove(code);
        return indexDataService.store(code);
    }
    /**
     * 删除缓存
     * **/
    @CacheEvict(key = "'indexData-code-'+#p0")
    public void remove(String code){

    }
    @CachePut(key="'indexData-code-'+ #p0")
    public List<IndexData> store(String code){
        return stringListMap.get(code);
    }
    @Cacheable(key = "'indexData-code-'+#p0")
    public List<IndexData> get(String code){
        return CollectionUtil.toList();
    }
    private List<IndexData> fetchIndexsFromThirdPart(String code) {
        List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/"+code+".json",List.class);
        return map2IndexData(temp);
    }
    private List<IndexData> map2IndexData(List<Map> temp) {
        List<IndexData> indexDatas = new ArrayList<>();
        for (Map map : temp) {
            String date = map.get("date").toString();
            float closePoint = Convert.toFloat(map.get("closePoint"));
            IndexData indexData = new IndexData();

            indexData.setDate(date);
            indexData.setClosePoint(closePoint);
            indexDatas.add(indexData);
        }

        return indexDatas;
    }

    public List<IndexData> thirdPartNotConnected(String code){
        System.out.println("thirdPartNotConnected()");
        IndexData index= new IndexData();
        index.setClosePoint(0);
        index.setDate("n/a");
        return CollectionUtil.toList(index);
    }

}
