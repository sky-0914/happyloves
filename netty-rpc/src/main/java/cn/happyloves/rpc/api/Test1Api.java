package cn.happyloves.rpc.api;

/**
 * @author zc
 * @date 2021/3/1 17:55
 */
public interface Test1Api {

    void test();

    void test(int id, String name);

    String testStr(int id);

    Object testObj();
}
