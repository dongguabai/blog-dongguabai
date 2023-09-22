package blog.dongguabai.lb.core.registry;

import blog.dongguabai.lb.core.registry.loadbalance.LoadBalance;
import blog.dongguabai.lb.core.registry.loadbalance.RandomLoadBanalce;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * 服务发现实现类
 * @author Dongguabai
 * @date 2018/11/2 9:56
 */
@Slf4j
public class ServiceDiscoveryImpl implements IServiceDiscovery {

    /**
     * /rpcNode/dgb.nospring.myrpc.demo.IHelloService
     * 当前服务下所有的协议地址
     */
    private  List<String> repos;

    /**
     * ZK地址
     */
    private String zkAddress;

    private CuratorFramework curatorFramework;

    @Override
    public String discover(String serviceName) {
        //获取/rpcNode/dgb.nospring.myrpc.demo.IHelloService下所有协议地址
        String nodePath = RegistryCenterConfig.NAMESPACE+"/"+serviceName;
        try {
            GetChildrenBuilder children = curatorFramework.getChildren();
            repos = curatorFramework.getChildren().forPath(nodePath);
        } catch (Exception e) {
            throw new RuntimeException("服务发现获取子节点异常！",e);
        }
        //动态发现服务节点变化，需要注册监听
        registerWatcher(nodePath);

        //这里为了方便，直接使用随机负载
        LoadBalance loadBalance  = new RandomLoadBanalce();
        return loadBalance.selectHost(repos);
    }

    /**
     * 监听节点变化，给repos重新赋值
     * @param path
     */
    private void registerWatcher(String path){
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,path,true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                repos = curatorFramework.getChildren().forPath(path);
            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            throw new RuntimeException("监听节点变化异常！",e);
        }
    }

    public ServiceDiscoveryImpl(String zkAddress) {
        this.zkAddress = zkAddress;
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(RegistryCenterConfig.CONNECTING_STR)
                .sessionTimeoutMs(RegistryCenterConfig.SESSION_TIMEOUT)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10)).build();
        curatorFramework.start();
    }
}
