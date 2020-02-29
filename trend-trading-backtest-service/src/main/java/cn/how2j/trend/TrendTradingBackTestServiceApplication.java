package cn.how2j.trend;

import cn.how2j.trend.util.CheckPortAbledUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * describe:
 *
 * @author 王立朝
 * @EnableFeignClients 这个注解用与启动 Feign 方式。
 * @date 2020/02/29
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class TrendTradingBackTestServiceApplication {
    public static void main(String[] args) {
        int port = 0;
        int defaultPort = 8051;
        //注册中心端口号
        int eurekaPort = 8761;
        //检测注册中心服务是否启动
        CheckPortAbledUtil.checkEurekaServerPortOpen(eurekaPort);
        port = CheckPortAbledUtil.initPort(args, port);
        //5秒之内如果不输入，则使用默认端口号
        port = CheckPortAbledUtil.writePort(defaultPort, port);
        //检测端口是否被占用
        CheckPortAbledUtil.checkUsableLocalPort(port);
        new SpringApplicationBuilder(TrendTradingBackTestServiceApplication.class).
                properties("server.port=" + port).run(args);
    }
}
