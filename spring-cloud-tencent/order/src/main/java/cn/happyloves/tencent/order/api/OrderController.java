package cn.happyloves.tencent.order.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zc
 * @date 2023/2/7 22:09
 */
@RestController
public class OrderController {

    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }
}
