package cn.happyloves.netty.tcpPackage.customprotocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zc
 * @date 2021/2/19 18:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestVO1 {
    private long id;
    private String name;
    private int age;
    private List<String> list;
}
