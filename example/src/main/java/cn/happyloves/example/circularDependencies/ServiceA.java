package cn.happyloves.example.circularDependencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zc
 * @date 2021/4/14 21:42
 */
@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceA {
//    private final ServiceC serviceC;

    //    @Autowired
    private ServiceC serviceC;

//    private ServiceC serviceC;
//    @Autowired
//    public ServiceA(ServiceC serviceC) {
//        this.serviceC = serviceC;
//    }

    @Autowired
    public void setServiceC(ServiceC serviceC) {
        System.out.println(serviceC);
        this.serviceC = serviceC;
    }
}
