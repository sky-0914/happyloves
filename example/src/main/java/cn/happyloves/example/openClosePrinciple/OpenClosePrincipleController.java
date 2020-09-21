package cn.happyloves.example.openClosePrinciple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 如果状态增加了，只需增加相应状态的类即可，也只需测试新增加的业务，这样就简单方便的实现了开闭原则。
 *
 * @author ZC
 * @date 2020/9/21 23:15
 */
@RestController
@RequestMapping("/openClosePrinciple")
public class OpenClosePrincipleController {
    @Autowired
    Map<String, IOpenClosePrinciple> openClosePrincipleMap;

    @GetMapping("/{name}")
    public String openClosePrinciple(@PathVariable String name) {
        return openClosePrincipleMap.get(name).testOut();
    }
}
