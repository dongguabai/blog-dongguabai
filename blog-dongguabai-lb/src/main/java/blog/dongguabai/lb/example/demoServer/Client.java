package blog.dongguabai.lb.example.demoServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author Dongguabai
 * @date 2018/10/31 19:38
 */
public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        //这里就不能直接new了；
        //获取Service实例
        IHelloService helloService = (IHelloService) Naming.lookup("rmi://127.0.0.1/hello");
        System.out.println(helloService.sayHello("张三"));
    }
}
