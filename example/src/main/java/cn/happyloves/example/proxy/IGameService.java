package cn.happyloves.example.proxy;

/**
 * @author zc
 * @date 2021/2/6 17:45
 */
public interface IGameService {
    /**
     * 登陆
     *
     * @param username 用户名
     * @param pwd      密码
     */
    void login(String username, String pwd);

    /**
     * 玩游戏
     */
    void playGame();
}
