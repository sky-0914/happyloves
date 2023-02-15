package cn.happyloves.tencent.goods.api;

import com.tencent.cloud.common.metadata.MetadataContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zc
 * @date 2023/2/10 20:19
 */
@Slf4j
@RestController
public class GoodsController {

    @Value("${common.name:甲}")
    private String commonName;
    @Value("${type:}")
    private String type;

    @GetMapping("/test1")
    public String test1() {
        return "goods-test1";
    }

    @GetMapping("/commonName")
    public String commonName() {
        return commonName;
    }


    @GetMapping("/goodsName")
    public String getGoodsName(HttpServletRequest request) {
        log.info("getGoodsName");
        // 获取可传递的元数据映射表
        final Map<String, String> transitiveMetadata = MetadataContextHolder.get().getTransitiveMetadata();
        // 获取上游传递过来的一次性元数据
        Map<String, String> upstreamDisposableMetadatas = MetadataContextHolder.getAllDisposableMetadata(true);
        // 获取本地配置的一次性元数据
        Map<String, String> localDisposableMetadatas = MetadataContextHolder.getAllDisposableMetadata(false);
        log.info("可传递的元数据映射表:{}, 上游传递过来的一次性元数据:{}, 本地配置的一次性元数据:{}", transitiveMetadata, upstreamDisposableMetadatas, localDisposableMetadatas);
        return "Macbook" + type;
    }
}
