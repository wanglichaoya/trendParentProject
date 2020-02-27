package cn.how2j.trend.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
        if (NetUtil.isUsableLocalPort(redisPort)) {
            System.err.printf("检查到端口%d 未启用，判断 redis 服务器没有启动，本服务无法使用，故退出%n", redisPort);
            System.exit(1);
        }
    }

    /**
     * 五秒之内如果不输入端口号，启动默认端口
     **/
    public static int writePort(int defaultPort, int port) {
        Future<Integer> future = ThreadUtil.execAsync(() -> {
            int p = 0;
            System.out.printf("请于5秒钟内输入端口号, 推荐  %d ,超过5秒将默认使用 %d %n", defaultPort, defaultPort);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String strPort = scanner.nextLine();
                if (!NumberUtil.isInteger(strPort)) {
                    System.err.println("只能是数字");
                    continue;
                } else {
                    p = Convert.toInt(strPort);
                    scanner.close();
                    break;
                }
            }
            return p;
        });
        try {
            port = future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            port = defaultPort;
        }
        return port;
    }
}
