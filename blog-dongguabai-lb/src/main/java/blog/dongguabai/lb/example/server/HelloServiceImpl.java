package blog.dongguabai.lb.example.server;


import blog.dongguabai.lb.core.registry.RpcAnnotation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author Dongguabai
 * @date 2018/11/1 15:51
 */
@RpcAnnotation(value = IHelloService.class,version = "1.0")
@NoArgsConstructor
@AllArgsConstructor
public class HelloServiceImpl implements IHelloService {

    private int port;

    @Override
    public String sayHello(String name) {
        return String.format("[%d]-你好，%s", port, name);
    }
}
