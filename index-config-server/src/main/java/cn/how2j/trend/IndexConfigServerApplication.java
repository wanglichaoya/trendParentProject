package cn.how2j.trend;

import cn.how2j.trend.util.CheckPortAbledUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * describe:配置服务器启动类
 *
 * @author 王立朝
 * @date 2020/03/09
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
@EnableEurekaClient
public class IndexConfigServerApplication {
    public static void main(String[] args) {
        int port = 8060;

        int eurekaServerPort = 8761;

        CheckPortAbledUtil.checkEurekaServerPortOpen(eurekaServerPort);
        CheckPortAbledUtil.checkUsableLocalPort(port);
        new SpringApplicationBuilder(IndexConfigServerApplication.class).properties("server.port=" + port).run(args);
    }
}
