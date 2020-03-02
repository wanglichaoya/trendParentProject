package cn.how2j.trend.client;

import cn.how2j.trend.pojo.IndexData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * describe: 使用Feign模式从其他微服务中获取数据
 *
 * @FeignClient(value="INDEX-DATA-SERVICE2"
 * 下面通过name或者value指定服务名，然后根据服务名调用 getIndexData()  服务。
 * @author 王立朝
 * @date 2020/02/28
 */
@FeignClient(value = "INDEX-DATA-SERVICE2", fallback = IndexDataClientFeignHystrix.class)
public interface IndexDateClient {
    @GetMapping("/data/{code}")
    List<IndexData> getIndexData(@PathVariable("code") String code);
}
