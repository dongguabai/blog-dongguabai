package blog.dongguabai.lb.core;


import blog.dongguabai.lb.core.registry.IServiceDiscovery;
import blog.dongguabai.lb.core.registry.Invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Dongguabai
 * @date 2018/11/1 16:20
 */
public class RemoteInvocationHandler implements InvocationHandler{

    private IServiceDiscovery serviceDiscovery;

    /**
     *发起客户端和服务端的远程调用。调用客户端的信息进行传输
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        //从ZK中获取地址 127.0.0.1:12345
        Invoker discover = serviceDiscovery.discover(rpcRequest.getClassName());
        //System.out.println("获取的服务地址为："+discover);
        //直接执行
//        TcpTransport tcpTransport = new TcpTransport(discover.getAddress());
//        return tcpTransport.send(rpcRequest);
        //机器性能问题，这里不直接调用，仅返回选择的Server
        return discover.getAddress().split(":")[1];
    }

    public RemoteInvocationHandler(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }
}
