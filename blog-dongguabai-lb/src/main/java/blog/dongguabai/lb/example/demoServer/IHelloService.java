package blog.dongguabai.lb.example.demoServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Dongguabai
 * @date 2018/10/31 19:09
 */
public interface IHelloService extends Remote{

    String sayHello(String name) throws RemoteException;
}
