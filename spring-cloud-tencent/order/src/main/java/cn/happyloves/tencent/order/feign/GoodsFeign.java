package cn.happyloves.tencent.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zc
 * @date 2023/2/10 20:50
 */

@FeignClient("goods")
public interface GoodsFeign {

    @GetMapping("/goodsName")
    public String getGoodsName();
}
