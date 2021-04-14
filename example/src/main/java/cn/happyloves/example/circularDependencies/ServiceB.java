package cn.happyloves.example.circularDependencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zc
 * @date 2021/4/14 21:42
 */
@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceB {
//    private final ServiceA serviceA;

    //    @Autowired
    private ServiceA serviceA;

//    private ServiceA serviceA;
//    @Autowired
//    public ServiceB(ServiceA serviceA) {
//        this.serviceA = serviceA;
//    }

    @Autowired
    public void setServiceA(ServiceA serviceA) {
        System.out.println(serviceA);
        this.serviceA = serviceA;
    }
}
