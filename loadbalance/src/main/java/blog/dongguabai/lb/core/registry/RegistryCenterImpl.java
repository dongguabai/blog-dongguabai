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
import java.util.stream.IntStream;

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
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
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
            log.info("服务注册成功：{}", rsNode);
            //启动更新元数据（上传）cpu 信息
            refreshMetadata(addressPath);
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
        }, 5, 2, TimeUnit.SECONDS);

        //模拟
        highCpuUsage(port);
    }

    private static final OperatingSystemMXBean OPERATING_SYSTEM_MX_BEAN = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    private static int getProcessCpuLoad() {
        //获取CPU Load
        double cpuLoad = OPERATING_SYSTEM_MX_BEAN.getProcessCpuLoad();
        return (int) (cpuLoad * 100);
    }

    public void highCpuUsage(int port) {
        //端口为12345的服务才进行模拟
        if (port != 12345) {
            return;
        }
        try {
            //延迟3s
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ignored) {
        }
        log.info("...........start highCpuUsage");
        //执行20s
        Thread cpuThread = new Thread(() -> call(), "highCpuUsage-thread");
        cpuThread.start();
        try {
            cpuThread.join();
            log.info("...........end highCpuUsage");
        } catch (InterruptedException ignored) {
        }
    }

    private void call() {
        long startTime = System.currentTimeMillis();
        long duration = 30000;
        while (System.currentTimeMillis() - startTime < duration) {
            // 空的计算任务，消耗CPU资源
            double result = Math.random() * Math.random();
        }
    }
}


