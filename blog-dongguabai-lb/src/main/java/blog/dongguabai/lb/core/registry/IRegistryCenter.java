package blog.dongguabai.lb.core.registry;

/**
 * 注册中心顶层接口
 * @author Dongguabai
 * @date 2018/11/1 19:05
 */
public interface IRegistryCenter {

    /**
     * 注册服务
     * @param serviceName 服务名称
     * @param serviceAddress 服务地址
     */
    void register(String serviceName, String serviceAddress);
}
