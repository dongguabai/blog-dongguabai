package blog.dongguabai.lb.example.client;

import blog.dongguabai.lb.core.RpcClientProxy;
import blog.dongguabai.lb.core.registry.IServiceDiscovery;
import blog.dongguabai.lb.core.registry.RegistryCenterConfig;
import blog.dongguabai.lb.core.registry.ServiceDiscoveryImpl;
import blog.dongguabai.lb.example.server.IHelloService;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Dongguabai
 * @date 2018/11/1 18:10
 */
@Slf4j
public class ClientDemo {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 600; i++) {
            IServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl(RegistryCenterConfig.CONNECTING_STR);
            RpcClientProxy proxy = new RpcClientProxy(serviceDiscovery);
            blog.dongguabai.lb.example.server.IHelloService service = proxy.clientProxy(IHelloService.class);
            System.out.println(new Date().toLocaleString() + " " + service.sayHello("张三"));
            Thread.sleep(1000);
        }
    }
}
