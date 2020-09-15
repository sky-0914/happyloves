package cn.happyloves.redis.session;

import cn.happyloves.redis.TestVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis Session
 *
 * @author zc
 * @date 2020/9/15 19:12
 */
@RestController
@RequestMapping(value = "/redis")
public class SessionController {

    @GetMapping(value = "/first")
    public Map<String, Object> firstResp(HttpSession session, String name) {
        Map<String, Object> map = new HashMap<>(1);
        TestVO vo = new TestVO();
        vo.setName(name);
        session.setAttribute("account", vo);
        map.put("sessionId", session.getId());
        return map;
    }

    @GetMapping(value = "/sessions")
    public Object sessions(HttpSession session) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("sessionId", session.getId());
        map.put("message", session.getAttribute("account"));
        return map;
    }
}
