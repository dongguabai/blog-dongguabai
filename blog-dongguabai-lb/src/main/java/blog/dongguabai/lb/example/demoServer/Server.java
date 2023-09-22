package blog.dongguabai.lb.example.demoServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author Dongguabai
 * @date 2018/10/31 19:33
 */
public class Server {

    public static void main(String[] args) {
        try {
            //实例化Service
            IHelloService helloService = new HelloServiceImpl();
            //注册，这个端口必须是1099
            LocateRegistry.createRegistry(1099);
            //绑定
            Naming.rebind("rmi://127.0.0.1/hello",helloService);
            System.out.println("服务发布成功！！");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
