package blog.dongguabai.lb.core.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 注册中心实现
 *
 * @author Dongguabai
 * @date 2018/11/1 19:10
 */
@Slf4j
public class RegistryCenterImpl implements IRegistryCenter {

    private CuratorFramework curatorFramework;

    {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(RegistryCenterConfig.CONNECTING_STR)
                .sessionTimeoutMs(RegistryCenterConfig.SESSION_TIMEOUT)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10)).build();
        curatorFramework.start();
    }

    //注册相应服务
    @Override
    public void register(String serviceName, String serviceAddress) {
        String serviceNodePath = RegistryCenterConfig.NAMESPACE + "/" + serviceName;
        try {
            //如果serviceNodePath（/rpcNode/userService）不存在就创建
            if (curatorFramework.checkExists().forPath(serviceNodePath)==null){
                //持久化节点
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(serviceNodePath,RegistryCenterConfig.DEFAULT_VALUE);
            }
            //注册的服务的节点路径
            String addressPath = serviceNodePath+"/"+serviceAddress;
            //临时节点
            String rsNode = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(addressPath, RegistryCenterConfig.DEFAULT_VALUE);
            log.info("服务注册成功：{}",rsNode);

        } catch (Exception e) {
            throw new RuntimeException("注册服务出现异常！",e);
        }
    }

}
