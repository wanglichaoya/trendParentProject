package cn.how2j.trend;

import cn.how2j.trend.util.CheckPortAbledUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 王立朝
 * @date 2019/11/30
 * @description:
 * @EnableEurekaServer: 表示这是一个注册中心服务器
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args){
        //8761:这个端口是默认的，后面的子项目都会访问这个端口号
        int port = 8761;
        //检测端口是否被占用
        CheckPortAbledUtil.checkUsableLocalPort(port);
        new SpringApplicationBuilder(EurekaServerApplication.class).properties("server.port="+port).run(args);
    }
}
