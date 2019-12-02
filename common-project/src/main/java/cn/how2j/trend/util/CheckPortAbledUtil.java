package cn.how2j.trend.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author 王立朝
 * @date 2019/11/30
 * @description:检测端口号是否被占用
 */
public class CheckPortAbledUtil {

    /**
     * 检测注册中心服务是否打开
     **/
    public static void checkEurekaServerPortOpen(int eurekaServerPort) {
        if (NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("检查到端口%d 未启用，判断 eureka 服务器没有启动，本服务无法使用，故退出%n", eurekaServerPort);
            System.exit(1);
        }
    }

    /**
     * 检测端口是被占用
     **/
    public static void checkUsableLocalPort(int port) {
        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port);
            System.exit(1);
        }
    }

    /**
     * 初始化的时候启动端口
     **/
    public static int initPort(String[] args, int port) {
        if (null != args && 0 != args.length) {
            for (String arg : args) {
                if (arg.startsWith("port=")) {
                    String strPort = StrUtil.subAfter(arg, "port=", true);
                    if (NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }
        return port;
    }

    /**
     * 检测redis端口号是否启动
     **/
    public static void checkUsableRedisPort(int redisPort) {
        if(NetUtil.isUsableLocalPort(redisPort)) {
            System.err.printf("检查到端口%d 未启用，判断 redis 服务器没有启动，本服务无法使用，故退出%n", redisPort );
            System.exit(1);
        }
    }
}
