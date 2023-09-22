package blog.dongguabai.lb.core;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * socket传输
 *
 * @author Dongguabai
 * @date 2018/11/1 16:25
 */
@Slf4j
public class TcpTransport {

    private String serviceAddress;

    private Socket newSocket() {
        //System.out.println("准备创建Socket连接，"+serviceAddress);
        String[] split = serviceAddress.split(":");
        try {
            Socket socket = new Socket(split[0], Integer.parseInt(split[1]));
            return socket;
        } catch (IOException e) {
            throw new RuntimeException("Socket连接创建失败！" + serviceAddress);
        }
    }

    public TcpTransport(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public Object send(RpcRequest rpcRequest) {
        Socket socket = null;
        try {
            socket = newSocket();
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(rpcRequest);
                outputStream.flush();
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Object result = inputStream.readObject();
                inputStream.close();
                outputStream.close();
                return result;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("发起远程调用异常！",e);
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
