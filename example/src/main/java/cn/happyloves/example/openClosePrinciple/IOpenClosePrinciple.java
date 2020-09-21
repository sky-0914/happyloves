package cn.happyloves.example.openClosePrinciple;

/**
 * 开闭原则、策略模式
 * 定义：一个软件实体如类、模块和函数应该对扩展开放，对修改关闭。
 * 问题由来：在软件的生命周期内，因为变化、升级和维护等原因需要对软件原有代码进行修改时，可能会给旧代码中引入错误，也可能会使我们不得不对整个功能进行重构，并且需要原有代码经过重新测试。
 * 解决方案：当软件需要变化时，尽量通过扩展软件实体的行为来实现变化，而不是通过修改已有的代码来实现变化。
 *
 * @author ZC
 * @date 2020/9/21 23:12
 */
public interface IOpenClosePrinciple {

    String TEST1 = "TEST1";
    String TEST2 = "TEST2";

    /**
     * 测试输出
     *
     * @return 返回值
     */
    String testOut();
}
