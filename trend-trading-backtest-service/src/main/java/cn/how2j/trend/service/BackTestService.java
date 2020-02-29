package cn.how2j.trend.service;

import cn.how2j.trend.client.IndexDateClient;
import cn.how2j.trend.pojo.IndexData;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * describe:用于提供所有模拟回测数据的微服务
 *
 * @author 王立朝
 * @date 2020/02/29
 */
@Service
public class BackTestService {
    @Autowired
    IndexDateClient indexDateClient;

    public List<IndexData> listIndexDate(String code) {
        List<IndexData> indexDataList = indexDateClient.getIndexData(code);
        CollectionUtil.reverse(indexDataList);
        for (IndexData indexData : indexDataList) {
            System.out.println(indexData.getDate());
        }
        return indexDataList;
    }


}
