package blog.dongguabai.lb.example.client;

import blog.dongguabai.lb.core.RpcClientProxy;
import blog.dongguabai.lb.core.registry.IServiceDiscovery;
import blog.dongguabai.lb.core.registry.RegistryCenterConfig;
import blog.dongguabai.lb.core.registry.ServiceDiscoveryImpl;
import blog.dongguabai.lb.example.server.IHelloService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dongguabai
 * @date 2018/11/1 18:10
 */
@Slf4j
public class ClientDemo {

    public static void main(String[] args) {
        /*RpcClientProxy proxy = new RpcClientProxy();
        IHelloService helloService = proxy.clientProxy(IHelloService.class, "127.0.0.1", 12345);
        String name = helloService.sayHello("张三");
        System.out.println(name);*/
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            IServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl(RegistryCenterConfig.CONNECTING_STR);
            RpcClientProxy proxy = new RpcClientProxy(serviceDiscovery);
            blog.dongguabai.lb.example.server.IHelloService service = proxy.clientProxy(IHelloService.class);
            log.info(service.sayHello("张三"));
        }
    }
}
