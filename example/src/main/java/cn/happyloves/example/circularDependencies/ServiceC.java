package cn.happyloves.example.circularDependencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zc
 * @date 2021/4/14 21:42
 */
@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceC {
//    private final ServiceB serviceB;

    //    @Autowired
    private ServiceB serviceB;

//    private ServiceB serviceB;
//    @Autowired
//    public ServiceC(ServiceB serviceB) {
//        this.serviceB = serviceB;
//    }

    @Autowired
    public ServiceC(ServiceB serviceB) {
        System.out.println(serviceB);
        this.serviceB = serviceB;
    }
}
