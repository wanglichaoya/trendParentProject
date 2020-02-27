package cn.how2j.trend;

import cn.how2j.trend.util.CheckPortAbledUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * describe:
 *
 * @author 王立朝
 * @date 2020/02/18
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCaching
public class IndexCodesApplication {


    public static void main(String[] args){
        int port =0;
        int defaultPort = 8011;
        int redisPort = 6379;
        int eurekaServerPort = 8761;

        //检测redis服务是否启动
        CheckPortAbledUtil.checkUsableRedisPort(redisPort);
        //检测eureka服务是否启动
        CheckPortAbledUtil.checkEurekaServerPortOpen(eurekaServerPort);

        //初始化启动端口
        port = CheckPortAbledUtil.initPort(args,port);
       //5秒内如果不输入端口号，就使用默认端口号
        port = CheckPortAbledUtil.writePort(defaultPort,port);
        //检测端口是否被占用
        CheckPortAbledUtil.checkUsableLocalPort(port);
        new SpringApplicationBuilder(IndexCodesApplication.class).properties("server.port=" + port).run(args);

    }


}
