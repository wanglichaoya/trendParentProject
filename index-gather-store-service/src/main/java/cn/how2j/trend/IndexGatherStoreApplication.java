package cn.how2j.trend;

import cn.how2j.trend.util.CheckPortAbledUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
/**
 * @author 王立朝
 * @date 2019/11/30
 * @description:
 * @EnableEurekaClient:表示注册为一个微服务客户端
 * @EnableHystrix: 表示启动短路器
 * @EnableCaching 表示启动缓存
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableCaching
public class IndexGatherStoreApplication {
    public static void main(String[] args) {

        int port = 0;
        //默认的端口号
        int defaultPort = 8001;
        //注册中心端口号
        int eurekaServerPort = 8761;
        //redis 端口号
        int redisPort = 6379;
        port = defaultPort ;

       //检测注册中心服务是否启动
        CheckPortAbledUtil.checkEurekaServerPortOpen(eurekaServerPort);

        /**批量启动多个端口的方法**/
        port = CheckPortAbledUtil.initPort(args,port);

        //检测端口是否被占用
        CheckPortAbledUtil.checkUsableLocalPort(port);

        //检测Redis端口是否启用
        CheckPortAbledUtil.checkUsableRedisPort(redisPort);
        new SpringApplicationBuilder(IndexGatherStoreApplication.class).properties("server.port=" + port).run(args);

    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}