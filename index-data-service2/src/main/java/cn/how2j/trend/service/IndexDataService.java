package cn.how2j.trend.service;

import cn.how2j.trend.pojo.IndexData;
import cn.hutool.core.collection.CollUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * describe:
 *
 * @author 王立朝
 * @date 2020/02/28
 */
@Service
@CacheConfig(cacheNames = "index_datas")
public class IndexDataService {

    /**
     * @Cacheable: 表示首先从redis中获取 key 的数据，如果没有，就执行方法里面的数据
     * **/
    @Cacheable(key = "'indexData-code-'+#p0")
    public List<IndexData> get(String code){
        return CollUtil.toList();
    }
}
