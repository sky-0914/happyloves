package cn.happyloves.tencent.order.api;

import cn.happyloves.tencent.order.config.OrderConfig;
import cn.happyloves.tencent.order.feign.GoodsFeign;
import com.tencent.cloud.common.metadata.MetadataContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zc
 * @date 2023/2/7 22:09
 */
@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderConfig orderConfig;

    @Value("${common.name:甲}")
    private String commonName;

    @Value("${price:10000}")
    private int price;

    @Autowired
    private GoodsFeign goodsFeign;

    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }


    @GetMapping("/test2")
    public String test2(int i) throws Exception {
        if (i == 0) {
            throw new Exception("lalalal");
        } else if (i > 0) {
            Thread.sleep(i);
        }
        return i + "";
    }

    @GetMapping("/test3")
    public OrderConfig test3() {
        return orderConfig;
    }

    @GetMapping("/commonName")
    public String commonName() {
        return commonName;
    }

    @GetMapping("/orderInfo")
    public String orderInfo() {
        log.info("orderInfo");
        final String goodsName = goodsFeign.getGoodsName();
        // 获取可传递的元数据映射表
        final Map<String, String> transitiveMetadata = MetadataContextHolder.get().getTransitiveMetadata();
        // 获取上游传递过来的一次性元数据
        Map<String, String> upstreamDisposableMetadatas = MetadataContextHolder.getAllDisposableMetadata(true);
        // 获取本地配置的一次性元数据
        Map<String, String> localDisposableMetadatas = MetadataContextHolder.getAllDisposableMetadata(false);
        log.info("可传递的元数据映射表:{}, 上游传递过来的一次性元数据:{}, 本地配置的一次性元数据:{}", transitiveMetadata, upstreamDisposableMetadatas, localDisposableMetadatas);

        return goodsName + " price:" + price;
    }
}
