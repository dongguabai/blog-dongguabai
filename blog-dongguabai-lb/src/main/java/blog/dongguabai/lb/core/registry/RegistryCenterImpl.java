package blog.dongguabai.lb.core.registry;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
            if (curatorFramework.checkExists().forPath(serviceNodePath) == null) {
                //持久化节点
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(serviceNodePath, RegistryCenterConfig.DEFAULT_VALUE);
            }
            //注册的服务的节点路径
            String addressPath = serviceNodePath + "/" + serviceAddress;
            //临时节点
            String rsNode = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(addressPath, RegistryCenterConfig.DEFAULT_VALUE);
            refreshMetadata(addressPath);
            log.info("服务注册成功：{}", rsNode);

        } catch (Exception e) {
            throw new RuntimeException("注册服务出现异常！", e);
        }
    }

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    private void refreshMetadata(String addressPath) {
        int port = Integer.parseInt(addressPath.split(":")[1]);
        SCHEDULED_EXECUTOR.scheduleWithFixedDelay(() -> {
            try {
                int processCpuLoad = getProcessCpuLoad();
                curatorFramework.setData().forPath(addressPath, String.valueOf(processCpuLoad).getBytes());
                log.info("[{}] refresh cpu : {}", port, processCpuLoad);
            } catch (Exception e) {
                log.error("refreshMetadata error.", e);
            }
        }, 3, 2, TimeUnit.SECONDS);

    }


    public static void main(String[] args) throws InterruptedException {


        while (true) {
            int percentCpuLoad = getProcessCpuLoad();

            //获取内存
            log.info("CPU = {}", percentCpuLoad);
            Thread.sleep(1000);

            new Thread(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("开始搞事/....");
                while (true) {
                    // 空的计算任务，消耗CPU资源
                    double result = Math.random() * Math.random();
                }

            }).start();

        }

    }


    private static final OperatingSystemMXBean OPERATING_SYSTEM_MX_BEAN = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    private static int getProcessCpuLoad() {
        //获取CPU
        double cpuLoad = OPERATING_SYSTEM_MX_BEAN.getProcessCpuLoad();
        return (int) (cpuLoad * 100);
    }

}


