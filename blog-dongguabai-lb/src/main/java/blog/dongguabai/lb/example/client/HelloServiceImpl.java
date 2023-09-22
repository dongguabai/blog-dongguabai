package blog.dongguabai.lb.example.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Dongguabai
 * @date 2018/10/31 19:10
 */
public class HelloServiceImpl extends UnicastRemoteObject implements IHelloService {

    protected HelloServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String name) throws RemoteException{
        System.out.println("你好："+name+"！");
        return name;
    }
}
