package blog.dongguabai.lb.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * 任务处理类
 *
 * @author Dongguabai
 * @date 2018/11/1 16:10
 */
public class ProcessorHandler implements Runnable {

    private Socket socket;
    /**
     * 服务端发布的服务
     */
    private Map<String,Object> handlerMap;

    /**
     * 通过构造传入Map
     * @param socket
     * @param handlerMap
     */
    public ProcessorHandler(Socket socket, Map<String, Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    //处理请求
    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            //反序列化
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = invoke(rpcRequest);
            //将结果返回给客户端
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 反射调用
     *
     * @param rpcRequest
     */
    /*private Object invoke(RpcRequest rpcRequest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("服务端开始调用------");
        Object[] parameters = rpcRequest.getParameters();
        Class[] parameterTypes = new Class[parameters.length];
        for (int i = 0, length = parameters.length; i < length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }
        Method method = service.getClass().getMethod(rpcRequest.getMethodName(), parameterTypes);
        return method.invoke(service, parameters);
    }*/

    /**
     * 反射调用（之前通过Service进行反射调用，现在通过Map获取service）
     *
     * @param rpcRequest
     */
    private Object invoke(RpcRequest rpcRequest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("服务端开始调用------");
        Object[] parameters = rpcRequest.getParameters();
        Class[] parameterTypes = new Class[parameters.length];
        for (int i = 0, length = parameters.length; i < length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }
        //从Map中获得Service（根据客户端请求的ServiceName，获得相应的服务），依旧是通过反射发起调用
        Object service = handlerMap.get(rpcRequest.getClassName());
        Method method = service.getClass().getMethod(rpcRequest.getMethodName(), parameterTypes);
        return method.invoke(service, parameters);
    }



}
