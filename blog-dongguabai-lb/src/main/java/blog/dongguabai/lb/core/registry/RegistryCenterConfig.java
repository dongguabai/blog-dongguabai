package blog.dongguabai.lb.core.registry;

/**
 * @author Dongguabai
 * @date 2018/11/1 19:13
 */
public interface RegistryCenterConfig {

    /**
     * ZK地址int
     */
    String CONNECTING_STR = "172.16.140.144,172.16.140.131";

    int SESSION_TIMEOUT = 4000;

    /**
     * 注册中心namespace
     */
    String NAMESPACE = "/rpcNode";

    /**
     * value一般来说作用不大；一般主要是利用节点特性搞点事情
     */
    byte[] DEFAULT_VALUE = "0".getBytes();
}
