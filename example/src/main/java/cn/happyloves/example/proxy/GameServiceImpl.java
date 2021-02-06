package cn.happyloves.example.proxy;

/**
 * @author zc
 * @date 2021/2/6 17:46
 */
public class GameServiceImpl implements IGameService {

    private String username;

    @Override
    public void login(String username, String pwd) {
        this.username = username;
        System.out.println(username + ",登陆成功");
    }

    @Override
    public void playGame() {
        System.out.println(username + ",正在玩游戏");
    }
}
