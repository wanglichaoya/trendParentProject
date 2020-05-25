package cn.how2j.trend.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * describe:视图控制器
 * @RefreshScope: 表示允许刷新
 * @author 王立朝
 * @date 2020/03/01
 */
@Controller
@RefreshScope
public class ViewController {
    @Value("${version}")
    String version;

    @GetMapping("/")
    public String view(Model model) {
        model.addAttribute("version", version);
        return "view";
    }
}
