package cn.how2j.trend;

import brave.sampler.Sampler;
import cn.how2j.trend.util.CheckPortAbledUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * describe:
 *
 * @author 王立朝
 * @date 2020/02/28
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class IndexZuulServiceApplication {
    public static void main(String[] args) {
        int port = 8031;
        CheckPortAbledUtil.checkUsableLocalPort(port);
        new SpringApplicationBuilder(IndexZuulServiceApplication.class).properties("server.port=" + port).run(args);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
