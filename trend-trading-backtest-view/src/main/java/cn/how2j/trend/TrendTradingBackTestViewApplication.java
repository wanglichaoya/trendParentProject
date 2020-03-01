package cn.how2j.trend;

import cn.how2j.trend.util.CheckPortAbledUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * describe:
 *
 * @author 王立朝
 * @date 2020/03/01
 */
@SpringBootApplication
@EnableEurekaClient
public class TrendTradingBackTestViewApplication {
    public static void main(String[] args){
        int port = 0;
        int defaultPort = 8041;
        int eurekaSerivcePort = 8761;
        //检测注册中心服务是否启动
        CheckPortAbledUtil.checkEurekaServerPortOpen(eurekaSerivcePort);
        port = CheckPortAbledUtil.initPort(args, port);
        //5秒之内如果不输入，则使用默认端口号
        port = CheckPortAbledUtil.writePort(defaultPort, port);
        //检测端口是否被占用
        CheckPortAbledUtil.checkUsableLocalPort(port);
        new SpringApplicationBuilder(TrendTradingBackTestViewApplication.class).
                properties("server.port=" + port).run(args);
    }
}
