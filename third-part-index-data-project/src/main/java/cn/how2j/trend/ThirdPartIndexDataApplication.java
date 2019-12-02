package cn.how2j.trend;

import cn.how2j.trend.util.CheckPortAbledUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 王立朝
 * @date 2019/11/30
 * @description:@EnableEurekaClient 表示注册为微服务
 */
@SpringBootApplication
@EnableEurekaClient
public class ThirdPartIndexDataApplication {

    public static void main(String[] args) {
        int port = 8090;
        int eurekaServerPort = 8761;


        //检测注册中心微服务是否启动
        CheckPortAbledUtil.checkEurekaServerPortOpen(eurekaServerPort);
        //启动端口
        port = CheckPortAbledUtil.initPort(args, port);

        //检测端口是否被占用
        CheckPortAbledUtil.checkUsableLocalPort(port);

        new SpringApplicationBuilder(ThirdPartIndexDataApplication.class).properties("server.port=" + port).run(args);
    }

}
