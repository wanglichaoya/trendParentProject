package cn.how2j.trend.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * describe:视图控制器
 *
 * @author 王立朝
 * @date 2020/03/01
 */
@Controller
public class ViewController {

    @GetMapping("/")
    public String view() {
        return "view";
    }
}
