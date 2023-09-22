package blog.dongguabai.lb.core;

import blog.dongguabai.lb.core.registry.IRegistryCenter;
import blog.dongguabai.lb.core.registry.RpcAnnotation;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Dongguabai
 * @date 2018/11/1 15:53
 */
@Slf4j
public class RpcServer {

    /**
     * 注册中心
     */
    private IRegistryCenter registryCenter;

    /**
     * 服务的发布地址
     */
    private String addressService;

    /**
     * 服务名称和服务对象之间的关系
     */
    private static final Map<String,Object> HANDLER_MAPPING = new HashMap<>();

    //不建议通过Executors创建线程池，这里为了方便
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    /*public void publisher(final Object service, int port) {
        //启动一个服务监听
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true){
                //通过ServerSocket获取请求
                Socket socket = serverSocket.accept();
                executor.execute(new ProcessorHandler(socket,service));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 改造后的发布服务的方法
     */
    public void publisher() {
        //启动一个服务监听
        //获取端口
        int port = Integer.parseInt(addressService.split(":")[1]);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            //循环获取所有的接口Name
            HANDLER_MAPPING.keySet().forEach(interfaceName->{
                registryCenter.register(interfaceName,addressService);
               // log.info("注册服务成功：【serviceName：{}，address：{}】",interfaceName,addressService);
            });
            while (true){
                //通过ServerSocket获取请求
                Socket socket = serverSocket.accept();
                executor.execute(new ProcessorHandler(socket,HANDLER_MAPPING));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定服务名称和服务对象
     * @param services
     */
    public void bind(Object... services){
        for (Object service : services) {
            //获取发布的服务接口
            RpcAnnotation rpcAnnotation = service.getClass().getAnnotation(RpcAnnotation.class);
            if (rpcAnnotation==null){
                continue;
            }
            //发布接口的class
            String serviceName = rpcAnnotation.value().getName();
            //将serviceName和service进行绑定
            HANDLER_MAPPING.put(serviceName,service);
        }
    }

    public RpcServer(IRegistryCenter registryCenter, String addressService) {
        this.registryCenter = registryCenter;
        this.addressService = addressService;
    }
}
