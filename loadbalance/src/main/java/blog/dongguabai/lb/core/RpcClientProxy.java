package blog.dongguabai.lb.core;

import blog.dongguabai.lb.core.registry.IServiceDiscovery;

import java.lang.reflect.Proxy;

/**
 * 客户端代理
 * @author Dongguabai
 * @date 2018/11/1 16:18
 */
public class RpcClientProxy {

    private IServiceDiscovery serviceDiscovery;

   /* public <T> T clientProxy(final Class<T> interfaceClass,final String host,final int port){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class[]{interfaceClass},new RemoteInvocationHandler(host, port));
    }*/
    public <T> T clientProxy(final Class<T> interfaceClass){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class[]{interfaceClass},new RemoteInvocationHandler(serviceDiscovery));
    }

    public RpcClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }
}
